package ua.masterjoi.sundial.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import ua.masterjoi.sundial.models.User;
import ua.masterjoi.sundial.models.dto.CaptchaResponseDto;
import ua.masterjoi.sundial.services.UserService;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {

    private final static String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    @Autowired
    private UserService userService;

    //Секретный ключ
    @Value("${recaptcha.secret}")
    private String secret;

    //Подготовили переменную с RestTemplate
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(
            //Передаем переменную запроса для recaptcha
            @RequestParam("g-recaptcha-response") String captchaResponse,
            //Сделали вместо поля в User, параметр
            @RequestParam("password2") String passwordConfirm,
            @Valid User user,
            BindingResult bindingResult,
            Model model)
    {
        //Заполняем шаблон запроса recaptcha
        //Создали url
        String url = String.format(CAPTCHA_URL, secret, captchaResponse);
        //На который отправляем post запрос через restTemplate
        CaptchaResponseDto response = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDto.class);

        if(!response.isSuccess()) {
            model.addAttribute("captchaError", "Fill captcha!");
        }


        boolean isConfirmEmpty = StringUtils.isEmpty(passwordConfirm);
        if(isConfirmEmpty) {
            model.addAttribute("password2Error", "Password confirmation cannot be empty!");
        }

        //Если пароли не одинаковые - выводим ошибку
        if(user.getPassword() != null && !user.getPassword().equals(passwordConfirm)) {
            model.addAttribute("passwordError", "Passwords are different!");
            return "registration";
        }
        //Проверяем если в bindingResult есть ошибки, преобразуем их
        if(isConfirmEmpty || bindingResult.hasErrors() || !response.isSuccess()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            //Ложим их в model
            model.mergeAttributes(errors);
            //Чтобы не сохранялся потом невалидный пользователь - отправляем его на страницу регистрации
            return "registration";
        }
        if(!userService.addUser(user)) {
            model.addAttribute("usernameError", "User exists!");
            return "registration";
        }
        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        //code - код активации
        boolean isActivated = userService.activateUser(code);

        if(isActivated) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "User successfully activated!");
        } else {
            model.addAttribute("messageType","danger");
            model.addAttribute("message","Activation code is not found!");
        }
        return "login";
    }

}
