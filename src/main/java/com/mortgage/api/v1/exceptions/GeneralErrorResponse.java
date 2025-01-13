package com.mortgage.api.v1.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;


@Getter
@Setter
public class GeneralErrorResponse {

    private String id;
    private int httpStatus;
    private String message;
    private ZonedDateTime utcTimestamp;

    public GeneralErrorResponse(
            String id, int status, String message, ZonedDateTime utcTimestamp
    ) {
        this.id = id;
        this.httpStatus = status;
        this.message = message;
        this.utcTimestamp = utcTimestamp;
    }

}
