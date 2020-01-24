package lt.vtmc.dvs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lt.vtmc.dvs.dao.UserRepository;
import lt.vtmc.dvs.model.User;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User newUser = findUserByUsername(username);
		if (newUser == null) {
			throw new UsernameNotFoundException(username + " not found.");
		} else {
			return new org.springframework.security.core.userdetails.User(newUser.getUsername(), newUser.getPassword(),
					AuthorityUtils.createAuthorityList(new String[] { "ROLE_" + newUser.getRole() })); //ar ROle priimtu string spring security?
		}
	}

	@Transactional
	public User findUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	@Transactional
	public void createUser(String username, String password) {
		User newUser = new User();
		newUser.setUsername(username);

		PasswordEncoder encoder = new BCryptPasswordEncoder();

		newUser.setPassword(encoder.encode(password));

		newUser.setRole("USER");
		userRepository.save(newUser);
	}

	@Transactional
	public void createSystemAdministrator(String username, String password) {
		User newUser = new User();
		newUser.setUsername(username);

		PasswordEncoder encoder = new BCryptPasswordEncoder();

		newUser.setPassword(encoder.encode(password));

		newUser.setRole("ADMIN");
		userRepository.save(newUser);
	}
}
