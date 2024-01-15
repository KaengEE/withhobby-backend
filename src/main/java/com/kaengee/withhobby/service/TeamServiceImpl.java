package com.kaengee.withhobby.service;

import com.kaengee.withhobby.model.*;
import com.kaengee.withhobby.repository.MemberRepository;
import com.kaengee.withhobby.repository.TeamRepository;
import com.kaengee.withhobby.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final CategoryService categoryService;



    //동아리 등록
    @Override
    public Team saveTeam(Team team, User user) {
        team.setTeamname(team.getTeamname());
        team.setTeamTitle(team.getTeamTitle());
        team.setTeamImg(team.getTeamImg());
        team.setCreateAt(LocalDateTime.now());
        team.setCategory(team.getCategory());
        team.setTeamHost(user); //현재 로그인된 유저가 host가 됨

        //동아리만든 유저의 status를 MASTER로 변경
        if (user != null) {
            user.setUserStatus(Status.MASTER);
            userRepository.save(user);
        }
        //System.out.println(team);
        return teamRepository.save(team);
    }

    @Override
    //teamform => team
    public Team transToTeam(TeamForm teamForm){
        Team team = new Team();
        team.setTeamname(teamForm.getTeamname());
        team.setTeamTitle(teamForm.getTeamTitle());
        team.setTeamImg(teamForm.getTeamImg());

        Optional<Category> category =categoryService.findByCategory(teamForm.getCategory());
        if(category.isPresent()){
            //System.out.println(category.get());
            team.setCategory(category.get());
        }

        return team;
    }

    //동아리 카테고리 이동
    @Transactional
    @Override
    public void updateTeamCategory(Long teamId, Category newCategoryId) {
        // 1. 해당 팀 가져오기
        Optional<Team> teamOptional = teamRepository.findById(teamId);

        if (teamOptional.isPresent()) {
            Team teamToUpdate = teamOptional.get();

            // 2. 새로운 카테고리로 수정
            teamToUpdate.setCategory(newCategoryId);

            // 3. 업데이트된 팀 저장
            teamRepository.save(teamToUpdate);
        } else {
            throw new EntityNotFoundException("팀 아이디를 찾을 수 없음.: " + teamId);
        }
    }


    //팀 아이디로 팀 이름 찾기 getTeamById
    @Override
    public Team getTeamById(Long teamId) {
        Optional<Team> teamOptional = teamRepository.findById(teamId);
        return teamOptional.orElse(null);

    }

    @Override
    //팀 이름으로 host_id 찾기
    public Long findTeamHostId(String teamname) {
        return teamRepository.findTeamHostId(teamname);
    }

    //동아리 수정(이름,내용,이미지)
    @Transactional
    @Override
    public void updateTeam(TeamForm teamForm){
        teamRepository.updateTeam(teamForm.getTeamname(), teamForm.getTeamTitle(), teamForm.getTeamImg());
    }


    //동아리 삭제
    @Transactional
    @Override
    public void deleteTeam(String teamname) {
            teamRepository.deleteTeam(teamname);
    }

    //팀 아이디 찾기
    @Override
    public Long findTeamId(String teamname){
        return teamRepository.findTeamId(teamname);
    }


}
