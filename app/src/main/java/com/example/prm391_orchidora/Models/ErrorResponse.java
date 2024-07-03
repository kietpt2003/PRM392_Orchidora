package com.example.prm391_orchidora.Models;

public class ErrorResponse {
    private String target;
    private String message;

    public ErrorResponse(String target, String message) {
        this.target = target;
        this.message = message;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
