package org.hhplus.reserve.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hhplus.reserve.Business.Domain.Queue.QueueRedisService;
import org.hhplus.reserve.Business.Domain.User.TokenRedisService;
import org.hhplus.reserve.Business.Usecase.Facade.ProcessFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;


@Component
public class AuthInterceptor  implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(AuthInterceptor.class);

    @Autowired
    private ProcessFacade processFacade;
    @Autowired
    private TokenRedisService tokenRedisService;
    @Autowired
    private QueueRedisService queueRedisService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userUuid = request.getHeader("UUID");
        if (userUuid != null) {
            try {
                if (tokenRedisService.checkToken(userUuid)) {
                    log.info("유효한 고객입니다. 고객 번호: " + userUuid);
                }else {
                    log.info("없는 고객입니다. 토큰 발급 진행합니다. 고객 번호: " + userUuid);
                    tokenRedisService.saveToken(userUuid);
                }
                    // 1차 대기열에서 대기
                    if (!processFacade.waitingQueue(userUuid)) {
                        response.sendError(HttpServletResponse.SC_FORBIDDEN, "다시 시도 해주세요.");
                        return false;
                    }
                    // 2차 대기열 처리
                    if (!processFacade.activeQueue(userUuid)) {
                        response.sendError(HttpServletResponse.SC_FORBIDDEN, "다시 시도 해주세요.");
                        return false;
                    }
                    log.info("토큰 활성화 완료. 고객 번호: " + userUuid);
                    return true;
            } catch (NumberFormatException e) {
                log.info("유효하지 않은 고객입니다. 고객 번호: " + userUuid);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않은 고객입니다.");
            }
        }
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "응답받은 고객이 없습니다.");
        return false;
    }
}
