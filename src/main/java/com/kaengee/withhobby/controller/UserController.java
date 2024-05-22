package com.kaengee.withhobby.controller;

import com.kaengee.withhobby.model.ChangePassword;
import com.kaengee.withhobby.model.Role;
import com.kaengee.withhobby.model.Team;
import com.kaengee.withhobby.model.User;
import com.kaengee.withhobby.security.UserPrinciple;
import com.kaengee.withhobby.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;

    //전체 회원 리스트
    @GetMapping("list")
    public ResponseEntity<List<User>> getUserList(){
        //System.out.println("getUserList() 메서드가 호출되었습니다.");
        List<User> user = userService.findAll();
        if(user.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    //회원 role 수정 (관리자가 다른 유저의 역할을 변경)
    @PutMapping("change/{username}/{role}")
    public ResponseEntity<Object> changeRole(@PathVariable String username, @PathVariable Role role) {
        userService.changeRole(role, username);
        return ResponseEntity.ok(true);
    }

    //회원 프로필 수정
    @PutMapping("profile")
    public void updateProfile(@AuthenticationPrincipal UserPrinciple userPrinciple,
                              @RequestBody User user){
//        System.out.println(userPrinciple.getUsername());
//        System.out.println(user.getName());
//        System.out.println(user.getUserProfile());

        userService.updateUserProfile(userPrinciple.getUsername(), user.getName(), user.getUserProfile());
    }

    //비밀번호 변경
    @PutMapping("/changePassword")
    public ResponseEntity<Object> changePassword(@AuthenticationPrincipal UserPrinciple userPrinciple,
                                                 @RequestBody ChangePassword changePassword){

        String username = userPrinciple.getUsername();
        String currentPassword = changePassword.getCurrentPassword();
        String newPassword = changePassword.getNewPassword();

        userService.changePassword(username, currentPassword, newPassword);

        return ResponseEntity.ok("비밀번호 변경 성공");
    }

    //회원 삭제
    @DeleteMapping("/delete")
    public void deleteUser(@AuthenticationPrincipal UserPrinciple userPrinciple){
        userService.deleteUser(userPrinciple.getUsername());
    }

    //유저id로 유저 찾기
    @GetMapping("/findUsername/{userId}")
    public Optional<User> findUsernameById(@PathVariable Long userId,@AuthenticationPrincipal UserPrinciple userPrinciple){
        return userService.findById(userId);
    }
}
