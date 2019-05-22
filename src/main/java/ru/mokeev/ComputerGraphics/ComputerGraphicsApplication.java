package ru.mokeev.ComputerGraphics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ComputerGraphicsApplication {

	public static void main(String[] args) {

		new DrawingComponent().draw();

		SpringApplication.run(ComputerGraphicsApplication.class, args);
	}

}
