package com.cosc300.suicidal.repository;

import com.cosc300.suicidal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByEmail(String email);

    @Transactional
    void deleteByEmail(@Param("email") String email);
}
