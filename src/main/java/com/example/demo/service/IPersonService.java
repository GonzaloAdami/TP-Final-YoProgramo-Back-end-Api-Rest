/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.service;

import com.example.demo.repository.Person;
import java.util.List;

/**
 *
 * @author gonza
 */
public interface IPersonService {
    public Person findPersonByEmail(String email);
    public List<Person> verPersonas();
    public void crearPersona (Person pers);
    public void borrarPersona (Long id);
    public Person buscarPersona (Long id);

    public List<Person> login();
    
}
