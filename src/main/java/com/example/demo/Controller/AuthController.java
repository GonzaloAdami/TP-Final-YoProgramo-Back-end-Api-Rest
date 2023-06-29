package com.example.demo.controller;


import com.example.demo.repository.Person;
import com.example.demo.service.IPersonService;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = {
        "https://yoprogramo-frontend-2d963.firebaseapp.com",
        "https://yoprogramo-frontend-2d963.web.app",
        "http://localhost:4200",
        "http://localhost:8080" 
})


public class AuthController {

    @Autowired
    private IPersonService persoServ;
   
    @PostMapping("/new/persona")
        public void agregarPersonas(@RequestBody Person pers){
        persoServ.crearPersona(pers);
    }
       @GetMapping("/status") 
    public String status(){
       return "El servidor funciona correctamente";
    } 
    @GetMapping("/ver/personas") 
    public List<Person> verPersonas(){
        return persoServ.verPersonas();
    } 
    
    @DeleteMapping("/delete/{id}")
    public void borrarPersona (@PathVariable Long id){
        persoServ.borrarPersona(id);
    }
  @PostMapping("/login")
public ResponseEntity<ResponseEntity<String>> login(@RequestBody Person pers) {
    String email = pers.getEmail();
    String password = pers.getPassword();
        ResponseEntity<String> token = persoServ.login(email, password);

    if (token != null) {
        return ResponseEntity.ok(token);
    } else {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
}