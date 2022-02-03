package training.lepeskin.utility.logger;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FilesLogger {
    private Logger logger;

    public FilesLogger(String className, String fileName) {
        try {
            logger = Logger.getLogger(className);
            Handler fileHandler = new FileHandler(fileName);
            logger.setUseParentHandlers(false);
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            String logString = String.format("Logs cannot be redirected to a file '%s'. ", fileName);
            throw new RuntimeException(logString + e);
        }
    }

    public void log(String logString, Throwable exception) {
        logger.log(Level.INFO, logString, exception);
    }
}
