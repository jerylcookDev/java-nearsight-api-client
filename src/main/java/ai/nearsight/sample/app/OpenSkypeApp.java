package ai.nearsight.sample.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

import ai.nearsight.sample.config.NearsightClientProperties;
import ai.nearsight.sample.config.OpenSkyFeedProperties;

/**
 * Bootstraps the sample OpenSky → NearSight feeder application.
 *
 * <p>This class is a minimal Spring Boot entrypoint that enables the
 * configuration properties used by the example (`OpenSkyFeedProperties` and
 * `NearsightClientProperties`) and component-scans the `ai.nearsight.*`
 * packages so the sample beans are discovered.</p>
 *
 * <p>Run this class to start the scheduled feed that polls OpenSky and
 * forwards tracked assets to a NearSight API (the behavior is implemented in
 * {@code PopulateNearSight}).</p>
 */
@ComponentScan("ai.nearsight.*")
@EnableConfigurationProperties({
	OpenSkyFeedProperties.class,
	NearsightClientProperties.class   // do the same for your other @ConfigurationProperties
})
@SpringBootApplication
public class OpenSkypeApp {

	public static void main(String[] args) {
		SpringApplication.run(OpenSkypeApp.class, args);
	}
}
