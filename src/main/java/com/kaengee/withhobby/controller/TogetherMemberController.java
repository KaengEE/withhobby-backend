package com.kaengee.withhobby.controller;

import com.kaengee.withhobby.model.User;
import com.kaengee.withhobby.repository.MemberRepository;
import com.kaengee.withhobby.security.UserPrinciple;
import com.kaengee.withhobby.service.TogetherMemberService;
import com.kaengee.withhobby.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/together")
public class TogetherMemberController {

    private final TogetherMemberService togetherMemberService;
    private final UserService userService;
    private final MemberRepository memberRepository;

    //모임참가
    @PostMapping("/{teamId}/join/{togetherId}")
    public ResponseEntity<Object> joinTogether(@PathVariable Long togetherId,
                                               @PathVariable Long teamId,
                                               @AuthenticationPrincipal UserPrinciple userPrinciple){

        // 유저id 찾기
        Optional<User> user = userService.findByUsername(userPrinciple.getUsername());
        if (user.isPresent()) {
            Long userId = user.get().getId();

            // 멤버 확인 - 특정 팀에 이미 속한 멤버인지 확인
            boolean isMemberOfTeam = togetherMemberService.isMemberOfTeam(userId, teamId);
            if (!isMemberOfTeam) {
                // 특정 팀에 속한 멤버가 아닌 경우
                return ResponseEntity.badRequest().body("해당 팀의 멤버가 아닙니다.");
            }

            // 중복 체크 - 이미 모임에 참여한 멤버인지 확인
            boolean isAlreadyJoined = togetherMemberService.isMemberAlreadyJoined(userId, togetherId);
            if (isAlreadyJoined) {
                // 이미 모임에 참여한 경우
                return ResponseEntity.badRequest().body("이미 모임에 참가한 회원입니다.");
            }

            // 모임 참가
            togetherMemberService.joinTogether(userId, togetherId);
            return ResponseEntity.ok().body("모임 참가 완료");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
