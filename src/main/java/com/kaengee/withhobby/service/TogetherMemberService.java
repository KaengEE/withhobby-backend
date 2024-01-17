package com.kaengee.withhobby.service;

import com.kaengee.withhobby.model.TogetherMember;

import java.util.List;
import java.util.Optional;

public interface TogetherMemberService {
    //모임 참가
    TogetherMember joinTogether(Long userId, Long togetherId);

    //멤버인지 확인
    boolean isMemberOfTeam(Long userId, Long teamId);

    boolean isMemberAlreadyJoined(Long userId, Long togetherId);

    //togetherMemberId로 userId 찾기
    Optional<TogetherMember> findById(Long togetherMemberId);

    //참가취소
    void cancelTogetherByUserIdAndTogetherId(Long userId, Long togetherId);

    //모임의 참가자 조회
    List<TogetherMember> getTogetherMembersByTogetherId(Long togetherId);
}
