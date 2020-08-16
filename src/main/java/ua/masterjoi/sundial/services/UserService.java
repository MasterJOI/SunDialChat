package ua.masterjoi.sundial.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ua.masterjoi.sundial.models.Role;
import ua.masterjoi.sundial.models.User;
import ua.masterjoi.sundial.repositories.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${hostname}")
    private String hostname;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public boolean addUser(User user) {
        User userFromDb = userRepository.findByUsername(user.getUsername());

        if(userFromDb != null) {
            return false;
        }
        //Поставили флажок активности, и спискои из одного елемента указали роль
        user.setActive(false);
        user.setRoles(Collections.singleton(Role.USER));
        //генерируем код активации после того как пользователь перешел по ссылке с почты
        user.setActivationCode(UUID.randomUUID().toString());
        //Добавляем хешированый пароль
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //Сохраняем пользователя
        userRepository.save(user);

        sendMessage(user);

        return true;
    }

    private void sendMessage(User user) {
        //Если почта есть
        if(!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Dear, %s! \n" +
                            "Thank you for creating your SunDialChat Account!\n" +
                            "To complete your registration, click the link below:\n" +
                            "http://%s/activate/%s \n" +
                            "Yours truly,\n" +
                            "The SunDialChat Team :)",
                    user.getUsername(),
                    hostname,
                    user.getActivationCode()
            );
            notificationService.send(user.getEmail(), "Activation code", message);
        }
    }

    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);
        if(user == null) {
            return false;
        }
        user.setActive(true);
        user.setActivationCode(null);

        userRepository.save(user);

        return true;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void saveUser(User user, String username, Map<String, String> form) {
        user.setUsername(username);
        //Перед устоновкой пользователю ролей, проверим что они установлены данному пользователю
        //Берем список ролей, преобразовав их из Enum в сет из строк
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        //Предварительно очистим все роли пользователя
        user.getRoles().clear();

        //Проверяем что форма содержит роли для пользователя, так как тут есть и другие поля(token, id...)
        for(String key : form.keySet()) {
            //Если роли содержат такой ключ то добавляем роль(Enum ищет у себя такое значение и возвращает его)
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userRepository.save(user);
    }

    public void updateProfile(User user, String password, String email) {
        String userEmail = user.getEmail();
        //Проверяем 2 случая так как что измененный емейл что существующий могут быть равны null а equals тогда может словить NullPointerExeption
        boolean isEmailChanged = (email != null && !email.equals(userEmail)) ||
                (userEmail != null && !userEmail.equals(email));
        if(isEmailChanged) {
            user.setEmail(email);

            if(!StringUtils.isEmpty(email)) {
                user.setActivationCode(UUID.randomUUID().toString());
            }
        }

        if(!StringUtils.isEmpty(password)) {
            user.setPassword(passwordEncoder.encode(password));
        }


        userRepository.save(user);

        sendMessage(user);

        if(isEmailChanged) {
            sendMessage(user);
        }
    }
}
