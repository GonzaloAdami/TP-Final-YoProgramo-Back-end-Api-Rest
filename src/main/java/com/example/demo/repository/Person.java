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
    private String token;
    
    
    public Person(){
        
    }
    public Person (Long id, String name, String password, String email, String biografia, String token){
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.biografia = biografia;
        this.token = token;
    }
     public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
   public String getToken(){
       return token;
   }

}
