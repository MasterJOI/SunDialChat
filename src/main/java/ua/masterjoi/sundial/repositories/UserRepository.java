package ua.masterjoi.sundial.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.masterjoi.sundial.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByActivationCode(String code);
}
