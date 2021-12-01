package tech.amandaam.eDoe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@RestController
@EnableSwagger2
public class EDoeApplication {

	public static void main(String[] args) {
		SpringApplication.run(EDoeApplication.class, args);
	}

	@GetMapping("/")
	@ResponseBody
	public String index() {
		return String.format("Welcome to eDoe API!");
	}
}
