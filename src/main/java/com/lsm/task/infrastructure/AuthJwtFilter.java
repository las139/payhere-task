package com.lsm.task.infrastructure;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthJwtFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException,
                                                                                                                              IOException {
        try {
            String jwt = jwtTokenProvider.getJwtFromRequestHeader(request);
            if (!StringUtils.hasText(jwt)) {
                throw new SignatureException("토큰이 존재하지 않습니다.");
            }

            String phoneNumber = jwtTokenProvider.getPayload(jwt);
            if (!StringUtils.hasText(phoneNumber)) {
                throw new SignatureException("토큰의 정보가 유효하지 않습니다.");
            }

            if (jwtTokenProvider.validateToken(jwt)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                throw new SignatureException("토큰이 유효하지 않습니다.");
            }
        } catch (ExpiredJwtException e) {
            request.setAttribute("jwtExceptionMessage", "토큰이 만료되었습니다.");
        } catch (SignatureException e) {
            request.setAttribute("jwtExceptionMessage", e.getMessage());
        } catch (JwtException e) {
            request.setAttribute("jwtExceptionMessage", "토큰 인증 과정 중 오류가 발생하였습니다.");
        }

        filterChain.doFilter(request, response);
    }
}
