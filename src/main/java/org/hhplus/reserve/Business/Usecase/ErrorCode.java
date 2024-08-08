package org.hhplus.reserve.Business.Usecase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    USER_NOT_FOUND("E001", "유저를 찾을수없습니다. userId : ",""),
    USER_DUPLICATED("E002","이미 등록된 유저입니다. userId : ",""),
    USER_COUNT_MAX("E003","처리중입니다. 잠시만 기다려주세요.",""),
    INSUFFICIENT_BALANCE("E100", "잔액이 부족합니다 ",""),
    CONCERT_NOT_FOUND("E200","등록된 콘서트가 없습니다.",""),
    INVALID_INPUT("E003", "Invalid input provided",""),
    INTERNAL_SERVER_ERROR("E500", "Internal server error","");


    private final String code;
    private final String message;
    private final String requestData;

}
