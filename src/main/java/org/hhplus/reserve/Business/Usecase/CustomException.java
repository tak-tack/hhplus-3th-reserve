package org.hhplus.reserve.Business.Usecase;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{
    private final ErrorCode errorCode;
    private final String requestData;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.requestData = errorCode.getRequestData();
    }
    public CustomException(ErrorCode errorCode, String requestData) {
        super(String.format("%s: %s", errorCode.getMessage(), requestData));
        this.errorCode = errorCode;
        this.requestData = requestData;
        }
    @Override
    public String getMessage() {
        return String.format("%s: %s", errorCode.getMessage(), requestData);
    }
}

