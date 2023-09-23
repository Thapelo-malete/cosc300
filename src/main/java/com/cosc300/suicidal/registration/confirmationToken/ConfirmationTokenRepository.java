package com.cosc300.suicidal.registration.confirmationToken;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Integer> {
    ConfirmationToken findByToken(String token);
}
