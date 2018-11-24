package com.example.alvaro.client_audit.exceptions;

public class BDException extends RuntimeException {

    public BDException(){
        super();
    }

    public BDException(String msg){
        super(msg);
    }
}
