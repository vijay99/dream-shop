package com.vijay.vz.dreamshop.repository;

import com.vijay.vz.dreamshop.model.Cart;
import com.vijay.vz.dreamshop.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    void deleteAllByCartId(Long id);
}
