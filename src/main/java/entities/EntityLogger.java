package entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityLogger {
    private static final Logger logger = LoggerFactory.getLogger("EntityLogic");

    public static void error(String message, Object... args) {
        logger.error(message, args);
    }

    public static void info(String message, Object... args) {
        logger.info(message, args);
    }
}
