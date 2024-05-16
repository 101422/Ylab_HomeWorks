package ru.vladimirvorobev.ylabhomework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.vladimirvorobev.ylabhomework.annotations.EnableLogging;

@SpringBootApplication(scanBasePackages = "ru.vladimirvorobev.ylabhomework")
@EnableLogging
public class Homework5Application {

	public static void main(String[] args) {
		SpringApplication.run(Homework5Application.class, args);
	}

}
