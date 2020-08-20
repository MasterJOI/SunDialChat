package ua.masterjoi.sundial;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ua.masterjoi.sundial.controllers.MainController;

import static org.assertj.core.api.Assertions.assertThat;

//Клас-контейнер других тестов
//Аннотация Junit(фреймворк для тестирования) которая будет стартовать тесты
@RunWith(SpringRunner.class)
//Указывает что запускаем тесты в SpringBoot и будет находить автоматически все конфигурации
@SpringBootTest(classes = {Application.class})
public class LoginTest {

    @Autowired
    private MainController controller;

    //@Test - помечает все тестовые методы
    @Test
    public void test() throws Exception {
        //Проверили что контроллер с контекста успешно подключен
        assertThat(controller).isNotNull();
    }
}
