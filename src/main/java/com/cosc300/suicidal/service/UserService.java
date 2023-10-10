package com.cosc300.suicidal.service;

import com.cosc300.suicidal.model.PsychologistDetails;
import com.cosc300.suicidal.model.User;
import com.cosc300.suicidal.model.enums.UserRole;
import com.cosc300.suicidal.registration.confirmationToken.ConfirmationToken;
import com.cosc300.suicidal.registration.confirmationToken.ConfirmationTokenRepository;
import com.cosc300.suicidal.registration.exceptions.EmailTakenException;
import com.cosc300.suicidal.registration.exceptions.UserDoesNotExistException;
import com.cosc300.suicidal.registration.exceptions.UserExistsException;
import com.cosc300.suicidal.repository.PsychologistDetailsRepository;
import com.cosc300.suicidal.repository.PsychologistRepository;
import com.cosc300.suicidal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;
    @Autowired
    private PsychologistDetailsRepository psychologistDetailsRepository;
    @Autowired
    private PsychologistRepository psychologistRepository;


    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public String signUpUser(User user) throws EmailTakenException {
        User dbUser = userRepository.findUserByEmail(user.getEmail());

        if (dbUser != null && dbUser.getEnabled()){
            throw new EmailTakenException("Email already exception");
        } else if  (dbUser != null) {
            dbUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userRepository.save(dbUser);
            throw new UserExistsException("User exists but unverified", user.getEmail());
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRole(UserRole.USER);
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

    public void addPsychologist(User user) {
        User dbUser = userRepository.findUserByEmail(user.getEmail());
        if (dbUser != null) {
            throw new EmailTakenException("User is already registered");
        }

        String password = user.getFirstName().substring(0,3) + "." + user.getLastName().substring(0,3) + 123;
        System.out.println(password);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setRole(UserRole.PSYCHOLOGIST);
        userRepository.save(user);

    }

    public String addPsychologistDetails(PsychologistDetails psychologistDetails, Integer id) {
        Optional<User> psychologist = userRepository.findById(id);
        if (psychologist.isEmpty() || !(psychologist.get().getRole() == UserRole.PSYCHOLOGIST)) {
            throw new UserDoesNotExistException("Psychologist with the given id:" + id + " does not exist");
        }

        if (psychologistDetailsRepository.findPsychologistDetailsByPsychologist(
                psychologist.orElseThrow()
        ) != null) {
            throw new ResponseStatusException(HttpStatus.ALREADY_REPORTED);
        }
        psychologistDetails.setPsychologist(psychologist.orElseThrow());
        psychologistDetailsRepository.save(psychologistDetails);
        return "created";
    }

    public List<User> getAllPsychologists() {
        return psychologistRepository.findAllByRole(UserRole.PSYCHOLOGIST);
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}
