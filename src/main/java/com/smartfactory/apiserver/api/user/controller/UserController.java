package com.smartfactory.apiserver.api.user.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    @GetMapping(value = "user-info", produces = "application/json")
    public ResponseEntity<?> getUserInformation(@RequestParam Long userSeq){
        return null;
    }
}
