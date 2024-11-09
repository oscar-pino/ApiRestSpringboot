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

import api.security.entities.CategoryEntity;
import api.security.entities.NationalityEntity;
import api.security.entities.PermissionEntity;
import api.security.entities.RoleEntity;
import api.security.entities.UserEntity;
import api.security.entities.enums.PermissionEnum;
import api.security.entities.enums.RoleEnum;
import api.security.services.CategoryServiceImp;
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
	
	@Autowired
	CategoryServiceImp categoryServiceImp;
	
	@Bean
	CommandLineRunner init() {

		return args -> {

			Set<NationalityEntity> nationalities = nationalityServiceImp.readAll().stream().collect(Collectors.toSet());

			List<PermissionEntity> permissions = permissionServiceImp.readAll();

			List<RoleEntity> roles = roleServiceImp.readAll();

			List<UserEntity> users = userServiceImp.readAll();
			
			Set<CategoryEntity> categories = categoryServiceImp.readAll().stream().collect(Collectors.toSet());

			if (nationalities.isEmpty()) {

				nationalities.add(new NationalityEntity("chile", "español"));
				nationalities.add(new NationalityEntity("peru", "español"));
				nationalities.add(new NationalityEntity("ee.uu", "ingles"));
				nationalities.add(new NationalityEntity("francia", "frances"));
				nationalities.add(new NationalityEntity("brasil", "portugues"));

				nationalities.stream().forEach(n -> nationalityServiceImp.create(n));
			}
			
			if(categories.isEmpty()) {
				
				categories.add(new CategoryEntity("Ficción", "Trata sobre hechos irreales e imaginarios."));
				categories.add(new CategoryEntity("Novela", "Acerca del comportamiento diario de la humanidad."));
				categories.add(new CategoryEntity("Terror", "Describe historias en donde se busca asustar al lector."));
				categories.add(new CategoryEntity("Fabulas", "Relata historias en donde los animales pueden hablar."));
				categories.add(new CategoryEntity("Cuento", "Relatos más cortos, generalmente con una única trama o incidente"));
				
				categories.stream().forEach(c -> categoryServiceImp.create(c));
				
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
					 
					 roles.add(new RoleEntity(RoleEnum.ADMIN, adminPermission));
					 roles.add(new RoleEntity(RoleEnum.DEVELOPER, developerPermission));
					 roles.add(new RoleEntity(RoleEnum.INVITED, invitedPermission));
					 roles.add(new RoleEntity(RoleEnum.USER, userPermission));					
					}
			}			

			if (users.isEmpty()) {			
				
				users.add(new UserEntity(1l, "oscar", passwordEncoder.encode("1234"), List.of(roles.get(0))));				
				users.add(new UserEntity(2l, "yonathan", passwordEncoder.encode("1234"), List.of(roles.get(1))));
				users.add(new UserEntity(3l, "jose", passwordEncoder.encode("1234"), List.of(roles.get(2))));				
				users.add(new UserEntity(4l, "juan", passwordEncoder.encode("1234"), List.of(roles.get(3))));
				
				users.stream().forEach(u -> userServiceImp.create(u));
			}
		};
	}
}
