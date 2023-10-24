package com.smartfactory.apiserver.api.sample.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

public class SampleDTO {
    @Data
    public static class GetTBDataResponse  {
        private Long seq;
        private String name;
    }

    @Data
    public static class GetTBDataRequest{
        private Long seq;
        private String name;
    }
}
