package com.ranferi.ssrsi.model;

public class PlacesResponse {
    private boolean error;
    private String message;

    public PlacesResponse() {

    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
