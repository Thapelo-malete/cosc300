package com.cosc300.suicidal.model;

import lombok.Data;

@Data
public class PasswordResetDTO {
    private String email;
    private String newPassword;
    private String token;
}
