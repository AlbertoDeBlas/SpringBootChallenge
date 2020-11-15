package com.n26.exception;

public class OldTransactionException extends RuntimeException{
    public OldTransactionException(){
        super("Old transaction ");
    }

}
