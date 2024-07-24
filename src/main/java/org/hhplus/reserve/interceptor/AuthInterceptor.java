package org.hhplus.reserve.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hhplus.reserve.Business.Service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
public class AuthInterceptor  implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(AuthInterceptor.class);

    @Autowired
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userIdStr = request.getHeader("userId"); // userId 획득
        if (userIdStr != null) {
            try {
                Integer userId = Integer.parseInt(userIdStr); // 순수 어플리케이션 연산. 부하아님.
                if (tokenService.checkAuth(userId).getUserId() != null) {
                    log.info("유효한 고객 입니다. 고객 번호 : " + userId);
                    return true;
                }
            } catch (NumberFormatException  e) {
                log.warn("유효하지않은 고객 입니다.");
            }
        }
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "응답받은 고객이 없습니다."); // 에러컨트롤러감. 인터셉터에서는 advice 로 해됨.
        return false;

    }

//    @Override
//    public void postHandle(HttpServletRequest request,
//                           HttpServletResponse response,
//                           Object handler,
//                           ModelAndView modelAndView) throws Exception {
//        // 토큰만료 구현예정.
//    }


}
