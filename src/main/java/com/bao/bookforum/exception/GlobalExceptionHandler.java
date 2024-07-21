package com.bao.bookforum.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
//    @ResponseBody
//    @ExceptionHandler(Exception.class)
//    public R exception(Exception e){
//        e.printStackTrace();
//        return R.error();
//    }

//    //自定义异常
//    @ResponseBody
//    @ExceptionHandler(TestException.class)
//    public R exception(TestException e){
//        e.printStackTrace();
//        return R.error().message(e.getMessage()).code(e.getCode());
//    }
}
