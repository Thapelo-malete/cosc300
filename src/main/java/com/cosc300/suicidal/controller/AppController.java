package com.cosc300.suicidal.controller;

import com.cosc300.suicidal.model.Crime;
import com.cosc300.suicidal.model.EmergencyContact;
import com.cosc300.suicidal.model.MyUserDetails;
import com.cosc300.suicidal.model.User;
import com.cosc300.suicidal.repository.CrimeRepository;
import com.cosc300.suicidal.repository.EmergencyContactRepository;
import com.cosc300.suicidal.repository.UserRepository;
import com.cosc300.suicidal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/app")
public class AppController {
    private final CrimeRepository crimeRepository;
    private final EmergencyContactRepository emergencyContactRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping
    public String home(){
        return "home_page";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact_us";
    }

    @GetMapping("/emergency-contacts")
    public String emergency() {
        return "emegency_contacts";
    }

    @PostMapping("/emergency-contacts/add")
    public String addEmergencyContact(EmergencyContact emergencyContact) {

        /* this use the logged-in email to find the user from the database and then sets
         it to the emergency contact*/
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        User user = userRepository.findUserByEmail(
                ((MyUserDetails)authentication.getPrincipal()
        ).getUsername());

        emergencyContact.setUser(user);
        emergencyContactRepository.save(emergencyContact);
        return "redirect:/";
    }

    @GetMapping("/faqs")
    public String faqs() {
        return "FAQs";
    }

    @GetMapping("/learn-more")
    public String learnMore() {
        return "learn_more";
    }

    @GetMapping("/suicidal-prevention")
    public String suicidalPrevention() {
        return "suicidal_prevention";
    }

//    @GetMapping("/crime-notifier")
//    public String publicNotifier() {
//        return "crime_notifier";
//    }

    @GetMapping("/chatbox")
    public String chatbox() {
        return "chat_box";
    }

    @GetMapping("/assistance")
    public String assistance() {
        return "helpers";
    }

    @GetMapping("/report-crime")
    public String reportCrime(Model model) {
        List<Crime> crimes = crimeRepository.findAll();
        model.addAttribute("crimes", crimes);
        return "report_crime";
    }

    @PostMapping("/report-crime")
    public String addCrime(Crime crime) {
        crimeRepository.save(crime);
        return "redirect:/report-crime";
    }

    @GetMapping("/text-analyzer")
    public String textAnalyzer() {
        return "text_analyzer";
    }

    @GetMapping("/crime-notifier")
    public String crimeNotifier() {
        return "crime_notifier";
    }

    @GetMapping("/crimes")
    public String crimes(Model model) {
        List<Crime> crimes = crimeRepository.findAll();
        model.addAttribute("crimes", crimes);
        return "crimes";
    }

    @GetMapping("/message-board")
    public String messageBoard() {
        return "message_board";
    }

    @GetMapping("/analyzer")
    public String analyzer() {
        return "analyzer";
    }

    @GetMapping("/psychologist")
    public String psychologist(Model model) {
        model.addAttribute("psychologists", userService.getAllPsychologists());
        return "psychologist";
    }

    @GetMapping("/community")
    public String community(Model model) {
        model.addAttribute("user", userService.getAuthenticatedUser());
        return "home";
    }
}
