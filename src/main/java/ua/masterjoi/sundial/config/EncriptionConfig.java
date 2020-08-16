package ua.masterjoi.sundial.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class EncriptionConfig {
    //Бин для хешировщика паролей(можно подключать в любом месте)
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		//strength - надежность ключа
		return new BCryptPasswordEncoder(8);
	}
}
