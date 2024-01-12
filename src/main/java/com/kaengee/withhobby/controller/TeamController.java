package com.kaengee.withhobby.controller;

import com.kaengee.withhobby.model.Team;
import com.kaengee.withhobby.model.TeamForm;
import com.kaengee.withhobby.model.User;
import com.kaengee.withhobby.repository.CategoryRepository;
import com.kaengee.withhobby.security.UserPrinciple;
import com.kaengee.withhobby.service.TeamService;
import com.kaengee.withhobby.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/team")
public class TeamController {

    private final TeamService teamService;
    private final UserService userService;
    private final CategoryRepository categoryRepository;


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
}