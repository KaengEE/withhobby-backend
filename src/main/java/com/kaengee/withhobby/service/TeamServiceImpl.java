package com.kaengee.withhobby.service;

import com.kaengee.withhobby.model.*;
import com.kaengee.withhobby.repository.TeamRepository;
import com.kaengee.withhobby.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final CategoryService categoryService;


    //동아리 등록
    @Override
    public Team saveTeam(Team team, User loggedInUser) {
        team.setTeamname(team.getTeamname());
        team.setTeamTitle(team.getTeamTitle());
        team.setTeamHost(team.getTeamHost());
        team.setTeamImg(team.getTeamImg());
        team.setCreateAt(LocalDateTime.now());
        team.setTeamHost(loggedInUser); //현재 로그인된 유저가 host가 됨

        // team_host를 멤버에 추가
        Set<User> members = new HashSet<>();
        members.add(loggedInUser); // 호스트를 멤버에 추가
        team.setMembers(members);

        // 동아리를 만든 사람이 동아리 호스트이면서 동시에 동아리 멤버
        //동아리만든 유저의 status를 MASTER로 변경
        if (team.getTeamHost() != null) {
            User hostUser = team.getTeamHost();
            hostUser.setUserStatus(Status.MASTER);
            hostUser.getTeams().add(team); // team 추가
            userRepository.save(hostUser);
        }

        return teamRepository.save(team);
    }

    @Override
    //teamform => team
    public Team transToTeam(TeamForm teamForm){
        Team team = new Team();
        team.setTeamname(teamForm.getTeamname());
        team.setTeamTitle(teamForm.getTeamTitle());
        //현재 로그인된 유저의 정보를 저장
        team.setTeamHost(teamForm.getTeamHost());
        team.setTeamImg(teamForm.getTeamImg());
        Optional<Category> category =categoryService.findByCategory(teamForm.getCategory());
        category.ifPresent(team::setCategory);
        return team;
    }

    //동아리 가입 유저 찾기


    //동아리 수정


    //동아리 삭제


}
