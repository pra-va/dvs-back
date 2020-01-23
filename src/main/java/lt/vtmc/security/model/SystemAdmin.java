package lt.vtmc.security.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "SystemAdmin")
public class SystemAdmin {

	@NotEmpty
	private String password;

	@Id
	@Size(min = 5)
	private String username;

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.DETACH })
	@JoinColumn(name = "ROLE_ID")
	private Role role;

	public SystemAdmin(String password, String username, Role role) {
		this.password = password;
		this.username = username;
		this.role = role;
	}

	public SystemAdmin() {
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
