package com.example.emergencyapp.AAA;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private long id;
    private String username;
    private String password;
    private String email;

    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;

    public static UserDetailsImpl build(User user) {
        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                true, // accountNonExpired
                true, // accountNonLocked
                true, // credentialsNonExpired
                true  // enabled
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password; // Повертаємо реальний пароль
    }

    @Override
    public String getUsername() {
        return username; // Повертаємо реальне ім'я користувача
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Використовуємо значення з поля
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Використовуємо значення з поля
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Використовуємо значення з поля
    }

    @Override
    public boolean isEnabled() {
        return true; // Використовуємо значення з поля
    }
}
