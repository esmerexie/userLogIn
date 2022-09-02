package com.rexie.userLogIn.controllers;

import com.rexie.userLogIn.models.SiteUser;
import com.rexie.userLogIn.respositories.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class AuthenticationController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/login")
    public String getLoginPage(){
        return "/login.html";
    }

    @GetMapping("/signup")
    public String getSignUpPage(){
        return "/signup.html";
    }

    @PostMapping("login")
    public RedirectView loginUser(String username, String password){
        SiteUser userFromDb = userRepository.findByUsername(username);

        if((userFromDb == null || (!BCrypt.checkpw(password, userFromDb.getPassword())))){
        }
        return new RedirectView("/");
    }

    @PostMapping("signup")
    public RedirectView signUpUser(String username, String password){
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));

        SiteUser newUser = new SiteUser(username, hashedPassword);
        userRepository.save(newUser);

        return new RedirectView("/login");
    }

}
