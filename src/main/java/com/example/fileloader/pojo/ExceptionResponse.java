package com.example.fileloader.pojo;

import java.util.Date;

public class ExceptionResponse {
    private Date timestamp;
    private String message;
    private String details;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private int status;

    public ExceptionResponse(Date date, int status, String message, String details){
        this.timestamp = date;
        this.status = status;
        this.message = message;
        this.details = details;
    }

    public ExceptionResponse(){}

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timeStamp) {
        this.timestamp = timeStamp;
    }
}
