package ua.masterjoi.sundial.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String getLoginPage(Model model, HttpServletRequest request) {
        //Если страница с параметром ошибки, то
        if(request.getParameterMap().containsKey("error")) {
            //отдадим страницу но с параметром error
            model.addAttribute("error", true);
        }
        //Возврозаем View (страницу) с названием login а на странице тогда отобразиться блок
        // с текстом ошибки
        return "login";
    }
}
