package com.example.demo.premierleague;

import com.example.demo.premierleague.controllers.MvcScoreController;
import com.example.demo.premierleague.controllers.WebFluxScoreController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = MvcScoreController.class)
@ComponentScan(basePackageClasses = WebFluxScoreController.class)
public class PremierLeagueApplication {

	public static void main(String[] args) {
		SpringApplication.run(PremierLeagueApplication.class, args);
	}

}
