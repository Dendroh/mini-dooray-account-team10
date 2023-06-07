package com.example.minidoorayaccount.exception;

public class NotFoundAccountTeamBundleException extends RuntimeException {

    public NotFoundAccountTeamBundleException() {
        super("이 계정이 팀에 속하지 않거나 팀 내에 계정이 속하지 않습니다.");
    }
}
