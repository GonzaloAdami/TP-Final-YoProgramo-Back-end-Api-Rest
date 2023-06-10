package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
public class AuthController {

    @CrossOrigin(origins = {"http://localhost:4200", "https://yoprogramo-frontend-2d963.firebaseapp.com"})
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // Realiza la lógica de autenticación aquí
        // Verifica las credenciales y genera un token de acceso si son válidas

        // Ejemplo de verificación de credenciales
        if (loginRequest.getEmail().equals("usuario@example.com") && loginRequest.getPassword().equals("password123")) {
            // Si la autenticación es exitosa, devuelve un token de acceso en la respuesta
            String token = generateAccessToken(loginRequest.getEmail());
            return ResponseEntity.ok(new AuthResponse(token));
        } else {
            // Si la autenticación falla, devuelve una respuesta de error
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }

    private String generateAccessToken(String email) {
        // Lógica para generar un token de acceso (por ejemplo, utilizando JWT)
        // ...

        // Lógica para generar un token de acceso (por ejemplo, utilizando JWT)
        // ...
        return "dummy-token";
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

        public AuthResponse(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
