package com.example.demo.repository;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class Person {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String password;
    private String email;
    private String biografia;
    
    
    public Person(){
        
    }
    public Person (Long id, String name, String password, String email, String biografia){
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.biografia = biografia;
    }
}
