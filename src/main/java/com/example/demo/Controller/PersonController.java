package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Person;
import com.example.demo.repository.PersonRepository;

@RestController
@RequestMapping("/api")
public class PersonController {
	
	@Autowired
	private PersonRepository repository;
	
	
	@GetMapping("/persons")
	public List<Person> allPersons(){
		return repository.findAll();
	}
	
	@GetMapping("/person/{name}")
	public List<Person> findByName(@PathVariable("name") String name) {
		return repository.findByName(name);
	}
	
	@PostMapping("/person")
	public Person createPerson(@RequestBody Person person) {
		return repository.save(person);
	}
	
	@PutMapping("/person/{id}")
	public Person updatePerson(@PathVariable int id ,@RequestBody Person person) {
		return repository.save(person);
	}
	
	@DeleteMapping("/person/{id}")
	public void deletePerson(@PathVariable("id") Long id) {
		repository.deleteById(id);
	}
}

