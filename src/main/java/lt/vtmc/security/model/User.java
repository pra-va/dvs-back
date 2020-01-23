package lt.vtmc.security.model;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "User")
public class User {

	@NotEmpty
	private String password;

	@Id
	@Size(min = 5)
	private String username;

	@Enumerated
	private Role role;

	public User(String password, String username, Role role) {
		this.password = password;
		this.username = username;
		this.role = role;
	}

	public User() {
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
