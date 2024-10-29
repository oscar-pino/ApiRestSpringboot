package api.security.dto;

import api.security.entities.enums.PermissionEnum;

public class PermissionDTO {

    private Long id;

    private PermissionEnum permissionEnum;

	public PermissionDTO() {
	}

	public PermissionDTO(PermissionEnum permissionEnum) {
		this.permissionEnum = permissionEnum;
	}

	public PermissionDTO(Long id, PermissionEnum permissionEnum) {
		this.id = id;
		this.permissionEnum = permissionEnum;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PermissionEnum getPermission() {
		return permissionEnum;
	}

	public void setPermission(PermissionEnum permissionEnum) {
		this.permissionEnum = permissionEnum;
	}

	@Override
	public String toString() {
		return "PermissionEntity [id=" + id + ", name=" + this.permissionEnum.name() + "]";
	}    
}
