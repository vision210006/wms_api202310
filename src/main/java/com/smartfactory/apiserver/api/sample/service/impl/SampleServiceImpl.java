package com.smartfactory.apiserver.api.sample.service.impl;

import com.smartfactory.apiserver.api.sample.dto.SampleDTO.GetTBDataResponse;
import com.smartfactory.apiserver.api.sample.service.SampleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jdt.core.compiler.InvalidInputException;
import org.springframework.stereotype.Service;

//@Service("smapleService")
//@RequiredArgsConstructor
//@Slf4j
//public class SampleServiceImpl implements SampleService {
//    private final TBTestRepository tbTestRepository;
//    public String getTestString(){
//
//        TBTestEntity entity = TBTestEntity.builder().name("스마트팩토리").build();
//        tbTestRepository.save(entity);
//        return "testString";
//    }
//
//    public GetTBDataResponse getTestDataBySeq(Long seq) {
//        try {
//            TBTestEntity entity = tbTestRepository.findTbTestEntityBySeqAndName(seq, "스마트").orElseThrow(() -> new InvalidInputException());
//            GetTBDataResponse getTBDataResponse = new GetTBDataResponse();
//            getTBDataResponse.setSeq(entity.getSeq());
//            getTBDataResponse.setName(entity.getName());
//            return getTBDataResponse;
//        }catch (Exception e){
//            log.error(e.getMessage());
//            throw new RuntimeException("FAILED TO getTestDataBySeq");
//        }
//    }
//}
