package api.security.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.security.entities.NationalityEntity;
import api.security.repositories.INationalityRepository;

@Service
public class NationalityServiceImp implements IDAO<NationalityEntity> {

	@Autowired
	INationalityRepository nationalityRepository;

	@Override
	public void create(NationalityEntity nation) {
		
		nationalityRepository.save(nation);
	}
	
	@Override
	public List<NationalityEntity> readAll() {

		return (List<NationalityEntity>) nationalityRepository.findAll();
	}

	@Override
	public Optional<NationalityEntity> readById(Long id) {

		return nationalityRepository.findById(id);
	
	}

	public Optional<NationalityEntity> readByName(String name) {

		return nationalityRepository.findByName(name);
	}
	
	
	@Override
	public void update(NationalityEntity nationality) {

		nationalityRepository.save(nationality);		
	}

	@Override
	public void deleteById(Long id) {
		
		nationalityRepository.deleteById(id);
	}
}
