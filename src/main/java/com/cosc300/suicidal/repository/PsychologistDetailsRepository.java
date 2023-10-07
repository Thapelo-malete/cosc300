package com.cosc300.suicidal.repository;

import com.cosc300.suicidal.model.PsychologistDetails;
import com.cosc300.suicidal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PsychologistDetailsRepository extends JpaRepository<PsychologistDetails, Integer> {
    PsychologistDetails findPsychologistDetailsByPsychologist(User user);
}
