package com.cosc300.suicidal.controller;

import com.cosc300.suicidal.model.PasswordResetDTO;
import com.cosc300.suicidal.model.User;
import com.cosc300.suicidal.registration.confirmationToken.ConfirmationToken;
import com.cosc300.suicidal.registration.confirmationToken.ConfirmationTokenRepository;
import com.cosc300.suicidal.service.RegistrationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/user")
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;
    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @PostMapping("/register")
    public String register(User requestUser) {
        registrationService.register(requestUser);
        return "redirect:/user/confirm?email=" + requestUser.getEmail();
    }

    @GetMapping("/confirm/{token}")
    public String confirmToken(@PathVariable String token) {
        registrationService.confirmToken(token);
        return "redirect:/user/confirmed";
    }

    @GetMapping("/token/resend/")
    public String resendToken(@RequestParam String email) {
        registrationService.register(email);
        return "redirect:/user/confirm?email=" + email;
    }

    @GetMapping("/password/reset")
    public String resetPassword(@RequestParam(name = "email") String email) {
        registrationService.resetPassword(email);
        return "redirect:/user/confirm?email=" + email;
    }

    @GetMapping("/password/reset/confirm/{token}")
    public String updatePassword(@PathVariable String token) {
        registrationService.checkToken(token);
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token);
        return "redirect:/user/password/update?email=" + confirmationToken.getUser().getEmail() + "&token="+ token;
    }

    @PostMapping("/password/update")
    public String updatePassword(PasswordResetDTO passwordResetDTO) {
        registrationService.updatePassword(passwordResetDTO);
        return "password_updated";
    }
    
    @GetMapping("/password/update")
    public String passwordUpdatePage(
            @RequestParam(name = "email") String email,
            @RequestParam(name = "token") String token,
            Model model
    ) {
        model.addAttribute("token", token);
        model.addAttribute("email", email);
        return "password_update";
    }
}
