package com.ComoPaPobre.main.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class AuthenticationEventListener implements
        ApplicationListener<org.springframework.context.ApplicationEvent> {

    private final LoginAttemptService loginAttemptService;

    public AuthenticationEventListener(LoginAttemptService loginAttemptService) {
        this.loginAttemptService = loginAttemptService;
    }

    @Override
    public void onApplicationEvent(org.springframework.context.ApplicationEvent event) {
        String ip = getClientIP();

        if (ip == null) return;

        if (event instanceof AuthenticationFailureBadCredentialsEvent) {
            loginAttemptService.loginFailed(ip);
        } else if (event instanceof AuthenticationSuccessEvent) {
            loginAttemptService.loginSucceeded(ip);
        }
    }

    private String getClientIP() {
        RequestAttributes attrs = RequestContextHolder.getRequestAttributes();
        if (attrs instanceof ServletRequestAttributes requestAttributes) {
            HttpServletRequest request = requestAttributes.getRequest();
            String xfHeader = request.getHeader("X-Forwarded-For");
            return xfHeader == null ? request.getRemoteAddr() : xfHeader.split(",")[0];
        }
        return null; 
    }
}
