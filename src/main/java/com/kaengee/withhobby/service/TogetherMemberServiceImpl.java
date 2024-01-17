package com.kaengee.withhobby.service;

import com.kaengee.withhobby.model.Together;
import com.kaengee.withhobby.model.TogetherMember;
import com.kaengee.withhobby.repository.TogetherMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TogetherMemberServiceImpl implements TogetherMemberService{

    private final TogetherMemberRepository togetherMemberRepository;
    private final TogetherService togetherService;

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
}
