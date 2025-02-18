package com.example.products.services;

import com.example.products.entities.UserInfo;
import com.example.products.repositories.UserRepository;
import com.example.products.user.UserDetailsImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;  // Your UserRepository

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Load user from the database using the repository
        UserInfo userInfo = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        // Return a UserDetails object (this can be UserDetailsImpl in your case)
        return UserDetailsImpl.build(userInfo); // Assuming UserDetailsImpl is your custom implementation
    }
}
