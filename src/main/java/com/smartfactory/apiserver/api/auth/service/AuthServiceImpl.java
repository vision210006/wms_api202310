package com.smartfactory.apiserver.api.auth.service;

import com.smartfactory.apiserver.api.auth.dto.AuthDTO.SignInRequest;
import com.smartfactory.apiserver.api.auth.dto.AuthDTO.SignUpRequest;
import com.smartfactory.apiserver.api.auth.dto.AuthDTO.TokenInfo;
import com.smartfactory.apiserver.common.constant.CommonCode.UserAuthority;
import com.smartfactory.apiserver.common.constant.CommonCode.UserStatus;
import com.smartfactory.apiserver.common.exception.BusinessException;
import com.smartfactory.apiserver.common.exception.auth.AuthException;
import com.smartfactory.apiserver.common.jwt.JwtTokenProvider;
import com.smartfactory.apiserver.common.response.ApiResponseCode;
import com.smartfactory.apiserver.common.util.AES256Util;
import com.smartfactory.apiserver.domain.database.entity.UserAuthorityEntity;
import com.smartfactory.apiserver.domain.database.entity.UserEntity;
import com.smartfactory.apiserver.domain.database.repository.UserAuthorityRepository;
import com.smartfactory.apiserver.domain.database.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service("AuthService")
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService, UserDetailsService {
    private final UserRepository userRepository;
    private final UserAuthorityRepository userAuthorityRepository;

    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public void signUp(SignUpRequest signUpRequest){
        try{
            UserEntity userEntity = UserEntity.builder()
                    .userId(signUpRequest.getUserId())
                    .password(passwordEncoder.encode(signUpRequest.getPassword()))
                    .userName(signUpRequest.getUserName())
                    .phoneNumber(AES256Util.encrypt(signUpRequest.getPhoneNumber()))
                    .emailAddress(AES256Util.encrypt(signUpRequest.getEmailAddress()))
                    .createId(signUpRequest.getUserId())
                    .userStatus(UserStatus.USE)
                    .build();
            userEntity = userRepository.save(userEntity);

            UserAuthorityEntity authority = UserAuthorityEntity.builder()
                    .user(userEntity)
                    .authority(UserAuthority.ROLE_GENERAL)
                    .createId(userEntity.getUserId())
                    .build();
            userAuthorityRepository.save(authority);

        }catch(Exception e){
            log.error(e.getMessage());
            throw new BusinessException(ApiResponseCode.FAILED_SIGN_UP_USER, HttpStatus.BAD_REQUEST);
        }
    }

    public TokenInfo signIn(SignInRequest signInRequest){

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(signInRequest.getUserId(), signInRequest.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        TokenInfo jwtToken = jwtTokenProvider.createToken(authentication);
        UserEntity userEntity = userRepository.findUserEntityByUserId(signInRequest.getUserId()).orElseThrow( () -> new BusinessException(ApiResponseCode.FAILED_SIGN_IN_USER, HttpStatus.BAD_REQUEST));
        userEntity.setRefreshToken(jwtToken.getRefreshToken());
        userEntity.setRefreshTokenExpireAt(jwtToken.getRefreshTokenExpireAt());
        userRepository.save(userEntity);
        return jwtToken;
    }
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findUserEntityByUserId(username).orElseThrow( () -> new BusinessException(ApiResponseCode.FAILED_SIGN_IN_USER, HttpStatus.BAD_REQUEST));
        User userDetail = this.createUser(userEntity);
        return userDetail;
    }

    private User createUser(UserEntity userEntity){
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for(UserAuthorityEntity userAuthorityEntity : userEntity.getUserAuthorityEntities()){
            grantedAuthorities.add(new SimpleGrantedAuthority(userAuthorityEntity.getAuthority().toString()));
        }
        return new User(userEntity.getUserId(), userEntity.getPassword(), grantedAuthorities);
    }

}
