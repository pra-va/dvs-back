package lt.vtmc;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

	@GetMapping("/")
	public String helloAll() {
		return "Hello, everybody!";
	}

	@GetMapping("/api/user")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public String helloUser() {
		return "Hello, User!";
	}

	@GetMapping("/api/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String helloAdmin() {
		return "Hello, Admin!";
	}
}
