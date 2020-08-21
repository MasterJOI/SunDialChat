package ua.masterjoi.sundial.util;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*Обработчик который будет добавлять заголовок к ответам сервера, что будет оповещать turbolink что нужно сделать redirect на другую страницу*/
public class RedirectInterceptor extends HandlerInterceptorAdapter {
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if(modelAndView != null) {
            /*Данная строка будет содержать все елементы с запроса после знака ?, но так как они не всегда присутствуют, то есть проверка на null*/
            String arguments = request.getQueryString() != null ? request.getQueryString() : "";
            String url = request.getRequestURI().toString() + "?" + arguments;
            response.setHeader("Turbolinks-Location", url);
        }
    }
}
