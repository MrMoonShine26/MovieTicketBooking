package com.xyz.exception;


public class BookingException extends Exception {

    private final String msg;


    public BookingException(String msg) {
        super(msg);
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return msg;
    }
}
