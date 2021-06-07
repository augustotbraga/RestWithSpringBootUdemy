package br.com.erudio.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	public List<PersonVO> findAll() {
		List<Person> listPerson = repository.findAll(); 
		return DozerConverter.parseListObjects(listPerson, PersonVO.class);
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
	
}
