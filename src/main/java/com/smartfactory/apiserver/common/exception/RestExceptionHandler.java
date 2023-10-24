package com.smartfactory.apiserver.common.exception;

import com.smartfactory.apiserver.common.response.ApiResponseCode;
import com.smartfactory.apiserver.common.response.BaseResponse;
import com.smartfactory.apiserver.common.response.RestApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice(annotations= RestController.class)
@Slf4j
public class RestExceptionHandler {
    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<?> handleBusinessException(HttpServletRequest request, BusinessException e){
        RestApiResponse responseDTO = new RestApiResponse();
        responseDTO.setResult(new BaseResponse(e.getResponseCode(), e.getDetail()));
        return new ResponseEntity<RestApiResponse>(responseDTO, e.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage();
        log.error(errorMessage);
        RestApiResponse responseDTO = new RestApiResponse();
        responseDTO.setResult(new BaseResponse(ApiResponseCode.INVALID_PARAMETER_ERR, errorMessage));
        return new ResponseEntity<RestApiResponse>(responseDTO, HttpStatus.BAD_REQUEST);
    }
}
