package vn.edu.fpt.SmartHealthC;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import vn.edu.fpt.SmartHealthC.serivce.FormQuestionService;
import vn.edu.fpt.SmartHealthC.serivce.Impl.FormQuestionServiceImpl;

@SpringBootApplication
public class SmartHealthCApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartHealthCApplication.class, args);
	}

}
