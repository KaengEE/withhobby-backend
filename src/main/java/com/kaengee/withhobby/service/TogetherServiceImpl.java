package com.kaengee.withhobby.service;

import com.kaengee.withhobby.model.Team;
import com.kaengee.withhobby.model.Together;
import com.kaengee.withhobby.repository.TogetherRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TogetherServiceImpl implements TogetherService{

    private final TogetherRepository togetherRepository;
    private final TeamService teamService;
    private final UserService userService;

    @Transactional
    @Override
    //모임 생성
    public Together createTogether(Long teamId, Together together, Long hostId){

            Team team = teamService.getTeamById(teamId);

            // 모임 생성 및 설정
            together.setTeam(team); //팀객체
            together.setTitle(together.getTitle()); //모임제목
            together.setTogetherDep(together.getTogetherDep()); //모임설명
            together.setLocation(together.getLocation()); //모임장소
            together.setDate(together.getDate()); //모임날짜

            return togetherRepository.save(together);

    }

}
