package be.faros.flags.web;

import be.faros.flags.FeatureFlags;
import be.faros.flags.service.LoggingService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.togglz.core.context.FeatureContext;

@Component
public class LoggingToggleTask {

    private final LoggingService loggingService;

    public LoggingToggleTask(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    @Scheduled(fixedRate = 5000) // Check every 5 seconds
    public void updateLogging() {
        boolean isLoggingEnabled = FeatureContext.getFeatureManager().isActive(FeatureFlags.DEBUG_LOGS);
        loggingService.toggleLogging(isLoggingEnabled);
    }
}
