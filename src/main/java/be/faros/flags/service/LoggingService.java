package be.faros.flags.service;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.Level;


@Service
public class LoggingService {

    public void toggleLogging(boolean enableDebugLogs) {
        Logger togglzLogger = (Logger) LoggerFactory.getLogger("org.togglz");
        Logger corsLogger = (Logger) LoggerFactory.getLogger("org.springframework.web.cors");
        Logger securityLogger = (Logger) LoggerFactory.getLogger("org.springframework.security");

        Level logLevel = enableDebugLogs ? Level.DEBUG : Level.INFO;

        togglzLogger.setLevel(logLevel);
        corsLogger.setLevel(logLevel);
        securityLogger.setLevel(logLevel);
    }
}
