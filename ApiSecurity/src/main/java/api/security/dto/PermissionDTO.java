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
		this(permissionEnum);
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PermissionEnum getPermissionEnum() {
		return permissionEnum;
	}

	public void setPermissionEnum(PermissionEnum permissionEnum) {
		this.permissionEnum = permissionEnum;
	}
}
