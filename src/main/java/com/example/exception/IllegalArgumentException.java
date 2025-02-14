package com.example.exception;

public class IllegalArgumentException extends RuntimeException{
    public IllegalArgumentException(String msg){
        super(msg);
    }
}
