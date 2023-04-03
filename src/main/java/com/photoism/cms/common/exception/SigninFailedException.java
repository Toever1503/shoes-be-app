package com.photoism.cms.common.exception;

public class SigninFailedException extends RuntimeException {
    public SigninFailedException(String msg, Throwable t) {
        super(msg, t);
    }

    public SigninFailedException(String msg) {
        super(msg);
    }

    public SigninFailedException() {
        super();
    }
}