package com.example.demo.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "https://yoprogramo-frontend-2d963.firebaseapp.com"})
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // Realiza la lógica de autenticación aquí
        // Verifica las credenciales y genera un token de acceso si son válidas

        // Ejemplo de verificación de credenciales
        if (loginRequest.getEmail().equals("test@gmail.com") && loginRequest.getPassword().equals("yoprogramo2023")) {
            // Si la autenticación es exitosa, genera un token de acceso
            String token = generateAccessToken(loginRequest.getEmail());

            String username = obtenerNombreDeUsuario(loginRequest.getEmail());

            AuthResponse response = new AuthResponse(token, username);
            return ResponseEntity.ok(response);
        } else {
            // Si la autenticación falla, devuelve una respuesta de error
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }

    private String generateAccessToken(String email) {
        String secretKey = "secretKey";
        String token = Jwts.builder()
                .setSubject(email)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        return token;
    }

    private String obtenerNombreDeUsuario(String email) {
        // Aquí debes implementar la lógica para obtener el nombre de usuario basado en el correo electrónico
        // Puede ser una consulta a la base de datos u otra fuente de datos
        return "Nombre del usuario"; // Reemplaza con la lógica real
    }

    static class LoginRequest {
        private String email;
        private String password;

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
    }

    static class AuthResponse {
        private String token;
        private String name;

        public AuthResponse(String token, String name) {
            this.token = token;
            this.name = name;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
