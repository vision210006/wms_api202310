package com.smartfactory.apiserver.common.jwt;

import com.smartfactory.apiserver.api.auth.dto.AuthDTO.TokenInfo;
import com.smartfactory.apiserver.common.util.StringUtil;
import com.smartfactory.apiserver.domain.database.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtTokenProvider implements InitializingBean {
    private final UserRepository userRepository;
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.token-validity-in-second}")
    private long tokenValidityInMilliseconds;
    private static final String AUTHORITIES_KEY = "auth";
    private Key key;

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenInfo createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date accessTokenValidity = new Date(now + this.tokenValidityInMilliseconds * 1000);
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(accessTokenValidity)
                .compact();
        String refreshToken = StringUtil.generateRefreshToken();
        Date refreshTokenValidity = new Date(now + this.tokenValidityInMilliseconds * 2 * 1000);
        TokenInfo tokenInfo = TokenInfo.builder().accessToken(accessToken)
                .refreshToken(refreshToken).refreshTokenExpireAt(refreshTokenValidity.getTime()).build();

        return tokenInfo;
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        User user = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(user, token, authorities);
    }
    public Authentication getVisitorAuthentication(){
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("visitor"));
        User user = new User("visitor", "", authorities);
        return new UsernamePasswordAuthenticationToken(user, "visitor", authorities);
    }

    public boolean validateToken(String token, HttpServletRequest request) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            request.setAttribute("exception", "invalid");
            log.error("잘못된 JWT 서명입니다.");
            throw e;
        } catch (ExpiredJwtException e) {
            request.setAttribute("exception", "expire");
            log.error("만료된 JWT 토큰입니다.");
            throw e;
        } catch (UnsupportedJwtException e) {
            request.setAttribute("exception", "invalid");
            log.error("지원되지 않는 JWT 토큰입니다.");
            throw e;
        } catch (IllegalArgumentException e) {
            request.setAttribute("exception", "invalid");
            log.info("JWT 토큰이 잘못되었습니다.");
            throw e;
        }
    }
}