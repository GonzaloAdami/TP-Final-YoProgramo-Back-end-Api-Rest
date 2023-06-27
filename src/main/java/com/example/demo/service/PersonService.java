/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.service;

import com.example.demo.repository.Person;
import com.example.demo.repository.PersonRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author gonza
 */
@Service
public class PersonService implements IPersonService{

    @Autowired  
    public PersonRepository persoRepo;
    
    @Override
    public List<Person> verPersonas() {
    return persoRepo.findAll();
    }

    @Override
    public void crearPersona(Person pers) {
        persoRepo.save(pers);
    }
    @Override
    public void borrarPersona(Long id) {
        persoRepo.deleteById(id);
    }

    @Override
    public Person buscarPersona(Long id) {
     return persoRepo.findById(id).orElse(null);
    }
    
}
