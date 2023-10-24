package com.smartfactory.apiserver.common.filter;

import com.smartfactory.apiserver.common.constant.Constant;
import com.smartfactory.apiserver.common.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private static final List<String> EXCLUDE_URL =
            Collections.unmodifiableList(
                    Arrays.asList(
                            "/api/v2/auth/sign-up",
                            "/api/v2/auth/sign-in",
                            "/api-docs"
                    ));

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getServletPath().contains("actuator")){
            filterChain.doFilter(request, response);
        }
        String token = this.resolveJwtToken(request);
        boolean tokenValidation = true;
        if( token != null ){
            tokenValidation = jwtTokenProvider.validateToken(token, request);
        }
        if(token != null && tokenValidation){
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }else{
            throw new RuntimeException();
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        boolean excludeUri = EXCLUDE_URL.stream().anyMatch(exclude -> exclude.equalsIgnoreCase(request.getServletPath()));
        if(request.getServletPath().contains("actuator") || request.getServletPath().contains("swagger") || request.getServletPath().contains("docs")){
            return true;
        }
        return excludeUri;
    }

    private String resolveJwtToken(HttpServletRequest request){
        String token = request.getHeader(AUTHORIZATION_HEADER);
        if(StringUtils.isEmpty(token)){
            token = null;
        }
        return token;
//        String clientId = request.getHeader("client-id");
//        String clientSecret = request.getHeader("client-secret");
//        if( clientId == null || clientSecret == null){
//            request.setAttribute("exception", "client");
//            throw new RuntimeException();
//        }
//        if( clientId.equals(Constant.CLIENT_ID) && clientSecret.equals(Constant.CLIENT_SECRET )){
//            return token;
//        }else{
//            request.setAttribute("exception", "client");
//            throw new RuntimeException();
//        }
    }
}
