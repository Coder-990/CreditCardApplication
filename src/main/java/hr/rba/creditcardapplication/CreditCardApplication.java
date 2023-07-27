package hr.rba.creditcardapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CreditCardApplication {

	public static final String FILE_LOCATION = "file/";

	public static void main(String[] args) {
		SpringApplication.run(CreditCardApplication.class, args);
	}

}
