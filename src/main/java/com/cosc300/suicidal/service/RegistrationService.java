package com.cosc300.suicidal.service;

import com.cosc300.suicidal.emailConfig.EmailSender;
import com.cosc300.suicidal.model.PasswordResetDTO;
import com.cosc300.suicidal.model.User;
import com.cosc300.suicidal.registration.confirmationToken.ConfirmationToken;
import com.cosc300.suicidal.registration.confirmationToken.ConfirmationTokenRepository;
import com.cosc300.suicidal.registration.exceptions.EmailTakenException;
import com.cosc300.suicidal.registration.exceptions.TokenExpiredException;
import com.cosc300.suicidal.registration.exceptions.TokenNotFoundException;
import com.cosc300.suicidal.registration.exceptions.UserDoesNotExistException;
import com.cosc300.suicidal.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RegistrationService {
    @Autowired
    private UserService userService;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private EmailSender emailSender;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public String register(User requestUser) throws EmailTakenException {
        String token = userService.signUpUser(requestUser);
        String link = "http://localhost:8080/user/confirm/" + token;

        String email = email(requestUser.getEmail(), link);
        emailSender.send(requestUser.getEmail(), email);

        return email;
    }

    public String register(String requestEmail) {
        User user = userRepository.findUserByEmail(requestEmail);
        if (user != null && user.getEnabled()) {
            throw new EmailTakenException("Email already taken");
        }
        String token = userService.regenerateToken(requestEmail);

        String email = email(requestEmail, token);
        emailSender.send(requestEmail, email);

        return email;
    }

    public String confirmToken(String token) {
        checkToken(token);
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token);

        userService.enableUser(confirmationToken.getUser());
        confirmationTokenRepository.delete(confirmationToken);
        return "confirmed";
    }

    public String email(String email, String link){
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Document</title>\n" +
                "\n" +
                "    <style>\n" +
                "        @import url(https://fonts.googleapis.com/css2?family=Montserrat:wght@100;200;300;400;500;600;800;900&display=swap);\n" +
                "        \n" +
                "        body {\n" +
                "          font-family: 'Montserrat', sans-serif;\n" +
                "          height: 95vh;\n" +
                "          width: 95vw;\n" +
                "        }\n" +
                "        .email-content{\n" +
                "            text-align: center;\n" +
                "            width: 100%;\n" +
                "        }\n" +
                "\n" +
                "        .email-content .btn {\n" +
                "            background-color: #004ab0;\n" +
                "            padding: 12px;\n" +
                "            color: white;\n" +
                "            text-decoration: none;\n" +
                "            border-radius: 5px;\n" +
                "        }\n" +
                "\n" +
                "        .email-content p {\n" +
                "            font-weight: 400;\n" +
                "        }\n" +
                "        .link {\n" +
                "            padding: 30px;\n" +
                "        }\n" +
                "        .btn:hover {\n" +
                "            background-color: black;\n" +
                "        }\n" +
                "\n" +
                "        .email-content h1 , .email-content b {\n" +
                "            font-weight: 600;\n" +
                "        }\n" +
                "        .image-container img {\n" +
                "            width: 250px;\n" +
                "        }\n" +
                "        .image-container {\n" +
                "            display: flex;\n" +
                "            justify-content: center;\n" +
                "        }\n" +
                "        .alternative {\n" +
                "            font-size: small;\n" +
                "            font-weight: 600;\n" +
                "        }\n" +
                "        .container {\n" +
                "            background-color: white;\n" +
                "            max-width: 650px;\n" +
                "            margin: auto;\n" +
                "            padding: 50px;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <div class=\"image-container\">\n" +
                "            <img src=\"email-illustrator.png\" alt=\"\" />\n" +
                "        </div>\n" +
                "        <div class=\"email-content\">\n" +
                "            <h1>Verify Email Address</h1>\n" +
                "            <div class=\"intro\">\n" +
                "                <p>\n" +
                "                    This is confirmation email from Save A Life for <b>"+ email +"</b>. Please verify your email by\n" +
                "                    clicking\n" +
                "                    the\n" +
                "                    button below or ignore the email if it was not you.\n" +
                "                </p>\n" +
                "            </div>\n" +
                "            <div class=\"link\">\n" +
                "                <a href=\""+ link +"\" class=\"btn\">Verify your email</a>\n" +
                "            </div>\n" +
                "            <div class=\"alternative\">\n" +
                "                <p>Or copy this link and paste in your browser</p>\n" +
                "                <a href=\""+ link +"\">"+ link +
                                "</a>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";
    }

    public void resetPassword(String requestEmail) {
        User user = userRepository.findUserByEmail(requestEmail);

        if (user == null) {
            throw new UserDoesNotExistException("User with provided email does not Exist!!");
        }

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
            token,
            LocalDateTime.now(),
            LocalDateTime.now().plusMinutes(2),
            user
        );

        confirmationTokenRepository.save(confirmationToken);

        String link = "http://localhost:8080/user/password/reset/confirm/" + token;
        String email = email(user.getEmail(), link);

        emailSender.send(user.getEmail(), email);
    }

    public void updatePassword(PasswordResetDTO passwordResetDTO) {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(passwordResetDTO.getToken());
        if (confirmationToken == null){
            throw new TokenNotFoundException("token not found");
        }
        User user = confirmationToken.getUser();
        user.setPassword(bCryptPasswordEncoder.encode(passwordResetDTO.getNewPassword()));
        userRepository.save(user);
        confirmationTokenRepository.delete(confirmationToken);
    }

    public void checkToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token);

        if (confirmationToken == null) {
            throw new TokenNotFoundException("provided token does not exist");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        if (expiredAt.isBefore(LocalDateTime.now())){
            confirmationTokenRepository.delete(confirmationToken);
            throw new TokenExpiredException("Token is expired");
        }
    }

}
