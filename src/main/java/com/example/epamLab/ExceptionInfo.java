package com.example.epamLab;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExceptionInfo {
    @JsonProperty
    private String message;

    @JsonProperty
    private int code;

    public ExceptionInfo(String message, int code){
        this.message=message;
        this.code=code;
    }
}
