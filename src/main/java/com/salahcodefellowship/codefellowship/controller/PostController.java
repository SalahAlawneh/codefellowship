package com.salahcodefellowship.codefellowship.controller;

import com.salahcodefellowship.codefellowship.model.DBUser;
import com.salahcodefellowship.codefellowship.model.Post;
import com.salahcodefellowship.codefellowship.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class PostController {

    @Autowired
    PostRepository postRepository;

    @PostMapping("/addPost")
    public RedirectView addPost(String body, String createdAt) {
        Post post = new Post(body, createdAt);
        postRepository.save(post);

        return new RedirectView("/myprofile");
    }


}
