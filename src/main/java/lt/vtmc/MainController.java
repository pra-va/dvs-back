package lt.vtmc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

	@GetMapping("/")
	public String helloAll() {
		return "Hello, everybody!";
	}

	@GetMapping("/api/user")
	public String helloUser() {
		return "Hello, User!";
	}

	@GetMapping("/api/admin")
	public String helloAdmin() {
		return "Hello, Admin!";
	}
}
