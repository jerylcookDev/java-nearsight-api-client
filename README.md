# java-nearsight-api-client

Sample Java client that polls OpenSky (state vectors) and forwards tracked assets to a NearSight API.

This module is a small Spring Boot-based example showing how to:

- Configure a reactive WebClient that authenticates to OpenSky using OAuth2 client credentials
- Map OpenSky state vectors to a NearSight `TrackedAsset` model
- Periodically poll OpenSky for aircraft in a bounding box and POST those assets to a NearSight ingest endpoint

Key implementation notes
- Main example app class: `ai.nearsight.sample.app.OpenSkypeApp`
- Polling/ingest component: `ai.nearsight.sample.app.PopulateNearSight`
- Configuration properties are defined under the `nearsight` prefix and in `application.yaml`.

Prerequisites
- Java 21 (project uses Java 21 language features)
- Maven 3.8+ (or a modern Maven compatible with Spring Boot 4.x)
- Network access to OpenSky token endpoint and API, and to your NearSight API endpoint

Build

From the project root (this repository folder):

```bash
mvn -DskipTests package
```

Run (dev)

You can run the sample app from Maven or your IDE. The sample main class is `ai.nearsight.sample.app.OpenSkypeApp`.

Run with Maven:

```bash
mvn spring-boot:run -Dspring-boot.run.main-class=ai.nearsight.sample.app.OpenSkypeApp
```

Or run from your IDE by launching `OpenSkypeApp`.

Configuration

The project includes `src/main/resources/application.yaml` with example values. The following environment variables must be provided (or set the properties in your external configuration):

- `NEARSIGHT_API_KEY` — API key used by this client to authenticate to the NearSight ingest endpoint (sent as `X-API-Key`).
- `OPENSKY_CLIENT_ID` — OAuth2 client id for OpenSky
- `OPENSKY_CLIENT_SECRET` — OAuth2 client secret for OpenSky

Relevant properties from `application.yaml`:

- `nearsight.client.base-url` — base URL for your NearSight API (example: `https://api1.nearsight.ai`)
- `nearsight.client.api-key` — (prefer to provide via `NEARSIGHT_API_KEY` env var)
- `nearsight.feed.opensky.enabled` — enable/disable the polling feed
- `nearsight.feed.opensky.poll-interval-seconds` — how often to poll OpenSky
- `nearsight.feed.opensky.box` — bounding box to query (la-min, lo-min, la-max, lo-max)

The OAuth2 OpenSky registration is configured in `application.yaml` under `spring.security.oauth2.client.registration.opensky`.

Behavior

- On each scheduled tick `PopulateNearSight` queries OpenSky `/states/all` for the configured bounding box.
- Each state vector row is mapped to a `TrackedAsset` (class `ai.nearsight.sample.model.TrackedAsset`) by `OpenSkyMapper`.
- The client posts each `TrackedAsset` to the NearSight ingest path `/api/v1/assets/track` using the configured `nearsightWebClient` which sends `X-API-Key`.
- The component contains simple resilience: a circuit breaker for OpenSky calls and retry/backoff for rate-limited responses.

Notes & caveats
- The sample `application.yaml` uses interpolation to read secrets from environment variables. Do not hardcode secrets in source control.
- The `pom.xml` declares Spring Boot 4.x and requires a compatible JDK and Maven. If you run into build issues, ensure your local tooling matches those versions.
- The `start-class` property in the `pom.xml` may point to a different main class in some parent templates — when running locally specify the `spring-boot.run.main-class` as shown above if needed.

Development & testing

- There are no integration tests shipped in this sample. Add tests under `src/test/java` and run `mvn test`.

Contributing

If you want to extend this sample:

- Add support for additional feeds or additional mapping logic in `OpenSkyMapper`.
- Add configuration to control concurrency or ingest paths.
- Add unit and integration tests.

License

This repository does not include a license file. Add your preferred license in `LICENSE` if you intend to publish or share the code.

