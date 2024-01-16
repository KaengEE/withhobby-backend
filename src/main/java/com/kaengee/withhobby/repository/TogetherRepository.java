package com.kaengee.withhobby.repository;

import com.kaengee.withhobby.model.Together;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TogetherRepository extends JpaRepository<Together, Long> {

    //모임 내용 수정(제목,dep,모임날짜)

    //모임 삭제

    //모임 조회(팀별 모임 list 조회)
}
