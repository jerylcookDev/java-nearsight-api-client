package ai.nearsight.sample.app;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import ai.nearsight.sample.config.OpenSkyFeedProperties;
import ai.nearsight.sample.model.OpenSkyMapper;
import ai.nearsight.sample.model.OpenSkyStates;
import ai.nearsight.sample.model.TrackedAsset;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;


@Component
public class PopulateNearSight {
	Logger log = LoggerFactory.getLogger(PopulateNearSight.class);
	private static final String TRACK_PATH = "/api/v1/assets/track";
	private static final int INGEST_CONCURRENCY = 8;
	private final WebClient openSkyWebClient;
	private final  WebClient nearsightWebClient;
	  private final OpenSkyFeedProperties props;
	    private final ReactiveCircuitBreaker openSkyBreaker;
	    private final AtomicBoolean inFlight = new AtomicBoolean(false);
	public PopulateNearSight(WebClient openSkyWebClient,
							WebClient nearsightWebClient,
							OpenSkyFeedProperties props,
							ReactiveCircuitBreakerFactory<?, ?> cbFactory) {
		this.openSkyWebClient = openSkyWebClient;
		this.nearsightWebClient = nearsightWebClient;
        this.props = props;
        this.openSkyBreaker = cbFactory.create("openSky");
	}
	
	@Scheduled(fixedRate = 30000) // Run every 30 seconds
	public void populate() {
		   if (!inFlight.compareAndSet(false, true)) {
	            log.debug("OpenSky relay still running; skipping tick");
	            return;
	        }
		   
		   var box = props.box();
	        openSkyBreaker.run(
	                trackedAircraftInBox(box.laMin(), box.loMin(), box.laMax(), box.loMax()),
	                t -> {                       // breaker-open / fetch-failure fallback
	                    log.warn("OpenSky fetch failed: {}", t.toString());
	                    return Flux.empty();
	                })
	            .flatMap(asset -> track(asset), INGEST_CONCURRENCY)
	            .doFinally(sig -> inFlight.set(false))
	            .subscribe(
	                null,
	                err -> log.warn("Nearsight relay failed: {}", err.toString()),
	                () -> log.debug("OpenSky relay tick complete"));
	}
	
	/** Query OpenSky for a bounding box and map to TrackedAssets, skipping positionless rows. */
	public Flux<TrackedAsset> trackedAircraftInBox(double laMin, double loMin, double laMax, double loMax) {
	    return getStatesInBox(laMin, loMin, laMax, loMax)
	            .flatMapMany(states -> {
	                List<List<Object>> rows = states.states();
	                return rows == null ? Flux.empty() : Flux.fromIterable(rows);
	            })
	            .mapNotNull(OpenSkyMapper::toTrackedAsset);   // drops nulls (no position / no id)
	}
	
    /** All current state vectors within a bounding box (lat/lng degrees). */
    public Mono<OpenSkyStates> getStatesInBox(double laMin, double loMin, double laMax, double loMax) {
        return openSkyWebClient.get()
                .uri(uri -> uri.path("/states/all")
                        .queryParam("lamin", laMin)
                        .queryParam("lomin", loMin)
                        .queryParam("lamax", laMax)
                        .queryParam("lomax", loMax)
                        .build())
                .retrieve()
                .bodyToMono(OpenSkyStates.class);
    }
    
    public Mono<ResponseEntity<Void>> track(TrackedAsset asset) {
    	asset.setName( asset.getName().toUpperCase() );   // standardize on uppercase hex)
    	log.info("Tracking asset sa saving {} ", asset.getName());
        return nearsightWebClient.post()
                .uri(TRACK_PATH)
                .bodyValue(asset)
                .retrieve()
                .onStatus(status -> status.value() == 401, this::unauthorized)
                .onStatus(status -> status.value() == 429, this::rateLimited)
                .toBodilessEntity()
                .retryWhen(rateLimitRetry());
    }
    
    private Mono<? extends Throwable> unauthorized(ClientResponse response) {
        return Mono.error(new RuntimeException("Unauthorized (401)"));
    }
    
    private Mono<? extends Throwable> rateLimited(ClientResponse response) {
        return Mono.error(new RuntimeException("Rate limited (429)"));
    }
    
    private Retry rateLimitRetry() {
        return Retry.backoff(3, Duration.ofSeconds(1));
    }
}
