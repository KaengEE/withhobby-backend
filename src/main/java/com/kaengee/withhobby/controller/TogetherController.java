package com.kaengee.withhobby.controller;

import com.kaengee.withhobby.model.Team;
import com.kaengee.withhobby.model.Together;
import com.kaengee.withhobby.model.User;
import com.kaengee.withhobby.security.UserPrinciple;
import com.kaengee.withhobby.service.TeamService;
import com.kaengee.withhobby.service.TogetherService;
import com.kaengee.withhobby.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/together")
public class TogetherController {

    private final TogetherService togetherService;
    private final TeamService teamService;
    private final UserService userService;

    @PostMapping("/{teamId}")
    public ResponseEntity<Object> createTogether(@RequestBody Together together,
                                                 @PathVariable Long teamId,
                                                 @AuthenticationPrincipal UserPrinciple userPrinciple) {

        //유저 이름으로 유저 객체 찾기
        User user = userService.getUserByUsername(userPrinciple.getUsername());
        //유저 아이디
        Long userId = user.getId();
        System.out.println("현재id" + userId);

        //teamId로 hostId찾기
        Optional<Team> teamHost = teamService.findTeamHostIdByTeamId(teamId);
        if (teamHost.isPresent()) {
            Long teamHostId = teamHost.get().getTeamHost().getId();
            System.out.println("hostId :" + teamHostId);
            if (userId.equals(teamHostId)) {
                // hostId와 유저 id가 일치하는지 확인
                togetherService.createTogether(teamId, together, userId);
                // 생성된 Together 엔터티를 반환
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }else {
                // hostId와 유저 id가 일치하지 않는 경우
                return ResponseEntity.badRequest().body("호스트만 생성 가능합니다.");
            }
        } else {
            // teamId에 해당하는 팀을 찾지 못한 경우
            return ResponseEntity.notFound().build();
        }
    }
}
