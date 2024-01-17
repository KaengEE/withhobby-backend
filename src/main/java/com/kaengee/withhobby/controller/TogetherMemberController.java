package com.kaengee.withhobby.controller;

import com.kaengee.withhobby.model.User;
import com.kaengee.withhobby.security.UserPrinciple;
import com.kaengee.withhobby.service.TogetherMemberService;
import com.kaengee.withhobby.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/together/join")
public class TogetherMemberController {

    private final TogetherMemberService togetherMemberService;
    private final UserService userService;
    //모임참가
    @PostMapping("/{togetherId}")
    public ResponseEntity<Object> joinTogether(@PathVariable Long togetherId,
                                               @AuthenticationPrincipal UserPrinciple userPrinciple){

        //유저id 찾기
        Optional<User> user = userService.findByUsername(userPrinciple.getUsername());
        if(user.isPresent()) {
            Long userId = user.get().getId();
            togetherMemberService.joinTogether(userId, togetherId);
            return ResponseEntity.ok().body("모임 참가 완료");
        }else {
            return ResponseEntity.notFound().build();
        }
    }

}
