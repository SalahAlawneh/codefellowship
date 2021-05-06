package com.salahcodefellowship.codefellowship.controller;

import com.salahcodefellowship.codefellowship.model.ApplicationUser;
import com.salahcodefellowship.codefellowship.model.Post;
import com.salahcodefellowship.codefellowship.repository.ApplicationUserRepository;
import com.salahcodefellowship.codefellowship.repository.PostRepository;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.jws.WebParam;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Controller
public class GeneralController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ApplicationUserRepository applicationUserRepository;
    @Autowired
    PostRepository postRepository;


    @GetMapping("/")
    public String getHomePage() {
        return "splash.html";
    }


    @GetMapping("/signup")
    public String getSignupPage() {
        return "signup";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @PostMapping("login")
    public RedirectView login(String username, String password) {
        if (applicationUserRepository.findByUsername(username) != null && applicationUserRepository.findByUsername(username).getPassword() == password) {
            ApplicationUser existsUser = applicationUserRepository.findByUsername(username);
            Authentication authentication = new UsernamePasswordAuthenticationToken(existsUser, null, new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        return new RedirectView("/");
    }

    @PostMapping("/signup")
    public RedirectView signup(String password, String username, String firstName, String lastName, String dateOfBirth, String bio) {
        if (applicationUserRepository.findByUsername(username) == null) {
            ApplicationUser signUser = new ApplicationUser(passwordEncoder.encode(password), username, firstName, lastName, dateOfBirth, bio);
            ApplicationUser newUser = applicationUserRepository.save(signUser);
            Authentication authentication = new UsernamePasswordAuthenticationToken(signUser, null, new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }

        return new RedirectView("/");
    }

    @GetMapping("/addpost")
    public String renderAddPostPage(Principal principal, Model model) {
        if (principal != null) {
            model.addAttribute("yourName", principal.getName());
        }
        return "addpost";
    }

    @PostMapping("/addpost")
    public RedirectView addPost(String body, Principal principal) {
        System.out.println(body);
        if (principal != null) {

            Post post = new Post();
            post.setBody(body);
            ApplicationUser writer = applicationUserRepository.findByUsername(principal.getName());
            post.setWriter(writer);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
            Date currentDate = new Date();
            String createdAt = dateFormat.format(currentDate);
            post.setCreatedAt(createdAt);
            postRepository.save(post);
        }

        return new RedirectView("/myprofile");
    }

    @GetMapping("/myprofile")
    public String getMyProfilePage(Principal principal, Model model) {
        if (principal != null) {
            model.addAttribute("yourName", principal.getName());
            ApplicationUser myProfile = applicationUserRepository.findByUsername(principal.getName());
            List<Post> myPosts = (List<Post>) myProfile.getPosts();
            model.addAttribute("myPosts", myPosts);
        }

        return "profile";
    }

    @GetMapping("/allusers")
    public String renderAllUsers(Principal principal, Model model) {
        if (principal != null) {
            model.addAttribute("yourName", principal.getName());
            List<ApplicationUser> users = applicationUserRepository.findAll();
            model.addAttribute("users", users);
        }
        return "allUsers";
    }

    @GetMapping("user/{id}")
    public String renderOneUserDetail(@PathVariable int id, Principal principal, Model model) {
        if (principal != null) {
            model.addAttribute("yourName", principal.getName());
            ApplicationUser wantedUser = applicationUserRepository.findById(id).get();
            model.addAttribute("wanteduser", wantedUser);
        }
        return "oneUserDetail";
    }

    @PostMapping("/follow")
    public RedirectView follow(Principal principal, int follow) {
        System.out.println(follow);
        ApplicationUser follower = applicationUserRepository.findByUsername(principal.getName());
        ApplicationUser writer = applicationUserRepository.getOne(follow);
        follower.follow(writer);
        applicationUserRepository.save(follower);
        return new RedirectView("/allusers");
    }

    @GetMapping("/feeds")
    public String renderFeeds(Principal principal, Model model) {
        model.addAttribute("yourName", principal.getName());
        ApplicationUser me = applicationUserRepository.findByUsername(principal.getName());
        Set<ApplicationUser> usersIFollow = me.getUsersIFollow();
        model.addAttribute("IFollow", usersIFollow);
        return "feeds";
    }

}
