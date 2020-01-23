package lt.vtmc.security.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import lt.vtmc.security.model.User;

public interface UserRepository extends JpaRepository<User, String> {
	User findByUsername(String username);

}
