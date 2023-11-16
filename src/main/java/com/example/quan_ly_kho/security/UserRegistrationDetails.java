package com.example.quan_ly_kho.security;

import com.example.quan_ly_kho.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
public class UserRegistrationDetails implements UserDetails {
    private String username;
    private String password;
    private Boolean isEnabled;
    private List<GrantedAuthority> authorities;

    public UserRegistrationDetails(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.isEnabled = user.getStatus();
        this.authorities = List.of(new SimpleGrantedAuthority( user.getRole().name()));
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
