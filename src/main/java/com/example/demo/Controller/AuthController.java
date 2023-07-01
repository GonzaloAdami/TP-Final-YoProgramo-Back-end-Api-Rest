package com.example.demo.controller;


import com.example.demo.LoginResponse.LoginResponse;
import com.example.demo.repository.Person;
import com.example.demo.service.IPersonService;
import java.sql.Blob;
import java.sql.SQLException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    
    @GetMapping("/buscar/persona/{id}")
    public Person buscarPersona(@PathVariable Long id) {
     return persoServ.FindPersonById(id);
    } 
    @DeleteMapping("/delete/{id}")
    public void borrarPersona (@PathVariable Long id){
        persoServ.borrarPersona(id);
    }
    @PutMapping("/upload/banner/{id}")
    public int uploadBanner(@PathVariable Long id, @RequestBody byte[] portada) {
    return persoServ.uploadNewPhotoBanner(id, portada);
        
        
    }



@PutMapping("/upload/profile/{id}")
public ResponseEntity<String> uploadProfile(@PathVariable Long id, @RequestBody byte[] perfil) {
    int updatedCount = persoServ.uploadPhotoProfile(id, perfil);
    if (updatedCount > 0) {
        return ResponseEntity.ok("Perfil actualizado correctamente");
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la persona con el ID especificado");
    }
}




    /**
     *
     * @param pers
     * @return
     */
    @PostMapping("/login")
public ResponseEntity<LoginResponse> login(@RequestBody Person pers) {
    String email = pers.getEmail();
    String password = pers.getPassword();
    ResponseEntity<String> token = persoServ.login(email, password);
    Long id = persoServ.FindIdBySQL(email, password);

    if (token != null && id != null) {
        LoginResponse response = new LoginResponse(token.getBody(), id);
        return ResponseEntity.ok(response);
    } else {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
   
@GetMapping("/see/PhotoProfile/{id}")
public ResponseEntity<byte[]> getProfileBlob(@PathVariable Long id) {
    byte[] profileData = persoServ.FindIdBySQLProfileBLOB(id);
    if (profileData != null) {
        try {
            Blob blob = new javax.sql.rowset.serial.SerialBlob(profileData);
            int blobLength = (int) blob.length();
            String mimeType = "image/jpeg"; // Establece el tipo de contenido adecuado según el tipo de imagen

            // Lee los bytes del blob en un arreglo de bytes
            byte[] blobBytes = blob.getBytes(1, blobLength);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(mimeType));
            headers.setContentLength(blobLength);

            // Devuelve los bytes del blob junto con los encabezados en una respuesta HTTP
            return new ResponseEntity<>(blobBytes, headers, HttpStatus.OK);
        } catch (SQLException e) {
            // Manejar la excepción de SQLException
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    } else {
        // Devolver un mensaje indicando que no se encontraron datos
        String errorMessage = "No se encontraron datos";
        byte[] errorBytes = errorMessage.getBytes();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setContentLength(errorBytes.length);
        return new ResponseEntity<>(errorBytes, headers, HttpStatus.NOT_FOUND);
    }
}


}