package com.company.Writers;


import org.slf4j.LoggerFactory;

public class MyLogger {

    private static org.slf4j.Logger logger;

    public void WriteLine(String str){
        logger.info(str);
    }

    public MyLogger() {
        logger = LoggerFactory.getLogger(MyLogger.class);
    }
}
