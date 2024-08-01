package org.hhplus.reserve.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hhplus.reserve.Business.Service.QueueRedisService;
import org.hhplus.reserve.Business.Service.TokenRedisService;
import org.hhplus.reserve.Business.Usecase.Facade.ProcessFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


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
        String userId = request.getHeader("userId"); // userId 획득
        log.info("요청 userId : " + userId);
        if (userId != null) {
            try {
                if (tokenRedisService.checkToken(userId)) {
                    log.info("유효한 고객입니다. 고객 번호: " + userId);
                    queueRedisService.saveQueue(Integer.parseInt(userId)); // 1차 대기열 진입

                    // 1차 대기열에서 대기
                    if (!processFacade.processFirstQueue(userId)) {
                        response.sendError(HttpServletResponse.SC_FORBIDDEN, "다시 시도 해주세요.");
                        return false;
                    }
                    // 2차 대기열 처리
                    if (!processFacade.processSecondQueue(userId)) {
                        response.sendError(HttpServletResponse.SC_FORBIDDEN, "다시 시도 해주세요.");
                        return false;
                    }
                    log.info("토큰 활성화 완료. 고객 번호: " + userId);
                    return true;
                }else{
                    log.info("존재하지 않은 고객입니다. 고객 번호: " + userId);
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "존재하지 않은 고객입니다.");
                }
            } catch (NumberFormatException e) {
                log.info("유효하지 않은 고객입니다. 고객 번호: " + userId);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않은 고객입니다.");
            }
        }
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "응답받은 고객이 없습니다.");
        return false;
    }
}
