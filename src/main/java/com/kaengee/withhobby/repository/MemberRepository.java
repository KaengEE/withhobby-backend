package com.kaengee.withhobby.repository;

import com.kaengee.withhobby.model.Member;
import com.kaengee.withhobby.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    // 특정 동아리에 속한 모든 멤버 가져오기
    List<Member> findByTeam(Team team);

    // 동아리에서 특정 멤버 삭제
    void deleteByTeamAndUsername(Team team, String username);

    //특정멤버조회
    // 동아리에서 특정 멤버 조회
    Optional<Member> findByTeamAndUsername(Team team, String username);

    //팀별로 멤버조회
    List<Member> findByTeamId(Long team);

    //username와 teamId로 해당팀의 멤버인지확인
    Optional<Member> findByUsernameAndTeamId(String username, Long teamId);

    //userId로 member 찾기
    List<Member> findByUsername(String username);
}
