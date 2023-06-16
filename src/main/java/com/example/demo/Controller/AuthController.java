import com.example.demo.repository.Person;
import com.example.demo.repository.PersonRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/user")
public class AuthController {
    private final PersonRepository personRepository;

    public AuthController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
    
    @CrossOrigin(origins = {"http://localhost:4200", "https://yoprogramo-frontend-2d963.firebaseapp.com", "https://yoprogramo-frontend-2d963.firebaseapp.com/login"})
    @PostMapping("/login")
public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
    if (loginRequest.getEmail().equals("test@gmail.com") && loginRequest.getPassword().equals("yoprogramo2023")) {
        String token = generateAccessToken(loginRequest.getEmail());
        String username = obtenerNombreDeUsuario(loginRequest.getEmail());
        AuthResponse response = new AuthResponse(token, username);
        return ResponseEntity.ok(response);
    } else {
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
    Person person = personRepository.findByEmail(email);
    if (person != null) {
        return person.getName();
    } else {
        return null; // O maneja el caso en el que no se encuentre el usuario de alguna manera apropiada
    }
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
