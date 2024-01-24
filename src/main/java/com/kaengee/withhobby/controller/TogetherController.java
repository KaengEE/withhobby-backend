package com.kaengee.withhobby.controller;

import com.kaengee.withhobby.model.Team;
import com.kaengee.withhobby.model.Together;
import com.kaengee.withhobby.model.User;
import com.kaengee.withhobby.repository.TogetherRepository;
import com.kaengee.withhobby.security.UserPrinciple;
import com.kaengee.withhobby.service.TeamService;
import com.kaengee.withhobby.service.TogetherService;
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
@RequestMapping("api/together")
public class TogetherController {

    private final TogetherService togetherService;
    private final TogetherRepository togetherRepository;
    private final TeamService teamService;
    private final UserService userService;

    //모임 생성
    @PostMapping("/{teamId}")
    public ResponseEntity<Object> createTogether(@RequestBody Together together,
                                                 @PathVariable Long teamId,
                                                 @AuthenticationPrincipal UserPrinciple userPrinciple) {

        //유저 이름으로 유저 객체 찾기
        User user = userService.getUserByUsername(userPrinciple.getUsername());
        //유저 아이디
        Long userId = user.getId();

        //teamId로 hostId찾기
        Optional<Team> teamHost = teamService.findTeamHostIdByTeamId(teamId);

        if (teamHost.isPresent()) {
            Long teamHostId = teamHost.get().getTeamHost().getId();
            //System.out.println("hostId :" + teamHostId);
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

  //모임 내용 수정
  @PutMapping("/{togetherId}")
    public ResponseEntity<Object> updateTogether(@RequestBody Together together,
                                                 @PathVariable Long togetherId,
                                                 @AuthenticationPrincipal UserPrinciple userPrinciple) {
        // 유저 이름으로 유저 객체 찾기
        User user = userService.getUserByUsername(userPrinciple.getUsername());
        // 유저 아이디
        Long userId = user.getId();

        // togetherId로 모임 찾기
        Together findTogether = togetherRepository.findById(togetherId).orElse(null);
        if (findTogether == null) {
            return ResponseEntity.notFound().build(); // 모임을 찾지 못한 경우
        }

        Team team = findTogether.getTeam();
        if (team != null) {
            // teamId로 hostId 찾기
            Optional<Team> teamHost = teamService.findTeamHostIdByTeamId(team.getId());
            if (teamHost.isPresent()) {
                Long teamHostId = teamHost.get().getTeamHost().getId();

                if (userId.equals(teamHostId)) {
                    // hostId와 유저 id가 일치하는지 확인 후 수정
                    togetherService.updateTogether(together, togetherId);
                    return ResponseEntity.ok("200");
                } else {
                    // hostId와 유저 id가 일치하지 않는 경우
                    return ResponseEntity.badRequest().body("호스트만 수정 가능합니다.");
                }
            } else {
                // teamId에 해당하는 팀을 찾지 못한 경우
                return ResponseEntity.notFound().build();
            }
        } else {
            // togetherId에 해당하는 팀을 찾지 못한 경우
            return ResponseEntity.notFound().build();
        }
    }

    //모임id로 찾기
    @GetMapping("/detail/{togetherId}")
    public Together findById(@PathVariable Long togetherId, @AuthenticationPrincipal UserPrinciple userPrinciple){
        return  togetherService.findById(togetherId);
    }

    //모임삭제
    @DeleteMapping("/{togetherId}")
    public ResponseEntity<Object> deleteTogether(@PathVariable Long togetherId,
                                                 @AuthenticationPrincipal UserPrinciple userPrinciple){
        // 유저 이름으로 유저 객체 찾기
        User user = userService.getUserByUsername(userPrinciple.getUsername());
        // 유저 아이디
        Long userId = user.getId();

        // togetherId로 모임 찾기
        Together findTogether = togetherRepository.findById(togetherId).orElse(null);
        if (findTogether == null) {
            return ResponseEntity.notFound().build(); // 모임을 찾지 못한 경우
        }

        Team team = findTogether.getTeam();
        if (team != null) {
            // teamId로 hostId 찾기
            Optional<Team> teamHost = teamService.findTeamHostIdByTeamId(team.getId());
            if (teamHost.isPresent()) {
                Long teamHostId = teamHost.get().getTeamHost().getId();

                if (userId.equals(teamHostId)) {

                    //해당 팀의 hostId와 userId가 동일해야 삭제 가능
                    togetherService.deleteTogether(togetherId);
                    return ResponseEntity.ok().body("모임 삭제 완료");
                } else {
                    // hostId와 유저 id가 일치하지 않는 경우
                    return ResponseEntity.badRequest().body("삭제 권한이 없습니다.");
                }
            } else {
                // teamId에 해당하는 팀을 찾지 못한 경우
                return ResponseEntity.notFound().build();
            }
        } else {
            // togetherId에 해당하는 팀을 찾지 못한 경우
            return ResponseEntity.notFound().build();
        }
    }

    //모임리스트
    @GetMapping("/list/{teamId}")
    public List<Together> togetherList(@PathVariable Long teamId,@AuthenticationPrincipal UserPrinciple userPrinciple){
        return togetherService.findTogetherById(teamId);
    }


}
