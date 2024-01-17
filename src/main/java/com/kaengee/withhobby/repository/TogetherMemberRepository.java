package com.kaengee.withhobby.repository;

import com.kaengee.withhobby.model.TogetherMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TogetherMemberRepository extends JpaRepository<TogetherMember, Long> {

    // togetherMemberId로 userId 찾기
    Optional<TogetherMember> findById(Long togetherMemberId);
    //이미 참여했는지 확인
    Optional<TogetherMember> findByUserIdAndTogetherId(Long userId, Long togetherId);

    //참가 취소
    void deleteByUserIdAndTogetherId(Long userId, Long togetherId);

    //참가자 조회

}
