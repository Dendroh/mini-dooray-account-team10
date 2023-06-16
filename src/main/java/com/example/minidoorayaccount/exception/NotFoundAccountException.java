package com.example.minidoorayaccount.exception;

public class NotFoundAccountException extends RuntimeException {
    public NotFoundAccountException() {
        super("존재하지 않는 계정입니다.");
    }
}
