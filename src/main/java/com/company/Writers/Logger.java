package com.company.Writers;

import org.slf4j.LoggerFactory;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class Logger {

    public org.slf4j.Logger logger;

    public void WriteLine(String str){
        logger.info(str);
    }

    public Logger() {
        logger =  LoggerFactory.getLogger(Logger.class);
    }
}
