package com.kaengee.withhobby.repository;

import com.kaengee.withhobby.model.Role;
import com.kaengee.withhobby.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    //findBy + fieldName
    Optional<User> findByUserId(String userid);

    //권한수정
    @Modifying
    @Query("update User set role = :role where userId = :userId")
    Void updateUserRole(@Param("userId") String userId, @Param("role")Role role);

    //유저프로필수정
    @Modifying
    @Query("update User set username = :username, userProfile = :userProfile where userId = :userId")
    void updateUserProfile(@Param("userId") String userId, @Param("username") String username, @Param("userProfile") String userProfile);

    //유저삭제(회원탈퇴)
    @Modifying
    @Query("delete from User where userId = :userId")
    void deleteUser(@Param("userId") String userId);
}
