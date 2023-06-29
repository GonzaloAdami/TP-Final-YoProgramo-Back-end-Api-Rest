/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.service;

import com.example.demo.repository.Person;
import com.example.demo.repository.PersonRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.Date;
import javax.crypto.SecretKey;

/**
 *
 * @author gonza
 */
@Service
public class PersonService implements IPersonService{

    @Autowired  
    public PersonRepository persoRepo;
    @PersistenceContext
    private EntityManager entityManager;
    
    
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

    @Override
    public Person findPersonByEmail(String email) {
        return persoRepo.findByEmail(email);
    }

    /**
     *
     * @param email
     * @param password
     * @return
     */
    @Override   
public String login(String email, String password) {
String query = "SELECT COUNT(*) FROM Person Pers WHERE Pers.email = :email AND Pers.password = :password";

    Query queryObj = entityManager.createQuery(query);
    queryObj.setParameter("email", email);
    queryObj.setParameter("password", password);
    
    boolean result = (Long) queryObj.getSingleResult() > 0;
if (result) {
            // Generar el token
            SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
            Date expirationDate = new Date(System.currentTimeMillis() + 86400000); // 24 horas
            String token = Jwts.builder()
                    .setSubject(email)
                    .setExpiration(expirationDate)
                    .signWith(secretKey)
                    .compact();

            return token;
        } else {
            return null;
        }
    
}

    @Override
    public Person findPersonByPassword(String password) {
      return persoRepo.findPersonByPassword(password);
        
    }

   

 
    }



