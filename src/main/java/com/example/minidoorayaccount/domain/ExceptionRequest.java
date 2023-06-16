package com.example.minidoorayaccount.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ExceptionRequest {
    private int statusCode;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timeStamp;

    private String message;

    private String error;

    private String path;

    public ExceptionRequest() {
        timeStamp = LocalDateTime.now();
    }
}
