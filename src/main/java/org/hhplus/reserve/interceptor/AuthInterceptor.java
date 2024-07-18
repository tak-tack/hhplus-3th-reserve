package org.hhplus.reserve.Presentation.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


@Component
public class AuthInterceptor  implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(AuthInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        long startTime = System.currentTimeMillis();
        System.out.println("Pre Handle method is Calling");
        log.debug("Request URL::" + request.getRequestURL().toString() + ":: "
                + "Start Time=" + System.currentTimeMillis());
        request.setAttribute("startTime", startTime);
        return true;

    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws Exception {
        log.debug("Request URL::" + request.getRequestURL().toString() + " Sent to Handler :: "
                + "Current Time=" + System.currentTimeMillis());
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception exception) throws Exception {
        // After Completion logic
        System.out.println("afterCompletion1");
    }

}
