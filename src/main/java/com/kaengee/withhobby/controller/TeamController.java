package com.kaengee.withhobby.controller;

import com.kaengee.withhobby.model.*;
import com.kaengee.withhobby.repository.CategoryRepository;
import com.kaengee.withhobby.security.UserPrinciple;
import com.kaengee.withhobby.service.CategoryService;
import com.kaengee.withhobby.service.TeamService;
import com.kaengee.withhobby.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/team")
public class TeamController {

    private final TeamService teamService;
    private final UserService userService;
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;


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
                System.out.println(user.get().getId());
                System.out.println(hostId);
                teamService.updateTeam(teamForm);
            } else {
                System.out.println("접근 불가");
            }
        }
    }

    //동아리 삭제
}