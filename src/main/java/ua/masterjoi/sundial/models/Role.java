package ua.masterjoi.sundial.models;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN, USER;

    @Override
    public String getAuthority() {
        return name(); //Возвращаем строковое представление enum
    }
}
