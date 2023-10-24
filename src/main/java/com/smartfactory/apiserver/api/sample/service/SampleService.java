package com.smartfactory.apiserver.api.sample.service;

import com.smartfactory.apiserver.api.sample.dto.SampleDTO.GetTBDataResponse;

public interface SampleService {
    String getTestString();
    GetTBDataResponse getTestDataBySeq(Long seq) throws Exception;
}
