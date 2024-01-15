package com.kaengee.withhobby.service;

import com.kaengee.withhobby.model.Member;
import com.kaengee.withhobby.model.Team;
import com.kaengee.withhobby.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    @Override
    // 동아리에 멤버 추가
    public void addMember(Member member) {
        memberRepository.save(member);
    }

    @Override
    // 특정 동아리에 속한 모든 멤버 가져오기
    public List<Member> getAllMembersByTeam(Team team) {
        return memberRepository.findByTeam(team);
    }

    @Override
    // 동아리에서 특정 멤버 삭제
    public void removeMember(Team team, String username) {
        memberRepository.deleteByTeamAndUsername(team, username);
    }

    @Override
    //특정멤버조회
    public Optional<Member> getMemberByUsernameAndTeam(Team team, String username) {
        return memberRepository.findByTeamAndUsername(team, username);
    }

    @Override
    // 동아리에서 전체멤버 삭제
    public void removeMemberFromTeam(Member member) {
        memberRepository.delete(member);
    }

    @Override
    // 동아리별 멤버조회
    public List<Member> getMembersByTeamId(Long teamId) {
        return memberRepository.findByTeamId(teamId);
    }

}
