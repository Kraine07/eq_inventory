package kraine.app.eq_inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("kraine.app")
public class EqInventoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(EqInventoryApplication.class, args);
	}

}
