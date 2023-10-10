package com.cosc300.suicidal.repository;

import com.cosc300.suicidal.model.User;
import com.cosc300.suicidal.model.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PsychologistRepository extends JpaRepository<User, Integer> {
    List<User> findAllByRole(UserRole role);
}
