package com.example.minidoorayaccount.exception;

public class NotFoundTeamCodeException extends RuntimeException {
    public NotFoundTeamCodeException() {
        super("존재하지 않는 조직입니다.");
    }
}
