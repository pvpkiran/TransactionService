package com.number26.exceptions;

public class TransactionException extends Exception{
    private final String message;

    public TransactionException(final String message){
        this.message = message;
    }

    public String toString(){
        return "Error : "+ message;
    }
}
