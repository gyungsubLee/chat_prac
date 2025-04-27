package com.example.chat.common.auth;

import com.example.chat.common.exception.JwtErrorResponder;
import com.example.chat.common.exception.custom.InvalidAuthHeaderException;
import com.example.chat.common.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends GenericFilter {

    private final JwtErrorResponder errorResponder;

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String token = httpServletRequest.getHeader("Authorization");

        try {
            if (token != null) {
                if (!token.startsWith("Bearer ")) {
                    throw new InvalidAuthHeaderException();
                }

                String jwtToken = token.substring(7);

                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(secretKey)
                        .build()
                        .parseClaimsJws(jwtToken)
                        .getBody();

                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("ROLE_" + claims.get("role")));

                UserDetails userDetails = new User(claims.getSubject(), "", authorities);
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, "", userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            chain.doFilter(request, response);
        } catch (InvalidAuthHeaderException e) {
            log.warn("Authorization 헤더 형식 오류", e);
            errorResponder.sendErrorResponse(httpServletRequest, httpServletResponse, ErrorCode.INVALID_AUTH_HEADER);
        } catch (io.jsonwebtoken.JwtException e) {
            log.warn("JWT 토큰 파싱 오류", e);
            errorResponder.sendErrorResponse(httpServletRequest, httpServletResponse, ErrorCode.INVALID_TOKEN);
        } catch (Exception e) {
            log.error("Unhandled Exception 발생", e);
            errorResponder.sendErrorResponse(httpServletRequest, httpServletResponse, ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}