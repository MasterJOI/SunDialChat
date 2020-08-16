package ua.masterjoi.sundial.models.dto;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Set;

@Data
//Клас для приема ответа от Recaptcha которая вернет json и из него нужно взять только нужные поля
@JsonIgnoreProperties(ignoreUnknown = true) //игнорируем те поля которые не определили
public class CaptchaResponseDto {
    private boolean success;
    //Определили что в переменную ложим значение с поля "error-codes"
    @JsonAlias("error-codes")
    private Set<String> errorCodes;
}
