package com.bao.bookforum.exception;

public class TestException extends RuntimeException{
    private Integer code;

    public TestException(ResultCodeEnum resultCodeEnum){
        super(resultCodeEnum.getMessage());
        this.code=resultCodeEnum.getCode();
    }

    public Integer getCode(){
        return code;
    }
}
