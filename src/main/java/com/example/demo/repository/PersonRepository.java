/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.demo.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author gonza
 */
@Transactional
@Repository
public interface PersonRepository extends JpaRepository <Person, Long>{

    Person findByEmail(String email);

    Person findByPassword(String password);

    Person findPersonByEmail(String email);
    
  @Modifying
    @Query("UPDATE Person SET perfil = :photo WHERE id = :personId")
    int uploadPhotoProfile(@Param("personId") Long id, @Param("photo") byte[] perfil);

    
    @Modifying
    @Query("UPDATE Person SET portada = :photo WHERE id = :personId")
    int uploadNewPhotoBanner(@Param("personId") Long id, @Param("photo") byte[] portada);
    
    @Query("SELECT perfil FROM Person WHERE id = :personId")
   public byte[] FindIdBySQLProfileBLOB(@Param("personId") Long id);

    public Person findPersonByPassword(String password);
}
