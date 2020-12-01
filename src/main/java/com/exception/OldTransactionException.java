package com.exception;

public class OldTransactionException extends RuntimeException{
    public OldTransactionException(){
        super("Old transaction ");
    }

}
