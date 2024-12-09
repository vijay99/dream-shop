package com.vijay.vz.dreamshop.repository;

import com.vijay.vz.dreamshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByEmail(String email);

    User getUserByEmail(String email);
}
