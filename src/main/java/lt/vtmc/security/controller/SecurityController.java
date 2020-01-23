package lt.vtmc.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lt.vtmc.security.model.CreateUserCommand;
import lt.vtmc.security.service.CreateUserService;
import lt.vtmc.security.service.UserService;

@RestController
public class SecurityController {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	private CreateUserService createUserService;

	@Autowired
	private UserService userService;

	@RequestMapping(path = "/api/loggedAdmin", method = RequestMethod.GET)
	public String getLoggedInUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String currentUsername = authentication.getName();
			return currentUsername;
		}
		return "not logged";
	}

	@RequestMapping(path = "/api/createadmin", method = RequestMethod.POST)
	public void createAdmin(@RequestBody CreateUserCommand command) {
		createUserService.createSystemAdministrator(command.getUsername(), command.getPassword());
	}

	@RequestMapping(path = "/api/createuser", method = RequestMethod.POST)
	public void createUser(@RequestBody CreateUserCommand command) {
		createUserService.createUser(command.getUsername(), command.getPassword());
	}

	@GetMapping(path = "/api/find/{username}")
	public void findUser(@PathVariable String username) {
		userService.findUserByUsername(username);
	}

}
