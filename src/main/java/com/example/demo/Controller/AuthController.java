package com.example.demo.controller;


import com.example.demo.LoginResponse.LoginResponse;
import com.example.demo.repository.Person;
import com.example.demo.service.IPersonService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
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
    @org.springframework.data.jpa.repository.Query("UPDATE Person SET perfil = :photo WHERE id = :personId")
    public int uploadProfile(@PathVariable Long id,@RequestBody byte[] perfil) {
           return persoServ.uploadPhotoProfile(id, perfil);
        

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
public byte[] getProfileBlob(@PathVariable Long id){
    return persoServ.FindIdBySQLProfileBLOB(id);
}
}