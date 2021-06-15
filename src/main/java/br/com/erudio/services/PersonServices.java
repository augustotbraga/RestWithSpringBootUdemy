package br.com.erudio.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.erudio.converter.DozerConverter;
import br.com.erudio.data.model.Person;
import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.exception.ResourceNotFoundException;
import br.com.erudio.repository.PersonRepository;

@Service
public class PersonServices {
	
	@Autowired
	private PersonRepository repository;
	
	public PersonVO findById(Long id) {
		Person person = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));
		return DozerConverter.parseObject(person, PersonVO.class);
	}
	
	public Page<PersonVO> findAll(Pageable pageable) {
		var page = repository.findAll(pageable); 
		return page.map(this::convertToPersonVO);
	}
	
	public Page<PersonVO> findByName(String firstName, Pageable pageable) {
		var page = repository.findByName(firstName, pageable); 
		return page.map(this::convertToPersonVO);
	}
	
	public PersonVO convertToPersonVO(Person entity) {
		return DozerConverter.parseObject(entity, PersonVO.class);
	}

	public PersonVO create(PersonVO personVO) {
		Person person = repository.save(DozerConverter.parseObject(personVO, Person.class));
		return DozerConverter.parseObject(person, PersonVO.class);
	}
	
	public PersonVO update(PersonVO personVO) {
		Person entity = repository.findById(personVO.getKey()).orElseThrow(
				() -> new ResourceNotFoundException("No records found for this id"));
		entity.setFirstName(personVO.getFirstName());
		entity.setLastName(personVO.getLastName());
		entity.setEnabled(personVO.getEnabled());
		entity.setAddress(personVO.getAddress());
		entity.setGender(personVO.getGender());
		 
		Person person = repository.save(entity);
		return DozerConverter.parseObject(person, PersonVO.class);
	}
	
	public void delete(Long id) {
		Person entity = repository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("No records found for this id"));
		repository.delete(entity);
	}
	
	@Transactional
	public PersonVO disablePerson(Long id) {
		Person entity = repository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("No records found for this id"));
		entity.setEnabled(false);
		repository.disablePerson(entity.getId());
		return DozerConverter.parseObject(entity, PersonVO.class);
	}
	
}
