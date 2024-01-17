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

    @Transactional
    @Override
    //모임 생성
    public void createTogether(Long teamId, Together together, Long hostId){

            Team team = teamService.getTeamById(teamId);

            // 모임 생성 및 설정
            together.setTeam(team); //팀객체
            together.setTitle(together.getTitle()); //모임제목
            together.setTogetherDep(together.getTogetherDep()); //모임설명
            together.setLocation(together.getLocation()); //모임장소
            together.setDate(together.getDate()); //모임날짜

        togetherRepository.save(together);

    }

    @Transactional
    @Override
    //모임 수정
    public void updateTogether(Together together, Long togetherId) {
        //수정된 내용 저장
        togetherRepository.updateTogether(
                together.getTitle(),
                together.getLocation(),
                together.getTogetherDep(),
                together.getDate(),
                togetherId
        );
    }


    @Transactional
    @Override
    //모임삭제
    public void deleteTogether(Long togetherId){
        togetherRepository.deleteTogether(togetherId);
    }

}
