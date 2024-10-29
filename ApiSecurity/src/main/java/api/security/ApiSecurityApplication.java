package api.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import api.security.entities.NationalityEntity;
import api.security.entities.PermissionEntity;
import api.security.entities.RoleEntity;
import api.security.entities.UserEntity;
import api.security.entities.enums.PermissionEnum;
import api.security.entities.enums.RoleEnum;
import api.security.services.NationalityServiceImp;
import api.security.services.PermissionServiceImp;
import api.security.services.RoleServiceImp;
import api.security.services.UserServiceImp;

@SpringBootApplication
public class ApiSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiSecurityApplication.class, args);
	}

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserServiceImp userServiceImp;

	@Autowired
	NationalityServiceImp nationalityServiceImp;

	@Autowired
	RoleServiceImp roleServiceImp;

	@Autowired
	PermissionServiceImp permissionServiceImp;

	// Bean que se ejecuta al arranque de la aplicación
	@Bean
	CommandLineRunner init() {

		return args -> {

			Set<NationalityEntity> nationalities = nationalityServiceImp.readAll().stream().collect(Collectors.toSet());

			List<PermissionEntity> permissions = permissionServiceImp.readAll();

			List<RoleEntity> roles = roleServiceImp.readAll();

			List<UserEntity> users = userServiceImp.readAll();

			if (nationalities.isEmpty()) {

				nationalities.add(new NationalityEntity("chile", "español"));
				nationalities.add(new NationalityEntity("peru", "español"));
				nationalities.add(new NationalityEntity("ee.uu", "ingles"));
				nationalities.add(new NationalityEntity("francia", "frances"));
				nationalities.add(new NationalityEntity("brasil", "portugues"));

				nationalities.stream().forEach(n -> nationalityServiceImp.create(n));
			}

			if (permissions.isEmpty()) {
				
				List<PermissionEntity> adminPermission = new ArrayList<PermissionEntity>();
				adminPermission.add(new PermissionEntity(PermissionEnum.CREATE));
				adminPermission.add(new PermissionEntity(PermissionEnum.READ));
				adminPermission.add(new PermissionEntity(PermissionEnum.UPDATE));
				adminPermission.add(new PermissionEntity(PermissionEnum.DELETE));
				
				 List<PermissionEntity> developerPermission = new ArrayList<PermissionEntity>(); 
				 developerPermission.add(new PermissionEntity(PermissionEnum.CREATE));
				 developerPermission.add(new PermissionEntity(PermissionEnum.READ)); 
				 developerPermission.add(new PermissionEntity(PermissionEnum.UPDATE));	
				 
				 List<PermissionEntity> invitedPermission = new ArrayList<PermissionEntity>();
				 invitedPermission.add(new PermissionEntity(PermissionEnum.READ));	
				 
				 List<PermissionEntity> userPermission = new ArrayList<PermissionEntity>();
				 userPermission.add(new PermissionEntity(PermissionEnum.CREATE));
				 userPermission.add(new PermissionEntity(PermissionEnum.READ));		
				 				 
				 if (roles.isEmpty()) {
					 
					 roles.add(new RoleEntity(1l, RoleEnum.ADMIN, adminPermission));
					 roles.add(new RoleEntity(2l, RoleEnum.DEVELOPER, developerPermission));
					 roles.add(new RoleEntity(3l, RoleEnum.INVITED, invitedPermission));
					 roles.add(new RoleEntity(4l, RoleEnum.USER, userPermission));					
					}
			}			

			if (users.isEmpty()) {			
				
				users.add(new UserEntity(1l, "oscar", passwordEncoder.encode("1234"), true, true, true, true, List.of(roles.get(0))));				
				users.add(new UserEntity(2l, "yonathan", passwordEncoder.encode("1234"), true, true, true, true, List.of(roles.get(1))));
				users.add(new UserEntity(3l, "jose", passwordEncoder.encode("1234"), true, true, true, true, List.of(roles.get(2))));				
				users.add(new UserEntity(4l, "juan", passwordEncoder.encode("1234"), true, true, true, true, List.of(roles.get(3))));
				
				users.stream().forEach(u -> userServiceImp.create(u));
			}
		};
	}
}
