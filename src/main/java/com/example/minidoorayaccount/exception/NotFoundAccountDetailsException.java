package com.example.minidoorayaccount.exception;

public class NotFoundAccountDetailsException extends RuntimeException {
    public NotFoundAccountDetailsException() {
        super("존재하지 않는 계정 세부정보입니다.");
    }
}
