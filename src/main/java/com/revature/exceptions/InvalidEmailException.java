package com.revature.exceptions;

public class InvalidEmailException extends Exception{
    public InvalidEmailException(String msg) {
        super(msg);
    }
}
