package com.salahcodefellowship.codefellowship.repository;

import com.salahcodefellowship.codefellowship.model.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUser,Integer> {
public ApplicationUser findByUsername(String userName);
}
