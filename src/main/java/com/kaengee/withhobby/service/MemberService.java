package com.kaengee.withhobby.service;

import com.kaengee.withhobby.model.Member;
import com.kaengee.withhobby.model.Team;

import java.util.List;
import java.util.Optional;

public interface MemberService {
    // 동아리에 멤버 추가
    void addMember(Member member);

    // 특정 동아리에 속한 모든 멤버 가져오기
    List<Member> getAllMembersByTeam(Team team);

    // 동아리에서 특정 멤버 삭제
    void removeMember(Team team, String username);

    //특정멤버조회
    Optional<Member> getMemberByUsernameAndTeam(Team team, String username);

    // 동아리에서 전체멤버 삭제
    void removeMemberFromTeam(Member member);
}
