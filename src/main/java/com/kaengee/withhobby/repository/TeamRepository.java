package com.kaengee.withhobby.repository;

import com.kaengee.withhobby.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team,Long> {

    //팀이름으로 find
    Optional<Team> findByTeamname(String teamname);

    //팀수정
    @Modifying
    @Query("update Team set teamTitle = :teamTitle, teamImg = :teamImg  where teamname = :teamname")
    void updateTeam(@Param("teamname") String teamname,
                    @Param("teamTitle") String teamTitle,
                    @Param("teamImg") String teamImg);

    //팀삭제
    @Modifying
    @Query("delete from Team where teamname = :teamname")
    void deleteTeam(@Param("teamname") String teamname);

}
