package com.kaengee.withhobby.service;

import com.kaengee.withhobby.model.Together;

public interface TogetherService {
    //모임 생성
    Together createTogether(Long teamId, Together together, Long hostId);
}
