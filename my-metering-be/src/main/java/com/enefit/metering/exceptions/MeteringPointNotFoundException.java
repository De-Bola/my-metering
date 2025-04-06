package com.enefit.metering.exceptions;

public class MeteringPointNotFoundException extends RuntimeException {
    public MeteringPointNotFoundException(){
        super();
    }

    public MeteringPointNotFoundException(String msg){
        super(msg);
    }

    public MeteringPointNotFoundException(String msg, Throwable throwable){
        super(msg, throwable);
    }
}
