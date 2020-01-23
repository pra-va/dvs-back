package lt.vtmc.security.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lt.vtmc.security.dao.UserRepository;
import lt.vtmc.security.model.Role;
import lt.vtmc.security.model.User;

@Service
public class CreateUserService {

	@Autowired
	private UserRepository userRepository;

	/**
	 * Method to create test users.
	 * 
	 * @param username
	 * @param password
	 * @param role
	 */
	@Transactional
	public void createUser(String username, String password) {
		User newUser = new User();
		newUser.setUsername(username);

		PasswordEncoder encoder = new BCryptPasswordEncoder();

		newUser.setPassword(encoder.encode(password));

		newUser.setRole(Role.USER);
		userRepository.save(newUser);
	}

	@Transactional
	public void createSystemAdministrator(String username, String password) {
		User newUser = new User();
		newUser.setUsername(username);

		PasswordEncoder encoder = new BCryptPasswordEncoder();

		newUser.setPassword(encoder.encode(password));

		newUser.setRole(Role.ADMIN);
		userRepository.save(newUser);
	}
}
