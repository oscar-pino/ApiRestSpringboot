package api.security.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotBlank(message = "el campo no debe ser null o solo contener espacios en blanco")
	@Size(min = 5, max = 20, message = "ingrese 5 caracteres como mínimo y 20 como máximo")
    private String username;
    
    @NotBlank(message = "el campo no debe ser null o solo contener espacios en blanco")
	@Size(min = 5, message = "ingrese 5 caracteres como mínimo")
    private String password;

    @Column(name = "is_enabled")
    private boolean isEnabled;

    @Column(name = "account_No_Expired")
    private boolean accountNoExpired;

    @Column(name = "account_No_Locked")
    private boolean accountNoLocked;

    @Column(name = "credential_No_Expired")
    private boolean credentialNoExpired;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<RoleEntity> roles;

	public UserEntity() {
		
	}
	
	public UserEntity(String username, String password, List<RoleEntity> roles) {
	
		this.username = username;
		this.password = password;
		this.isEnabled = true;
		this.accountNoExpired = true;
		this.accountNoLocked = true;
		this.credentialNoExpired = true;
		this.roles = roles;
	}
	
	public UserEntity(Long id, String username, String password, List<RoleEntity> roles) {
		
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