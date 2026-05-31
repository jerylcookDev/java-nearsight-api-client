package ai.nearsight.sample.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

import ai.nearsight.sample.config.NearsightClientProperties;
import ai.nearsight.sample.config.OpenSkyFeedProperties;

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
 