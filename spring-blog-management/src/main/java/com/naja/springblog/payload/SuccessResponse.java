package com.naja.springblog.payload;

public class SuccessResponse extends AppResponse {

    public SuccessResponse(String message) {
        super(true, message);

    }

}
