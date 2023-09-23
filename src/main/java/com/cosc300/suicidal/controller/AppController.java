package com.cosc300.suicidal.controller;

import com.cosc300.suicidal.model.Crime;
import com.cosc300.suicidal.model.EmergencyContact;
import com.cosc300.suicidal.model.MyUserDetails;
import com.cosc300.suicidal.model.User;
import com.cosc300.suicidal.repository.CrimeRepository;
import com.cosc300.suicidal.repository.EmergencyContactRepository;
import com.cosc300.suicidal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AppController {
    @Autowired
    private CrimeRepository crimeRepository;

    @Autowired
    private EmergencyContactRepository emergencyContactRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal OAuth2User principal){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Object ob = authentication.getPrincipal();
        System.out.println("principal: " + ob.toString());

        
        User user = userRepository.findUserByEmail(((MyUserDetails)(ob)).getUsername());
        
        

        if (user != null) {
            model.addAttribute("username", user.getFirstName() + " " + user.getLastName());
        } else if (principal != null) {
            model.addAttribute("username", principal.getName());
        }
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

        /* this use the logged in email to find the user from the database and then sets
         it to the emergency contact*/
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        User user = userRepository.findUserByEmail(
                ((UserDetails)authentication.getPrincipal()
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
}