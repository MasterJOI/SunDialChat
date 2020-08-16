package ua.masterjoi.sundial.controllers;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ControllerUtils {
    static Map<String, String> getErrors(BindingResult bindingResult) {
        //Получаем list с ошибками из bindingResult и преобразуем его в Map с ошибками
        Collector<FieldError, ?, Map<String, String>> collector = Collectors.toMap(
                //отображаем название ошибки
                fieldError -> fieldError.getField() + "Error",
                FieldError::getDefaultMessage
        );

        return bindingResult.getFieldErrors().stream().collect(collector);
    }
}
