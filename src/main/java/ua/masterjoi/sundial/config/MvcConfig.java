package ua.masterjoi.sundial.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ua.masterjoi.sundial.util.RedirectInterceptor;

//Хранит конфигурацию веб-слоя
@Configuration
public class MvcConfig implements WebMvcConfigurer {

	@Value("${upload.path}")
	private String uploadPath;

	//для Recaptcha
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

    public void addViewControllers(ViewControllerRegistry registry) {
        //Уже встроенный шаблон авторизации в Spring
		registry.addViewController("/login").setViewName("login");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/img/**")
//				"file://" - указывает что протокол file(находиться в файловой системе)
//				uploadPath - наш путь и "/"
				.addResourceLocations("file://" + uploadPath + "/");
		registry.addResourceHandler("/static/**")
//				classpath - файлы будут искаться в корне проэкта
				.addResourceLocations("classpath:/static/");
	}

	/*Регистрируем Interceptor с заголовком для turbolink*/
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new RedirectInterceptor());
	}
}
