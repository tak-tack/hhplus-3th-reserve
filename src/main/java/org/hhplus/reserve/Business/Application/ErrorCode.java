package org.hhplus.reserve.Business.Application;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    SUCCESS_CODE("성공적으로 작업을 수행 했습니다.", 1000, HttpStatus.OK),
    SERVER_INTERVAL_ERROR("서버 내부적 오류입니다.",1002,HttpStatus.BAD_REQUEST),
    NOT_FOUND_USER("유저를 찾을 수 없습니다.",1004,HttpStatus.BAD_REQUEST),
    LOCKING_FAILURE("Optimistic locking failure.",1009,HttpStatus.BAD_REQUEST);

    private final String description;
    private final int CustomCode;
    private final HttpStatus httpStatus;
}
