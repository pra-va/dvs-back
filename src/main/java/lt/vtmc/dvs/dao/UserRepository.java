package lt.vtmc.dvs.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import lt.vtmc.dvs.model.User;

public interface UserRepository extends JpaRepository<User, String> {
	User findByUsername(String username);

}
