package com.cosc300.suicidal.repository;

import com.cosc300.suicidal.model.User;
import com.cosc300.suicidal.model.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByEmail(String email);
    List<User> findAllByRole(UserRole role);

    @Transactional
    void deleteByEmail(@Param("email") String email);
}
