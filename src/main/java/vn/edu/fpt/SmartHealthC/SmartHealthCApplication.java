package vn.edu.fpt.SmartHealthC;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import vn.edu.fpt.SmartHealthC.serivce.FormQuestionService;
import vn.edu.fpt.SmartHealthC.serivce.Impl.FormQuestionServiceImpl;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(title="SmartHealthC API",
				version = "1.0",
				description = "App checking health for old people"
		)
)
public class SmartHealthCApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartHealthCApplication.class, args);
	}

}
