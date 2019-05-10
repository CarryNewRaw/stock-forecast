package com.ls.stockforecast.core.web.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * restController异常统一处理类
 */
@RestControllerAdvice
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {


    private Logger log = LoggerFactory.getLogger(RestControllerExceptionHandler.class);


    /**
     * 500异常处理
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleRuntimeException(HttpServletRequest request, RuntimeException ex){
        log.error("", ex);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
