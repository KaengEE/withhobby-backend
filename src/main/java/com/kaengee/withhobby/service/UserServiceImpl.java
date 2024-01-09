package com.kaengee.withhobby.service;

import com.kaengee.withhobby.model.Role;
import com.kaengee.withhobby.model.Status;
import com.kaengee.withhobby.model.User;
import com.kaengee.withhobby.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; //비밀번호 암호화 (스프링 시큐리티)


    @Override
    //유저등록(회원가입)
    public User saveUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword())); //비밀번호
        user.setRole(Role.USER); //초기값 user
        user.setUserStatus(Status.FREE); //초기값 free
        user.setCreateTime(LocalDateTime.now());
        user.setTeams(null); //처음에 null
        user.setUserProfile(null); //이미지 url 저장

        return userRepository.save(user);
    }

    @Override
    //유저찾기
    public Optional<User> findByUserId(String userId){
        return userRepository.findByUserId(userId);
    }

    @Override
    //role변경
    public void changeRole(Role newRole, String userId){
        userRepository.updateUserRole(userId, newRole);
    }

    @Override
    //유저 수정
    public void updateUserProfile(String userId, String username, String userProfile) {
        userRepository.updateUserProfile(userId, username, userProfile);
    }

    @Override
    //유저삭제
    public void deleteUser(String userId) {
        userRepository.deleteUser(userId);
    }


}