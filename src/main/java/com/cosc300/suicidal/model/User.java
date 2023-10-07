package com.cosc300.suicidal.model;

import java.util.ArrayList;
import java.util.List;

import com.cosc300.suicidal.model.enums.UserRole;
import com.cosc300.suicidal.registration.confirmationToken.ConfirmationToken;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @Enumerated(value = EnumType.STRING)
    private UserRole role;
    private Boolean enabled = false;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ConfirmationToken> confirmationTokens = new ArrayList<>();
}
