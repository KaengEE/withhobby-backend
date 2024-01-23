package com.kaengee.withhobby.service;

import com.kaengee.withhobby.model.*;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public interface TeamService {

    //동아리 등록
    Team saveTeam(Team team, User user);

    //teamform => team
    Team transToTeam(TeamForm teamForm);

    //카테고리별로 동아리 조회
    List<Team> findTeamByCategory(Category category);

    //동아리 카테고리 이동
    @Transactional
    void updateTeamCategory(Long teamId, Category newCategoryId);


    //팀 아이디로 팀 이름 찾기 getTeamById
    Team getTeamById(Long teamId);

    //팀 이름으로 host_id 찾기
    Long findTeamHostId(String teamname);


    //팀ID로 HOST_ID 찾기
    Optional<Team> findTeamHostIdByTeamId(Long teamId);


    //동아리 수정(이름,내용,이미지)
    @Transactional
    void updateTeam(TeamForm teamForm, Long teamId);

    //동아리 삭제
    @Transactional
    void deleteTeam(String teamname);

    //팀 아이디 찾기
    Long findTeamId(String teamname);


    //동아리 삭제
}
