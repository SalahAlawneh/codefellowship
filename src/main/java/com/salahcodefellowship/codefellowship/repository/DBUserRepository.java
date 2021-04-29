package com.salahcodefellowship.codefellowship.repository;

import com.salahcodefellowship.codefellowship.model.DBUser;
import org.springframework.data.repository.CrudRepository;

public interface DBUserRepository extends CrudRepository<DBUser, Integer> {
    public DBUser findByUsername(String userName);
}
