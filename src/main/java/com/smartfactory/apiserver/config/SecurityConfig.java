package com.smartfactory.apiserver.config;

import com.smartfactory.apiserver.common.jwt.JwtAccessDeniedHandler;
import com.smartfactory.apiserver.common.jwt.JwtAuthenticationEntryPoint;
import com.smartfactory.apiserver.common.jwt.JwtSecurityConfig;
import com.smartfactory.apiserver.common.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web){
        web.ignoring()
                .antMatchers("/swagger-ui/**");
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .exceptionHandling()
            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .accessDeniedHandler(jwtAccessDeniedHandler)

            .and()
            .headers()
            .frameOptions()
            .sameOrigin()

            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            .and()
            .authorizeRequests()
                .antMatchers("/favicon.ico").permitAll()
                .antMatchers("/api/v2/auth/sign-up", "/api/v2/auth/sign-in",
                        "/v2/api-docs", "/swagger-resources/**", "/swagger-ui/index.html", "/swagger-ui.html","/webjars/**", "/swagger/**", "/v3/api-docs/**").permitAll()
                .antMatchers("/actuator/**").permitAll()
            .anyRequest().authenticated()
                .and()
                .apply(new JwtSecurityConfig(jwtTokenProvider));
    }
}
