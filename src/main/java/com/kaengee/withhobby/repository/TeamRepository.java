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


    // 해당 카테고리에 속한 팀들을 찾는 메서드
    List<Team> findByCategory(Category category);
}
