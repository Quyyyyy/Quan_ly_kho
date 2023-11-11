package com.example.quan_ly_kho.security;

import com.example.quan_ly_kho.entity.User;
import com.example.quan_ly_kho.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username).map(UserRegistrationDetails::new).orElseThrow(
                ()->new UsernameNotFoundException("User not found with username:" + username)
        );
    }
}
