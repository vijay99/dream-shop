package com.vijay.vz.dreamshop.service.cart;

import com.vijay.vz.dreamshop.exceptions.ResourceNotFoundException;
import com.vijay.vz.dreamshop.model.Cart;
import com.vijay.vz.dreamshop.repository.CartItemRepository;
import com.vijay.vz.dreamshop.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
@Service
@RequiredArgsConstructor
public class CartService implements ICartService{

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    @Override
    public Cart getCart(Long id) {
        Cart cart=cartRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("Cart not found"));
        BigDecimal totalPrice = cart.getTotalAmount();
        cart.setTotalAmount(totalPrice);
        return cartRepository.save(cart);
    }

    @Override
    public void clearCart(Long id) {
        Cart cart = getCart(id);

        cartItemRepository.deleteAllByCartId(id);
        cart.getItems().clear();
        cartRepository.deleteById(id);

    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = getCart(id);

        return cart.getTotalAmount();

    }
}
