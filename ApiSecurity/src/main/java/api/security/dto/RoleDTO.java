package api.security.dto;

import java.util.List;
import java.util.Set;

import api.security.entities.PermissionEntity;
import api.security.entities.enums.RoleEnum;

public class RoleDTO {
	
    private Long id;
    
    private RoleEnum roleEnum;
    
    private List<PermissionEntity> permissionList;

	public RoleDTO() {
		
	}

	public RoleDTO(RoleEnum roleEnum) {
		this.roleEnum = roleEnum;
	}

	public RoleDTO(RoleEnum roleEnum, List<PermissionEntity> permissionList) {
		this.roleEnum = roleEnum;
		this.permissionList = permissionList;
	}

	public RoleDTO(Long id, RoleEnum roleEnum, List<PermissionEntity> permissionList) {
		this.id = id;
		this.roleEnum = roleEnum;
		this.permissionList = permissionList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RoleEnum getRoleEnum() {
		return roleEnum;
	}

	public void setRoleEnum(RoleEnum roleEnum) {
		this.roleEnum = roleEnum;
	}

	public List<PermissionEntity> getPermissionList() {
		return permissionList;
	}

	public void setPermissionList(List<PermissionEntity> permissionList) {
		this.permissionList = permissionList;
	}

	@Override
	public String toString() {
		return "RoleEntity [id=" + id + ", roleEnum=" + roleEnum + ", permissionListSize=" + permissionList.size() + "]";
	}    

}
