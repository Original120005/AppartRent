package com.rentalapp.security;

import com.rentalapp.models.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@AllArgsConstructor
public class PersonDetails implements UserDetails {

    private final User person; // Пользователь, для которого предоставляются данные безопасности

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Возвращает роли пользователя в виде коллекции GrantedAuthority
        return Collections.singletonList(new SimpleGrantedAuthority(person.getRole().name()));
    }

    @Override
    public String getPassword() {
        // Возвращает пароль пользователя
        return this.person.getPassword();
    }

    @Override
    public String getUsername() {
        // Возвращает email пользователя, используемый в качестве имени пользователя
        return this.person.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        // Указывает, что аккаунт пользователя не просрочен
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        // Указывает, что аккаунт пользователя не заблокирован
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Указывает, что учетные данные пользователя не просрочены
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        // Указывает, активен ли пользовательский аккаунт
        return UserDetails.super.isEnabled();
    }

    public User getPerson() {
        // Возвращает объект пользователя
        return this.person;
    }

    // Закомментированный метод для изменения объекта Person
    // public void setPerson(Person person) {
    //     this.person = person;
    // }
}

