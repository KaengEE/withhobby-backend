package com.kaengee.withhobby.service;

import com.kaengee.withhobby.model.Together;
import jakarta.transaction.Transactional;

public interface TogetherService {
    //모임 생성
    void createTogether(Long teamId, Together together, Long hostId);

    @Transactional
        //모임 수정
    void updateTogether(Together together, Long togetherId);

}
