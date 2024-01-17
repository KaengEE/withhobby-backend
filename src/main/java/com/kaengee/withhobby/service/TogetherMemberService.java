package com.kaengee.withhobby.service;

import com.kaengee.withhobby.model.TogetherMember;

public interface TogetherMemberService {
    //모임 참가
    TogetherMember joinTogether(Long userId, Long togetherId);

    //멤버인지 확인
    boolean isMemberOfTeam(Long userId, Long teamId);

    boolean isMemberAlreadyJoined(Long userId, Long togetherId);
}
