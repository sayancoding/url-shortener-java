package com.arrowsmodule.urlshortener.exception;

public class InvalidURLException extends RuntimeException{
    public InvalidURLException(String msg){
        super(msg);
    }
}
