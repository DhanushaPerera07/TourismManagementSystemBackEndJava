package com.elephasvacation.tms.web.config;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static com.elephasvacation.tms.web.commonconstant.Commons.LOGGER_FILE_DIRECTORY;
import static com.elephasvacation.tms.web.commonconstant.Commons.LOGGER_FILE_NAME;

public class LogConfig {

    public static void initLogging() {
        Properties prop = new Properties();
        try {
            String logFilePath;
            if (prop.getProperty("app.log_dir") != null) {
                logFilePath = Paths.get(prop.getProperty("app.log_dir"),
                        LOGGER_FILE_NAME).toString();
                // logFilePath = prop.getProperty("app.log_dir") + "/back-end.log";
            } else {
                logFilePath = Paths.get(System.getProperty("catalina.home"),
                        LOGGER_FILE_DIRECTORY,
                        LOGGER_FILE_NAME).toString();
                // logFilePath = System.getProperty("catalina.home") + "/logs/back-end.log";
            }
            FileHandler fileHandler = new FileHandler(logFilePath, true);
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setLevel(Level.INFO);
            Logger.getLogger("").addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
