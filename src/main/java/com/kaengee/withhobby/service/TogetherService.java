package com.kaengee.withhobby.service;

import com.kaengee.withhobby.model.Together;
import jakarta.transaction.Transactional;

import java.util.List;

public interface TogetherService {
    //모임 생성
    void createTogether(Long teamId, Together together, Long hostId);

    @Transactional
        //모임 수정
    void updateTogether(Together together, Long togetherId);

    @Transactional
        //모임삭제
    void deleteTogether(Long togetherId);

    //모임 조회(팀별 모임 list 조회 - teamId로 조회)
    List<Together> findTogetherById(Long togetherId);

    //모임 객체
    Together findById(Long togetherId);
}
