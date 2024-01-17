package com.kaengee.withhobby.service;

import com.kaengee.withhobby.model.Together;
import com.kaengee.withhobby.model.TogetherMember;
import com.kaengee.withhobby.model.User;
import com.kaengee.withhobby.repository.MemberRepository;
import com.kaengee.withhobby.repository.TogetherMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TogetherMemberServiceImpl implements TogetherMemberService{

    private final TogetherMemberRepository togetherMemberRepository;
    private final TogetherService togetherService;
    private final MemberRepository memberRepository;
    private final UserService userService;

    @Override
    //모임 참가
    public TogetherMember joinTogether(Long userId, Long togetherId){
        TogetherMember togetherMember = new TogetherMember();
        togetherMember.setUserId(userId);
        //togetherId로 togehter객체 찾기
        Together together =togetherService.findById(togetherId);
        togetherMember.setTogether(together);

        return togetherMemberRepository.save(togetherMember);
    }

    @Override
    // username과 teamId 이용하여 멤버 테이블에서 해당 유저가 특정 팀의 멤버인지 확인
    public boolean isMemberOfTeam(Long userId, Long teamId) {
        // Member 테이블에서 username과 togetherId로 조회 후 결과가 있으면 멤버이고, 없으면 멤버가 아님
        Optional<User> user = userService.findById(userId);

        if(user.isPresent()) {
            //userId로 유저네임찾기
            String username = user.get().getUsername();
            //System.out.println(username);
            return memberRepository.findByUsernameAndTeamId(username, teamId).isPresent();
        } else {
            return false;
        }
    }

    @Override
    public boolean isMemberAlreadyJoined(Long userId, Long togetherId) {
        // 이미 참여한 멤버인지 확인
        Optional<TogetherMember> existingMember = togetherMemberRepository.findByUserIdAndTogetherId(userId, togetherId);
        return existingMember.isPresent();
    }
}
