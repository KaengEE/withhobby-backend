package com.kaengee.withhobby.controller;

import com.kaengee.withhobby.model.Team;
import com.kaengee.withhobby.model.Together;
import com.kaengee.withhobby.security.UserPrinciple;
import com.kaengee.withhobby.service.TeamService;
import com.kaengee.withhobby.service.TogetherService;
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

    @PostMapping("/{teamId}/create")
    public ResponseEntity<Object> createTogether(@RequestBody Together together,
                                                 @PathVariable Long teamId,
                                                 @AuthenticationPrincipal UserPrinciple userPrinciple) {
        // 현재 유저id 찾기
        Long userId = userPrinciple.getId();
        System.out.println(userId);
        // teamId로 hostId 찾기
        Optional<Team> team = teamService.findTeamHostIdByTeamId(teamId);
        if (team.isPresent()) {
            Long hostId = team.get().getId();
            System.out.println(hostId);
            if (userId.equals(hostId)) {
                // hostId와 유저 id가 일치하는지 확인
                Together createdTogether = togetherService.createTogether(teamId, together, userId);
                // 생성된 Together 엔터티를 반환
                return ResponseEntity.status(HttpStatus.CREATED).body(createdTogether);
            }
        }
        // 생성이 실패한 경우
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
