package com.vijay.vz.dreamshop.repository;

import com.vijay.vz.dreamshop.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {
    Cart findByUserId(Long userId);
}
