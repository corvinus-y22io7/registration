package com.beadando2.reglog;

import com.beadando2.reglog.userForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class RegController {

    private final Logger logger = LoggerFactory.getLogger(RegController.class);

    private final UserRepository repository;

    @Autowired
    public RegController(UserRepository repository) {
        this.repository = repository;
    }



    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String register(@Valid userForm userForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            logger.info("Validation errors occurred!");
            return "registration";
        }

        logger.info("Registering user with email: {}", userForm.getUsername());
        final boolean userIsRegistered = repository.findByUsername(userForm.getUsername()).isPresent();
        if (!userIsRegistered) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(userForm.getPassword());
            userForm.setPassword(encodedPassword);
            repository.save(new User(userForm.getUsername(), userForm.getPassword(),userForm.getFullname()));
        }
        else {
            logger.info("Validation errors occurred!");
            model.addAttribute("error", "Ilyen felhasználó már van!");
            return "registration";
        }

        return "register_success";
    }

    @GetMapping("/welcome")
    public String showWelcomePage() {

        return "welcome";
    }

    @GetMapping("/")
    public String login() {

        return "login";
    }
    @GetMapping("/login")
    public String loginsuccess() {

        return "login";
    }

    @PostMapping("/login")
    public String loginHandler(@RequestParam(required = false) String username,String password, Model model){
        final Optional<User> foundUser = repository.findByUsername(username);
        if (foundUser.isEmpty()) {
            model.addAttribute("error1", "Ilyen felhasználó nincs.");
            return "login";
        }
        final User user = foundUser.get();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(password,user.getPassword())){
            model.addAttribute("error2", "Hibás jelszó!");
            return "login";
        }
        return "welcome";
    }

}
