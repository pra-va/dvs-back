package lt.vtmc.security.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import lt.vtmc.security.model.SystemAdmin;

public interface SystemAdminRepository extends JpaRepository<SystemAdmin, String> {
	SystemAdmin findByUsername(String username);

}
