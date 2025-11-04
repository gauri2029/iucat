package ed.iu.p566.iucat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class IucatApplication {

	public static void main(String[] args) {
		SpringApplication.run(IucatApplication.class, args);
	}

}
