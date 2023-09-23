package com.cosc300.suicidal.repository;

import com.cosc300.suicidal.model.EmergencyContact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmergencyContactRepository extends JpaRepository<EmergencyContact, Integer> {
}
