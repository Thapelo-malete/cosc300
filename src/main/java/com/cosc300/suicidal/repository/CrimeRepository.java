package com.cosc300.suicidal.repository;

import com.cosc300.suicidal.model.Crime;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CrimeRepository extends JpaRepository<Crime, Integer> {

}
