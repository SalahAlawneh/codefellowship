package com.salahcodefellowship.codefellowship.service;

import com.salahcodefellowship.codefellowship.model.ApplicationUser;
import com.salahcodefellowship.codefellowship.repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    // polymorphism
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);

        if(applicationUser == null){
            System.out.println("User not found");
            throw new UsernameNotFoundException("User "+username+" does not exist");
        }
        System.out.println("User "+ username + " found");
        return applicationUser;
    }
}
