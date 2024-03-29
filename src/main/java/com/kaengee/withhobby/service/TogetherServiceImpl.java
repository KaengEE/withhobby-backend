package com.kaengee.withhobby.service;

import com.kaengee.withhobby.model.Team;
import com.kaengee.withhobby.model.Together;
import com.kaengee.withhobby.model.TogetherMember;
import com.kaengee.withhobby.repository.TogetherMemberRepository;
import com.kaengee.withhobby.repository.TogetherRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TogetherServiceImpl implements TogetherService{

    private final TogetherRepository togetherRepository;
    private final TeamService teamService;
    private final TogetherMemberRepository togetherMemberRepository;

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

    @Override
    //모임 조회(팀별 모임 list 조회 - teamId로 조회)
    public List<Together> findTogetherById(Long togetherId) {
       return togetherRepository.findByTeamId(togetherId);
    }

    @Override
    // togetherId로 Together 객체 찾기
    public Together findById(Long togetherId) {
        return togetherRepository.findById(togetherId).orElse(null);
    }

    @Override
    public List<Together> getTogetherListByUserId(Long userId) {
        List<TogetherMember> togetherMembers = togetherMemberRepository.findByUserId(userId);

        // TogetherMember 엔터티에서 Together 엔터티를 추출
        return togetherMembers.stream()
                .map(TogetherMember::getTogether)
                .toList();
    }
}
