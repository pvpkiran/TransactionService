package com.number26.pojo.Response;

public class PutResponse {

    //Dummy Constructor for the sake of Json Mapping
    public PutResponse(){

    }
    String status;

    public String getStatus() {
        return status;
    }

    public PutResponse(String status) {
        this.status = status;
    }
}

