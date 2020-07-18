package com.learn.userservice.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.util.HashMap;

/**
 * 错误集合
 */
public class ErrorMap {
    private static final Logger logger = LoggerFactory.getLogger(ErrorMap.class);
    private static HashMap<String, String> errorMap = new HashMap<>();

    static{
        ClassPathResource classPathResource = new ClassPathResource("error.properties");
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(classPathResource.getFile()));
            String line;
            while((line = bufferedReader.readLine()) != null){
                if (line != "") {
                    String[] errorInfo = line.split("=");
                    errorMap.put(errorInfo[0],errorInfo[1]);
                }

            }
        } catch (IOException e) {
            logger.error("error.properties读取错误{}", e);
        }

    }

    public static String getMessage(String code){
        return errorMap.get(code);
    }
}
