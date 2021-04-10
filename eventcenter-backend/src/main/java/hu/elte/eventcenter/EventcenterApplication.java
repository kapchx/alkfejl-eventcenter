package hu.elte.eventcenter;

import hu.elte.eventcenter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class EventcenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventcenterApplication.class, args);
	}

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserRepository passwordEncoder;
}
