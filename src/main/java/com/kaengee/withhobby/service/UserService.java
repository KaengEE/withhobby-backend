package com.kaengee.withhobby.service;

import com.kaengee.withhobby.model.Role;
import com.kaengee.withhobby.model.Status;
import com.kaengee.withhobby.model.User;

import java.util.Optional;

public interface UserService {

    //유저등록(회원가입)
    User saveUser(User user);

    //유저찾기
    Optional<User> findByUsername(String username);

    //role변경
    void changeRole(Role newRole, String username);

    //유저 수정
    void updateUserProfile(String username, String name, String userProfile);

    //유저삭제
    void deleteUser(String username);

    //유저상태조회
    Status getUserStatusByUsername(String username);

    User getUserById(Long userId);
}
