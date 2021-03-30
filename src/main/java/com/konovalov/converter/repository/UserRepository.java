package com.konovalov.converter.repository;

import com.konovalov.converter.model.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
