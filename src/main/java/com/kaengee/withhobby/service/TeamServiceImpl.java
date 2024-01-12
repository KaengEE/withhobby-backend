package com.kaengee.withhobby.service;

import com.kaengee.withhobby.model.*;
import com.kaengee.withhobby.repository.TeamRepository;
import com.kaengee.withhobby.repository.UserRepository;
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
    private final UserService userService;


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

    //동아리 가입 유저 찾기


    //동아리 수정


    //동아리 삭제


}
