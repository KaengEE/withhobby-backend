package com.kaengee.withhobby.repository;

import com.kaengee.withhobby.model.Together;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface TogetherRepository extends JpaRepository<Together, Long> {

    //모임 내용 수정(제목,dep,장소,모임날짜)
    @Modifying
    @Query("update Together set title = :title, location = :location, " +
            "togetherDep = :togetherDep, date = :date where id = :id")
    void updateTogether(@Param("title") String title,
                        @Param("location") String location,
                        @Param("togetherDep") String togetherDep,
                        @Param("date") LocalDate date,
                        @Param("id") Long id);

    //모임 삭제

    //모임 조회(팀별 모임 list 조회 - teamId로 조회)
}
