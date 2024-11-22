package api.security.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.security.entities.UserEntity;
import api.security.repositories.IUserRepository;

@Service
public class UserServiceImp implements IDAO<UserEntity> {
	
	@Autowired
	IUserRepository userRepository;

	@Override
	public void create(UserEntity userEntity) {
	
		userRepository.save(userEntity);
	}	

	@Override
	public List<UserEntity> readAll() {
		
		return (List<UserEntity>)userRepository.findAll();
	}

	@Override
	public Optional<UserEntity> readById(Long id) {
		
		return userRepository.findById(id);
	}
	
	public Optional<UserEntity> readByUsername(String username) {
		
		return userRepository.findByUsername(username);
	}
	
	@Override
	public void update(UserEntity userEntity) {
		
		userRepository.save(userEntity);
	}

	@Override
	public void deleteById(Long id) {		

		userRepository.deleteById(id);
	}
}
