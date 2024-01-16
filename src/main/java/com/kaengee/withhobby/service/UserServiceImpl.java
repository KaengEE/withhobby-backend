package com.kaengee.withhobby.service;

import com.kaengee.withhobby.model.Role;
import com.kaengee.withhobby.model.Status;
import com.kaengee.withhobby.model.User;
import com.kaengee.withhobby.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        user.setUserProfile(null); //이미지 url 저장

        return userRepository.save(user);
    }

    @Override
    //유저찾기
    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    //role변경
    public void changeRole(Role newRole, String username){
        userRepository.updateUserRole(username, newRole);
    }

    @Override
    @Transactional
    //유저 수정
    public void updateUserProfile(String username, String name, String userProfile) {
        userRepository.updateUserProfile(username, name, userProfile);
    }

    @Override
    @Transactional
    //비밀번호 변경 (현재 비밀번호와 일치하는지 확인)
    public void changePassword(String username, String currentPassword, String newPassword) {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("유저를 찾을 수 없음: " + username);
        }

        User user = optionalUser.get();

        // 현재 비밀번호 확인
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않음");
        }

        // 새로운 비밀번호 설정 및 저장
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    @Transactional
    //유저삭제
    public void deleteUser(String username) {
        userRepository.deleteUser(username);
    }

    @Override
    //유저상태조회
    public Status getUserStatusByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return user.getUserStatus();
        }

        return null;
    }

    //userId로 유저찾기
    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("유저ID를 찾을 수 없음: " + userId));
    }

}
