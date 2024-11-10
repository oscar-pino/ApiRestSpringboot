package api.security.dto;

import java.util.List;

import api.security.entities.RoleEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserDTO {

	    private Long id;

	    private String username;

	    private String password;

	    private boolean isEnabled;

	    private boolean accountNoExpired;

	    private boolean accountNoLocked;

	    private boolean credentialNoExpired;
	    
	    private List<RoleEntity> roles;

		public UserDTO() {
			
		}
		
		public UserDTO(String username, String password, List<RoleEntity> roles) {
		
			this.username = username;
			this.password = password;
			this.isEnabled = true;
			this.accountNoExpired = true;
			this.accountNoLocked = true;
			this.credentialNoExpired = true;
			this.roles = roles;
		}
		
		public UserDTO(Long id, String username, String password, List<RoleEntity> roles) {
			
			this(username, password, roles);
			this.id = id;
		}	
		
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public boolean isEnabled() {
			return isEnabled;
		}

		public void setEnabled(boolean isEnabled) {
			this.isEnabled = isEnabled;
		}

		public boolean isAccountNoExpired() {
			return accountNoExpired;
		}

		public void setAccountNoExpired(boolean accountNoExpired) {
			this.accountNoExpired = accountNoExpired;
		}

		public boolean isAccountNoLocked() {
			return accountNoLocked;
		}

		public void setAccountNoLocked(boolean accountNoLocked) {
			this.accountNoLocked = accountNoLocked;
		}

		public boolean isCredentialNoExpired() {
			return credentialNoExpired;
		}

		public void setCredentialNoExpired(boolean credentialNoExpired) {
			this.credentialNoExpired = credentialNoExpired;
		}

		public List<RoleEntity> getRoles() {
			return roles;
		}

		public void setRoles(List<RoleEntity> roles) {
			this.roles = roles;
		}

		@Override
		public String toString() {
			return "UserEntity [id=" + id + ", username=" + username + ", password=" + password + ", isEnabled=" + isEnabled
					+ ", accountNoExpired=" + accountNoExpired + ", accountNoLocked=" + accountNoLocked
					+ ", credentialNoExpired=" + credentialNoExpired + ", rolesSize=" + roles.size() + "]";
		}    
}
