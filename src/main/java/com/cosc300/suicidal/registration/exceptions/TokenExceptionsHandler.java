package com.cosc300.suicidal.registration.exceptions;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TokenExceptionsHandler {
    @ExceptionHandler(value = TokenNotFoundException.class)
    public String handleTokenNotFound(TokenNotFoundException ex) {
        return "redirect:/user/register?m=notFound";
    }

    @ExceptionHandler({TokenExpiredException.class})
    public String handleTokenExpired(TokenExpiredException ex) {
        return "redirect:/user/register?m=expired";
    }

    @ExceptionHandler(value = EmailTakenException.class)
    public String handleEmailTaken(EmailTakenException exception, Model model) {
        model.addAttribute("msg", "taken");
        return "redirect:/user/register?m=taken";
    }

    @ExceptionHandler(value = UserExistsException.class)
    public String handleUserExistsButNotVerified(UserExistsException exception) {
        return "redirect:/user/token/resend/?email=" + exception.getEmail();
    }

    @ExceptionHandler(value = UserDoesNotExistException.class)
    public String handleUserDoesNotExistException() {
        return "redirect:/user/password/reset/?m=doesNotExist";
    }
}
