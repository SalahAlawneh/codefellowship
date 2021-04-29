package com.salahcodefellowship.codefellowship.controller;

import com.salahcodefellowship.codefellowship.model.DBUser;
import com.salahcodefellowship.codefellowship.repository.DBUserRepository;
import com.salahcodefellowship.codefellowship.service.BusinessException;
import javassist.bytecode.stackmap.BasicBlock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@Validated
@Controller
public class ApplicationUserController {

    @Autowired
    DBUserRepository dbUserRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/signup")
    public String getSignUpPage(){
        return "signup.html";
    }

    @GetMapping("/login")
    public String getLoginPage(){
        return "login.html";
    }

    @PostMapping("/signup")
    public RedirectView signup(@RequestParam("id") Integer id, @RequestParam(value="username") String username, @RequestParam(value="password") String password){
        DBUser newUser = new DBUser(username,bCryptPasswordEncoder.encode(password),"ROLE_USER");
        newUser = dbUserRepository.save(newUser);

        if(id > 0)
            throw new BusinessException();

//        Authentication authentication = new UsernamePasswordAuthenticationToken(newUser, null, new ArrayList<>());
//        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new RedirectView("/login");
    }

    @GetMapping("/myprofile")
    public String getUserProfilePage(Principal p,Model m){
        m.addAttribute("user", ((UsernamePasswordAuthenticationToken)p).getPrincipal());
        return "profile.html";
    }

    @GetMapping("/profiles/{id}")
    public String getUserProfile(Principal p, Model m,@PathVariable Integer id){
        DBUser requiredProfile = dbUserRepository.findById(id).get();
        if(requiredProfile != null){
            m.addAttribute("user", requiredProfile);
            String loggedInUserName= p.getName();
            DBUser loggedInUser = dbUserRepository.findByUsername(loggedInUserName);
            boolean isAllowedToEdit = loggedInUser.getId() == id;
            m.addAttribute("isAllowedToEdit",isAllowedToEdit);
            return "profile.html";
        } else {
            m.addAttribute("message","this user doesn't exist");
            return "error.html";
        }
    }

    @DeleteMapping("profiles/{id}")
    public RedirectView deleteUserProfile(@PathVariable Integer id){
        DBUser userToDelete = dbUserRepository.findById(id).get();
        dbUserRepository.delete(userToDelete);
        return new RedirectView("/");
    }

    @PutMapping("/profiles/{id}")
    public RedirectView editProfile(Principal p, @PathVariable Integer id, @RequestParam String username){
        System.out.println("we are editing !");
        String loggedInUserName = p.getName();
        DBUser loggedInUser = dbUserRepository.findByUsername(loggedInUserName);
        if(loggedInUser.getId() == id) {
            loggedInUser.setUsername(username);
            dbUserRepository.save(loggedInUser);
            return new RedirectView("/profiles/"+id);
        } else {
            return new RedirectView("/error?message=Unauthorized");
        }
    }

    @GetMapping("/error")
    public String getErrorPage(@RequestParam String message, Model m){
        m.addAttribute("message", message);
        return "error.html";
    }

}
