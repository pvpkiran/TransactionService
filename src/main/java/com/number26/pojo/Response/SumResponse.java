package com.number26.pojo.Response;

public class SumResponse {

    //Dummy Constructor for the sake of Json Mapping
    public SumResponse(){

    }

    Double sum;

    public Double getSum() {
        return sum;
    }

    public SumResponse(Double sum) {
        this.sum = sum;
    }
}
