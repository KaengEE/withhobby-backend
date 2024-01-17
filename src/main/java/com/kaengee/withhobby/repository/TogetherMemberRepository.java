package com.kaengee.withhobby.repository;

import com.kaengee.withhobby.model.TogetherMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TogetherMemberRepository extends JpaRepository<TogetherMember, Long> {

    //이미 참여했는지 확인
    Optional<TogetherMember> findByUserIdAndTogetherId(Long userId, Long togetherId);

    //참가 취소
    @Modifying
    @Query("delete from TogetherMember where id = :id")
    void cancelTogether(@Param("id")Long id);

    //참가자 조회

}
