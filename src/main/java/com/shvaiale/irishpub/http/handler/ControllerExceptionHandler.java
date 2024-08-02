package com.shvaiale.irishpub.http.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(basePackages = "com.shvaiale.irishpub.http.controller")
public class ControllerExceptionHandler /* extends ResponseEntityExceptionHandler */ {

//    @ExceptionHandler(Exception.class)
//    public String handleExceptions(Exception exception, HttpServletRequest request) {
//        return "error/error500";
//    }
}
