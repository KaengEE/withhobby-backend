package com.kaengee.withhobby.service;

import com.kaengee.withhobby.model.TogetherMember;

public interface TogetherMemberService {
    //모임 참가
    TogetherMember joinTogether(Long userId, Long togetherId);
}
