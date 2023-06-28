package com.example.demo.controller;


import com.example.demo.repository.Person;
import com.example.demo.service.IPersonService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

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
import org.springframework.web.bind.annotation.RequestMapping;

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
    private byte[] TOKEN_SECRET;
    private long TOKEN_EXPIRATION_TIME;
    

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
public ResponseEntity<String> login(@RequestBody Person person) {
    String email = person.getEmail();
    String password = person.getPassword();

    // Realiza la verificación de autenticación en la base de datos
    boolean isAuthenticated = verifyAuthentication(email, password);

    if (isAuthenticated) {
        // Autenticación exitosa
        String token = generateToken(email); // Genera un token de autenticación

        // Puedes devolver el token en la respuesta para que el cliente lo utilice en las solicitudes posteriores
        return ResponseEntity.ok(token);
    } else {
        // Autenticación fallida
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
    }
}

private boolean verifyAuthentication(String email, String password) {
    // Implementa la lógica para verificar la autenticación en la base de datos
    // Puedes utilizar el servicio IPersonService para acceder a los datos de la base de datos

    // Ejemplo de implementación básica utilizando el servicio IPersonService
    Person person = persoServ.findPersonByEmail(email);
        return person != null && person.getPassword().equals(password);
}

private String generateToken(String email) {
    // Implementa la generación de un token de autenticación único para el usuario
    // Puedes utilizar bibliotecas como JWT (JSON Web Tokens) para generar y manejar los tokens de forma segura

    // Ejemplo de generación de token utilizando la biblioteca jjwt
    String token;
        token = Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, TOKEN_SECRET)
                .compact();

    return token;
}

}
