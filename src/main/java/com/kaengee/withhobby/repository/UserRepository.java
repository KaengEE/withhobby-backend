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
    Optional<User> findByUsername(String username);

    //권한수정
    @Modifying
    @Query("update User set role = :role where username = :username")
    void updateUserRole(@Param("username") String username, @Param("role")Role role);

    //유저프로필수정
    @Modifying
    @Query("update User set name = :name, userProfile = :userProfile where username = :username")
    void updateUserProfile(@Param("username") String username, @Param("name") String name, @Param("userProfile") String userProfile);

    //유저삭제(회원탈퇴)
    @Modifying
    @Query("delete from User where username = :username")
    void deleteUser(@Param("username") String username);


}
