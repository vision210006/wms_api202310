package com.smartfactory.apiserver.api.sample.controller;

import com.smartfactory.apiserver.api.sample.dto.SampleDTO.GetTBDataRequest;
import com.smartfactory.apiserver.api.sample.dto.SampleDTO.GetTBDataResponse;
import com.smartfactory.apiserver.api.sample.service.SampleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sample")
@Slf4j
@RequiredArgsConstructor
public class SampleController {

}
