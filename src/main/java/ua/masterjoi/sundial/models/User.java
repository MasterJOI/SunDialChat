package ua.masterjoi.sundial.models;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "usr")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Username cannot be empty!")
    private String username;
    @NotBlank(message = "Password cannot be empty!")
    private String password;
    /*пароль для подтверждения
    @Transient //Значит что поле не будет внесено в БД
    @NotBlank(message = "Password confirmation cannot be empty!")
    
    private String password2;
     */
    private boolean active;

    //Поля для ввола почты и кода активации
    @Email(message = "Insert email is not correct!")
    @NotBlank(message = "Email cannot be empty!")
    private String email;
    private String activationCode;

    //Каждый пользователь имеет set сообщений, указываем названия поля cascade - втоматически изменяет или удаляет строки в зависимой таблицу при изменениях в главной
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Message> messages;

    //Какие роли у пользователя
    //ElementCollection - будет хранить отдельную таблицу с Enum
    //fetch - определяет как данные значения будут подгружаться в зависимости от вызова обьекта
    //EAGER - данные про роли подгружаються сразу при запросе пользователя - хорошо когда мало записей (как у нас)
    //LAZY - роли подгружаються только когда пользователя обратиться к полю roles - хорошо когда много записей
    //Например если у обьекта Институт будут в таблице поля нескольких тысяч студентов, то сразу их подгружать из БД не нужно

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    //Spring сам создаст таблицу user_role и сделает связь с таблицей "usr" в колонке user_id
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    //горорим что это ENUM и хранить его нужно как строку в БД
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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
        return isActive();
    }

    public boolean isAdmin() {
        return roles.contains(Role.ADMIN);
    }
}
