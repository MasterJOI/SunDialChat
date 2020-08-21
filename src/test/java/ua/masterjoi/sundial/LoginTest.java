package ua.masterjoi.sundial;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//Клас-контейнер других тестов
//Аннотация Junit(фреймворк для тестирования) которая будет стартовать тесты
@RunWith(SpringRunner.class)
//Указывает что запускаем тесты в SpringBoot и будет находить автоматически все конфигурации
@SpringBootTest(classes = {Application.class})
//Спринг автоматически будет создвать структуру классов которая подменяет слой SpringMvc(тоесть тест будет проходить в фейковом окруженни, что будет быстрее и безопасности)
@AutoConfigureMockMvc
public class LoginTest {

    @Autowired
    private MockMvc mockMvc;

    //@Test - помечает все тестовые методы
    @Test
    public void test() throws Exception {
        //Проверили что контроллер с контекста успешно подключен
        //assertThat(controller).isNotNull();
        //Указывем что нам нужно выполнить get-запрос на адрес("/")
        this.mockMvc.perform(get("/"))
                //Метод будет выводить полученный результат в консоль
                .andDo(print())
                //Для сравнения полученого результата с тем что мы ожидаем(что isOk)
                .andExpect(status().isOk())
                //Проверяем что вернеться результат и сравниваем что он содержит в себе указанную строку
				.andExpect(content().string(containsString("Hello, World")));
    }
}

