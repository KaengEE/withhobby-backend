package com.kaengee.withhobby.repository;

import com.kaengee.withhobby.model.TogetherMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TogetherMemberRepository extends JpaRepository<TogetherMember, Long> {

    //참가는 save

    //참가 취소

}
