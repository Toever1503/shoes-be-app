package com.photoism.cms.common.security;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthorizationChecker {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    String checkToken(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        if (token == null) {
            request.setAttribute("expired", "expired");
            return null;
        }

        if (!jwtTokenProvider.validateToken(token)) {
            request.setAttribute("expired", "expired");
            return null;
        }

        return token;
    }

    public Boolean hasAdminOrCheckId(HttpServletRequest request, Long id) {
        log.info("======================================================================");
        log.info("userid check : {}", request.getRequestURI());
        log.info("======================================================================");
        String token = checkToken(request);
        if (token == null)
            return false;
        Long userId = Long.valueOf(jwtTokenProvider.getUserPk(token));

        // check admin or same user
        if (!jwtTokenProvider.hasRole(token, "ADMIN") && !jwtTokenProvider.hasRole(token, "SUPER_ADMIN")) {
            return id.equals(userId);
        }
        return true;
    }
}
