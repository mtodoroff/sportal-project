package com.sportal.model.repository;

import com.sportal.model.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    //Todo all methods
    User findUserByEmail(String email);
    Optional<User> findUserByUsername(String username);
    User findUserById(long id);
}
