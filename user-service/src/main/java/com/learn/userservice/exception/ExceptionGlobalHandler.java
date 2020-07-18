package com.learn.userservice.exception;

import com.learn.userservice.entity.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionGlobalHandler {

    @ExceptionHandler(ServiceException.class)
    public Result HandlerServiceException(ServiceException exception) {
        String code = exception.getMessage();
        return Result.error(code,ErrorMap.getMessage(code));
    }
}
