/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.service;

import com.example.demo.repository.Person;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author gonza
 */
public interface IPersonService {
    public Person findPersonByEmail(String email);
    public Person findPersonByPassword(String password);
    public List<Person> verPersonas();
    public void crearPersona (Person pers);
    public void borrarPersona (Long id);
    public Person FindPersonById (Long id);
    public Long FindIdBySQL (String email, String password);
    @Modifying
    @Query("UPDATE Person SET perfil = :photo WHERE id = :personId")
    int uploadPhotoProfile(@Param("personId") Long id, @Param("photo") byte[] perfil);

    @Modifying
     @Query("UPDATE Person SET portada = :photo WHERE id = :personId")
    int uploadNewPhotoBanner(@Param("personId") Long id, @Param("photo") byte[] portada);
   
    @Query("SELECT perfil FROM Person WHERE id = :personId")
   public byte[] FindIdBySQLProfileBLOB(@Param("personId") Long id);

    /**
     *
     * @param password
     * @param email
     * @return
     */
    public ResponseEntity<String> login(String password, String email);

   
}
