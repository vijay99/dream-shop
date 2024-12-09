package com.vijay.vz.dreamshop.security.user;

import com.vijay.vz.dreamshop.model.User;
import com.vijay.vz.dreamshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShopUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = Optional.ofNullable(userRepository.getUserByEmail(email))
                .orElseThrow( ()-> new UsernameNotFoundException("User not found."));
        return ShopUserDetails.buildUserDetails(user);
    }


}
