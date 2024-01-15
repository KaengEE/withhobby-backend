package com.kaengee.withhobby.repository;

import com.kaengee.withhobby.model.Category;
import com.kaengee.withhobby.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
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


    //특정 팀 카테고리 이동
    @Modifying
    @Query("update Team t set t.category.id = :newCategoryId where t.id = :teamId")
    void updateTeamCategory(@Param("teamId") Team teamId, @Param("newCategoryId") Category newCategoryId);

    //삭제시 카테고리 이동
    @Modifying
    @Query("update Team t set t.category = :otherCategory where t.category = :category")
    void moveTeamsToOtherCategory(@Param("category") Category category, @Param("otherCategory") Category otherCategory);

    // 해당 카테고리id로 팀들을 찾는 메서드
    List<Team> findTeamByCategory(Category categoryId);

    // 팀 이름을 통해 team_host_id 가져오기
    @Query("select t.teamHost.id from Team t where t.teamname = :teamname")
    Long findTeamHostId(@Param("teamname") String teamname);

    // 팀 이름을 통해 team_id 가져오기
    @Query("SELECT id from Team where teamname = :teamname")
    Long findTeamId(@Param("teamname") String teamname);

}
