package com.enefit.metering.exceptions;

public class CustomerAlreadyExistsException extends Throwable {
    public CustomerAlreadyExistsException(){
        super();
    }

    public CustomerAlreadyExistsException(String msg){
        super(msg);
    }

    public CustomerAlreadyExistsException(String msg, Throwable throwable){
        super(msg, throwable);
    }
}
