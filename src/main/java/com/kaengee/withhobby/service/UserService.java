package com.kaengee.withhobby.service;

import com.kaengee.withhobby.model.Role;
import com.kaengee.withhobby.model.Status;
import com.kaengee.withhobby.model.User;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserService {

    //유저등록(회원가입)
    User saveUser(User user);

    //유저찾기
    Optional<User> findByUsername(String username);

    //전체 유저리스트
    List<User> findAllUsers();

    //role변경
    void changeRole(Role newRole, String username);

    //유저 수정
    void updateUserProfile(String username, String name, String userProfile);

    @Transactional
    //비밀번호 변경 (현재 비밀번호와 일치하는지 확인)
    void changePassword(String username, String currentPassword, String newPassword);

    //유저삭제
    void deleteUser(String username);

    //유저상태조회
    Status getUserStatusByUsername(String username);

    User getUserById(Long userId);

    //유저 이름으로 유저객체 찾기
    User getUserByUsername(String username);


    Optional<User> findById(Long id);
}
