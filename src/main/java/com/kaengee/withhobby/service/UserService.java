package com.kaengee.withhobby.service;

import com.kaengee.withhobby.model.Role;
import com.kaengee.withhobby.model.User;

import java.util.Optional;

public interface UserService {

    //유저등록(회원가입)
    User saveUser(User user);

    //유저찾기
    Optional<User> findByUserId(String userId);

    //role변경
    void changeRole(Role newRole, String userId);

    //유저 수정
    void updateUserProfile(String userId, String username, String userProfile);

    //유저삭제
    void deleteUser(String userId);
}
