package com.revature.exceptions;

public class DuplicateUserException extends Exception{

    public DuplicateUserException(String msg) {
        super(msg);
    }
}
