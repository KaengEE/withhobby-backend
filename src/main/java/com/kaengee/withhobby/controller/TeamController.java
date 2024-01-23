package com.kaengee.withhobby.controller;

import com.kaengee.withhobby.model.*;
import com.kaengee.withhobby.repository.MemberRepository;
import com.kaengee.withhobby.repository.TeamRepository;
import com.kaengee.withhobby.security.UserPrinciple;
import com.kaengee.withhobby.service.CategoryService;
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
@RequestMapping("api/team")
public class TeamController {

    private final TeamService teamService;
    private final UserService userService;
    private final TeamRepository teamRepository;
    private final CategoryService categoryService;
    private final MemberRepository memberRepository;
    private final TogetherService togetherService;


    //team 생성
    @PostMapping("create")
    public ResponseEntity<Object> createTeam(@RequestBody TeamForm teamForm,
                                             @AuthenticationPrincipal UserPrinciple userPrinciple) {
        // 현재 로그인한 사용자의 username을 이용해서 상태 가져오기
        String loggedInUsername = userPrinciple.getUsername();
        Status status = userService.getUserStatusByUsername(loggedInUsername);

        // 사용자의 상태가 FREE인 경우에만 팀을 생성
        if (status == Status.FREE) {
            Team team = teamService.transToTeam(teamForm);

            Optional<User> user = userService.findByUsername(loggedInUsername);
            if (user.isPresent()) {
                teamService.saveTeam(team, user.get());
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }
        }
        // 사용자의 상태가 FREE가 아닌 경우에는 생성 불가능
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("팀 생성 권한이 없습니다.");
    }

    //카테고리 이동(개별)
    @PutMapping("/{teamId}/updateCategory/{newCategoryId}")
    public void updateTeamCategory(@PathVariable Team teamId, @PathVariable Long newCategoryId) {
        Category newCategory = categoryService.getCategoryById(newCategoryId);
        teamService.updateTeamCategory(teamId.getId(), newCategory);
    }

    //동아리 host만 수정,삭제 가능
    //동아리 수정
    @PutMapping("/update/{teamId}")
    public ResponseEntity<Object> updateTeam(@PathVariable Long teamId,
                           @RequestBody TeamForm teamForm,
                           @AuthenticationPrincipal UserPrinciple userPrinciple){

        // 현재 로그인한 사용자의 username을 이용해서 상태 가져오기
        String loggedInUsername = userPrinciple.getUsername();
        //host_id 가져오기
        Optional<Team> team = teamService.findTeamHostIdByTeamId(teamId);
        if(team.isPresent()) {
            Long hostId = team.get().getTeamHost().getId();
            //로그인한 사용자의 id 찾기
            Optional<User> user = userService.findByUsername(loggedInUsername);

            //카테고리 객체찾기
            Optional<Category> category = categoryService.findByCategory(teamForm.getCategory());
            if(category.isPresent()) {
                if (user.isPresent()) {
                    //수정 접근 조건 : 해당 팀의 hostId와 동일
                    if (hostId.equals(user.get().getId())) {
                        teamRepository.updateTeam(teamForm.getTeamname(), teamForm.getTeamTitle(), teamForm.getTeamImg(), category.get(), teamId);
                        return ResponseEntity.ok("수정 성공");
                    } else {
                        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("수정 실패");
                    }
                }
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("수정 실패");
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
                //삭제 성공 후 hostId의 유저 "FREE"로 상태변경
                User userStatus = user.get();
                user.get().setUserStatus(Status.FREE);
                userService.saveUser(userStatus);

                return ResponseEntity.ok("삭제 성공");

            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("접근 제한");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("삭제 실패");
        }
    }

    //카테고리별로 팀조회하기
    @GetMapping("/{categoryId}")
    public ResponseEntity<List<Team>> teamList(@PathVariable Category categoryId){

        List<Team> teams = teamService.findTeamByCategory(categoryId);

        if (teams.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(teams, HttpStatus.OK);
    }

    //팀id로 팀 조회
    @GetMapping("/detail/{teamId}")
    public Team findById(@PathVariable Long teamId){
        return teamService.getTeamById(teamId);
    }


    //팀ID로 모임조회
    @GetMapping("/{teamId}/together")
    public ResponseEntity<List<Together>> getTogetherByTeamId(@PathVariable Long teamId) {
        List<Together> togetherList = togetherService.findTogetherById(teamId);
        if (togetherList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(togetherList);
    }

}