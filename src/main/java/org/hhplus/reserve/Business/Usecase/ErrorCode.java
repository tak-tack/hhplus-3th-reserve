package org.hhplus.reserve.Business.Usecase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

//    SUCCESS_CODE("성공적으로 작업을 수행 했습니다.", 1000, HttpStatus.OK),
//    SERVER_INTERVAL_ERROR("서버 내부적 오류입니다.",1002,HttpStatus.BAD_REQUEST),
//    NOT_FOUND_USER("유저를 찾을 수 없습니다.",1004,HttpStatus.BAD_REQUEST),
//    LOCKING_FAILURE("Optimistic locking failure.",1009,HttpStatus.BAD_REQUEST);
//
//    private final String description;
//    private final int CustomCode;
//    private final HttpStatus httpStatus;

    USER_NOT_FOUND("E001", "유저를 찾을수없습니다. userId : ",""),
    USER_DUPLICATED("E002","이미 등록된 유저입니다. userId : ",""),
    INSUFFICIENT_BALANCE("E100", "잔액이 부족합니다 ",""),
    CONCERT_NOT_FOUND("E200","등록된 콘서트가 없습니다.",""),
    INVALID_INPUT("E003", "Invalid input provided",""),
    INTERNAL_SERVER_ERROR("E500", "Internal server error","");

    private final String code;
    private final String message;
    private final String requestData;

}
