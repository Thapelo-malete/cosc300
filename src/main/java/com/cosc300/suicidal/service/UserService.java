package com.cosc300.suicidal.service;

import com.cosc300.suicidal.model.User;
import com.cosc300.suicidal.registration.confirmationToken.ConfirmationToken;
import com.cosc300.suicidal.registration.confirmationToken.ConfirmationTokenRepository;
import com.cosc300.suicidal.registration.exceptions.EmailTakenException;
import com.cosc300.suicidal.registration.exceptions.UserDoesNotExistException;
import com.cosc300.suicidal.registration.exceptions.UserExistsException;
import com.cosc300.suicidal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public String signUpUser(User user) throws EmailTakenException {
        User dbUser = userRepository.findUserByEmail(user.getEmail());

        if (dbUser != null && dbUser.getEnabled() == true){
            throw new EmailTakenException("Email already exception");
        } else if (dbUser != null && dbUser.getEnabled() == false) {
            dbUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userRepository.save(dbUser);
            throw new UserExistsException("User exists but unverified", user.getEmail());
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(2),
                user
        );

        confirmationTokenRepository.save(confirmationToken);
        System.out.println(token + "from sign up method");
        return token;
    }

    public String regenerateToken(String email) {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new UserDoesNotExistException("User with the provided Email does not exist");
        }
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(2),
                user
        );

        confirmationTokenRepository.save(confirmationToken);
        System.out.println(token + "from regenerate method");
        return token;
    }

    public String enableUser(User user) {
        user.setEnabled(true);
        userRepository.save(user);
        return "enabled";
    }
}
