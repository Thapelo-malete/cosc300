package com.cosc300.suicidal.controller;

import com.cosc300.suicidal.model.User;
import com.cosc300.suicidal.model.enums.UserRole;
import com.cosc300.suicidal.repository.CrimeRepository;
import com.cosc300.suicidal.repository.UserRepository;
import com.cosc300.suicidal.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping(path = "/admin")
public class AdminController {
    private final UserService userService;
    private final CrimeRepository crimeRepository;
    private final UserRepository userRepository;

    @GetMapping("/psychologist")
    public String addPsychologist(Model model) {
        model.addAttribute("allPsychologists", userService.getAllPsychologists());
        return "psychologists";
    }

    @PostMapping("/psychologist/add")
    public String addPsychologist(User user) {
        userService.addPsychologist(user);
        return "redirect:/admin/psychologist";
    }

    @GetMapping("/psychologist/delete/{id}")
    public String deletePsychologist(@PathVariable Integer id){
        userService.deleteUser(id);
        return "redirect:/admin/psychologist";
    }

    @GetMapping("/home")
    public String adminHome(Model model) {
        model.addAttribute("users", userRepository.findAllByRole(UserRole.USER));
        model.addAttribute("allPsychologists", userService.getAllPsychologists());
        model.addAttribute("crimes", crimeRepository.findAll());
        return "index";
    }
}
