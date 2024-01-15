package com.kaengee.withhobby.controller;

import com.kaengee.withhobby.model.Member;
import com.kaengee.withhobby.model.Status;
import com.kaengee.withhobby.model.Team;
import com.kaengee.withhobby.security.UserPrinciple;
import com.kaengee.withhobby.service.MemberService;
import com.kaengee.withhobby.service.TeamService;
import com.kaengee.withhobby.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/member")
public class MemberController {

    private final MemberService memberService;
    private final TeamService teamService;
    private final UserService userService;


    //멤버추가
    @PostMapping("join")
    public ResponseEntity<String> joinTeam(@RequestParam Long teamId, @AuthenticationPrincipal UserPrinciple userPrinciple) {

        // 현재 로그인한 사용자의 정보 가져오기
        String username = userPrinciple.getUsername();

        //teamId로 팀이름 찾기
        Team team = teamService.getTeamById(teamId);

        //동아리가 없을 경우
        if (team == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("동아리를 찾을 수 없습니다");
        }

        // 동아리에 이미 멤버가 있는지 확인
        List<Member> members = memberService.getAllMembersByTeam(team);
        for (Member member : members) {
            if (member.getUsername().equals(username)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 동아리에 속한 멤버입니다");
            }
        }

        // 새로운 멤버를 생성하고 동아리에 저장
        Member newMember = new Member();
        newMember.setTeam(team);
        newMember.setUsername(username);

        memberService.addMember(newMember);

        return ResponseEntity.status(HttpStatus.CREATED).body("멤버가 동아리에 성공적으로 가입했습니다");
    }

    //특정 멤버 탈퇴
    @DeleteMapping("delete")
    public ResponseEntity<String> deleteTeam(@RequestParam Long teamId, @RequestParam String username,
                                             @AuthenticationPrincipal UserPrinciple userPrinciple) {

        // 현재 로그인한 사용자의 정보 가져오기
        String loggedInUsername = userPrinciple.getUsername();
        //System.out.println(loggedInUsername);

        // 현재 로그인한 사용자의 username을 이용해서 상태 가져오기
        Status status = userService.getUserStatusByUsername(loggedInUsername);
        //System.out.println(status);

        // teamId로 팀 정보 찾기
        Team team = teamService.getTeamById(teamId);

        // 동아리가 없을 경우
        if (team == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("동아리를 찾을 수 없습니다");
        }

        // 삭제 조건:
        // 1. 로그인한 사용자와 탈퇴 유저가 동일할 경우
        // 2. 마스터인 유저가 다른 유저를 탈퇴시킬 경우
        if ((loggedInUsername.equals(username)) ||
                (team.getTeamHost().getUsername().equals(loggedInUsername) &&
                        status.equals(Status.MASTER))) {

            // 특정 멤버 조회
            Optional<Member> memberToDelete = memberService.getMemberByUsernameAndTeam(team, username);

            if (memberToDelete.isPresent()) {
                // 동아리에서 특정 멤버 삭제
                memberService.removeMember(team, username);
                return ResponseEntity.status(HttpStatus.OK).body("동아리에서 성공적으로 탈퇴했습니다");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 멤버를 찾을 수 없습니다");
            }
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("동아리에서 탈퇴할 권한이 없습니다");
    }

}
