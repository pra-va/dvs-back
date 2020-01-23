package lt.vtmc.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lt.vtmc.security.dao.SystemAdminRepository;
import lt.vtmc.security.model.SystemAdmin;

@Service
public class SystemAdminService implements UserDetailsService {

	@Autowired
	private SystemAdminRepository systemAdminRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		SystemAdmin admin = findAdminByUsername(username);
		if (admin == null) {
			throw new UsernameNotFoundException(username + " not found.");
		} else {
			return new org.springframework.security.core.userdetails.User(admin.getUsername(), admin.getPassword(),
					AuthorityUtils.createAuthorityList(new String[] { "ROLE_" + admin.getRole().getName() }));
		}
	}

	@Transactional
	public SystemAdmin findAdminByUsername(String username) {
		return systemAdminRepository.findByUsername(username);
	}

}
