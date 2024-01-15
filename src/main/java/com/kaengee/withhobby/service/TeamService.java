package com.kaengee.withhobby.service;

import com.kaengee.withhobby.model.Category;
import com.kaengee.withhobby.model.Team;
import com.kaengee.withhobby.model.TeamForm;
import com.kaengee.withhobby.model.User;
import jakarta.transaction.Transactional;

public interface TeamService {

    //동아리 등록
    Team saveTeam(Team team, User user);

    //teamform => team
    Team transToTeam(TeamForm teamForm);

    //동아리 카테고리 이동
    @Transactional
    void updateTeamCategory(Long teamId, Category newCategoryId);


    //팀 아이디로 팀 이름 찾기 getTeamById
    Team getTeamById(Long teamId);

    //팀 이름으로 host_id 찾기
    Long findTeamHostId(String teamname);

    //동아리 수정(내용,이미지)
    @Transactional
    void updateTeam(TeamForm teamForm);

    //동아리 삭제
    @Transactional
    void deleteTeam(String teamname);

    //팀 아이디 찾기
    Long findTeamId(String teamname);


    //동아리 삭제
}
