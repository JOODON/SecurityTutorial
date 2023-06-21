package com.example.securitytutorial.Repository;

import com.example.securitytutorial.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUserName(String username);

    User findByEmail(String email);
}
