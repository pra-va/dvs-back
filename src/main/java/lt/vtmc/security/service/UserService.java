package lt.vtmc.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lt.vtmc.security.model.User;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserService userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User newUser = findUserByUsername(username);
		if (newUser == null) {
			throw new UsernameNotFoundException(username + " not found.");
		} else {
			return new org.springframework.security.core.userdetails.User(newUser.getUsername(), newUser.getPassword(),
					AuthorityUtils.createAuthorityList(new String[] { "ROLE_" + newUser.getRole().toString() }));
		}
	}

	@Transactional
	public User findUserByUsername(String username) {
		return userRepository.findUserByUsername(username);
	}

}
