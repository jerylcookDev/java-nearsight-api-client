package ai.nearsight.sample.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Enables Spring's scheduling support so components annotated with
 * {@link org.springframework.scheduling.annotation.Scheduled} are executed.
 */
@Configuration
@EnableScheduling
public class SchedulingConfig {

}

