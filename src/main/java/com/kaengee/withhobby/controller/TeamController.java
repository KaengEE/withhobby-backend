package com.kaengee.withhobby.controller;

import com.kaengee.withhobby.model.*;
import com.kaengee.withhobby.repository.MemberRepository;
import com.kaengee.withhobby.repository.TeamRepository;
import com.kaengee.withhobby.security.UserPrinciple;
import com.kaengee.withhobby.service.CategoryService;
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
@RequestMapping("api/team")
public class TeamController {

    private final TeamService teamService;
    private final UserService userService;
    private final TeamRepository teamRepository;
    private final CategoryService categoryService;
    private final MemberRepository memberRepository;


    //team 생성
    @PostMapping("create")
    public ResponseEntity<Object> createTeam(@RequestBody TeamForm teamForm,
                                             @AuthenticationPrincipal UserPrinciple userPrinciple) {

//        System.out.println(teamForm.getTeamname());
//        System.out.println(teamForm.getTeamTitle());
//        System.out.println(teamForm.getTeamImg());
//        System.out.println(teamForm.getCategory());

        Team team = teamService.transToTeam(teamForm);

        //System.out.println(userPrinciple.getUsername());
        Optional<User> user = userService.findByUsername(userPrinciple.getUsername());
        if(user.isPresent()){
            teamService.saveTeam(team, user.get());
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //카테고리 이동(개별)
    @PutMapping("/{teamId}/updateCategory/{newCategoryId}")
    public void updateTeamCategory(@PathVariable Team teamId, @PathVariable Long newCategoryId) {
        Category newCategory = categoryService.getCategoryById(newCategoryId);
        teamService.updateTeamCategory(teamId.getId(), newCategory);
    }

    //동아리 master만 수정,삭제 가능
    //동아리 수정
    @PutMapping("/master/update")
    public void updateTeam(@RequestBody TeamForm teamForm,
                           @AuthenticationPrincipal UserPrinciple userPrinciple){

        // 현재 로그인한 사용자의 username을 이용해서 상태 가져오기
        String loggedInUsername = userPrinciple.getUsername();
        Status status = userService.getUserStatusByUsername(loggedInUsername);

        //host_id 가져오기
        Long hostId = teamService.findTeamHostId(teamForm.getTeamname());

        //로그인한 사용자의 id 찾기
        Optional<User> user = userService.findByUsername(loggedInUsername);

        if(user.isPresent()) {
            //수정 접근 조건 : 해당 팀의 master
            if (hostId.equals(user.get().getId()) && status.equals(Status.MASTER)) {
                //System.out.println(user.get().getId());
                //System.out.println(hostId);
                teamService.updateTeam(teamForm);
            } else {
                System.out.println("접근 불가");
            }
        }
    }

    // 동아리 삭제
    // 조건: 동아리 멤버가 없어야함 해당 동아리의 'MASTER' 만 삭제 가능
    @DeleteMapping("/master/delete")
    public ResponseEntity<String> deleteTeam(@RequestBody TeamForm teamForm,
                                             @AuthenticationPrincipal UserPrinciple userPrinciple) {
        // 현재 로그인한 사용자의 username을 이용해서 상태 가져오기
        String loggedInUsername = userPrinciple.getUsername();
        Status status = userService.getUserStatusByUsername(loggedInUsername);
        // host_id 가져오기
        Long hostId = teamService.findTeamHostId(teamForm.getTeamname());


        // 로그인한 사용자의 id 찾기
        Optional<User> user = userService.findByUsername(loggedInUsername);

        if (user.isPresent()) {
            // 접근 조건 : 해당 팀의 master
            if (hostId.equals(user.get().getId()) && status.equals(Status.MASTER)) {

                Optional<Team> optionalTeam = teamRepository.findByTeamname(teamForm.getTeamname());
                //해당 팀의 멤버 전원 탈퇴시키기
                if (optionalTeam.isPresent()) {
                    Team team = optionalTeam.get();

                    // 특정 동아리에 속한 모든 멤버 가져오기
                    List<Member> members = memberRepository.findByTeam(team);
                    //System.out.println(members);
                    // 동아리에서 특정 멤버 삭제
                    for (Member member : members) {
                        memberRepository.delete(member);
                    }
                }

                teamService.deleteTeam(teamForm.getTeamname());
                return ResponseEntity.ok("삭제 성공");
                //삭제 성공 후 hostId의 유저 "FREE"로 상태변경

            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("접근 제한");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("삭제 실패");
        }
    }

}