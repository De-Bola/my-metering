package com.enefit.metering.exceptions;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(){
        super();
    }

    public CustomerNotFoundException(String msg){
        super(msg);
    }

    public CustomerNotFoundException(String msg, Throwable throwable){
        super(msg, throwable);
    }
}
