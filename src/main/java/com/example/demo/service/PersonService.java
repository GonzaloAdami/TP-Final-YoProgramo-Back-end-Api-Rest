/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.service;

import com.example.demo.repository.Person;
import com.example.demo.repository.PersonRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
/**
 *
 * @author gonza
 */
@Service
public class PersonService implements IPersonService{

    @Autowired  
    public PersonRepository persoRepo;
    @PersistenceContext
    private EntityManager entityManager;
    
    
    @Override
    public List<Person> verPersonas() {
    return persoRepo.findAll();
    }

    @Override
    public void crearPersona(Person pers) {
        persoRepo.save(pers);
    }
    @Override
    public void borrarPersona(Long id) {
        persoRepo.deleteById(id);
    }

    @Override
    public Person FindPersonById(Long id) {    
     return persoRepo.findById(id).orElse(null);
    } 

    @Override
    public Person findPersonByEmail(String email) {
        return persoRepo.findByEmail(email);
    }

    /**
     *
     * @param email
     * @param password
     * @return
     */
    @Override   
    public ResponseEntity<String> login(String email, String password) {
    String query = "SELECT COUNT(*) FROM Person Pers WHERE Pers.email = :email AND Pers.password = :password";
   
    Query queryObj = entityManager.createQuery(query);
    queryObj.setParameter("email", email);
    queryObj.setParameter("password", password);
    boolean result = (Long) queryObj.getSingleResult() > 0;
    
  
    

    if (result) {
        // Generar el token
        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        Date expirationDate = new Date(System.currentTimeMillis() + 86400000); // 24 horas
        String token = Jwts.builder()
                .setSubject(email)
                .setExpiration(expirationDate)
                .signWith(secretKey)
                .compact();
        
        return ResponseEntity.ok(token);
    } else {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}



    @Override
    public Person findPersonByPassword(String password) {
      return persoRepo.findPersonByPassword(password);
        
    }

    @Override
    public Long FindIdBySQL(String email, String password) {
    String query = "SELECT Pers.id FROM Person Pers WHERE Pers.email = :email AND Pers.password = :password";

    Query queryObjID = entityManager.createQuery(query);
    queryObjID.setParameter("email", email);
    queryObjID.setParameter("password", password);

    List<?> result = queryObjID.getResultList();
    if (!result.isEmpty()) {
        return (Long) result.get(0);
    } else {
        return null; // No se encontró ningún ID correspondiente al correo y contraseña proporcionados
    }
}

      
    @Override
    @Modifying
    @Transactional
   public int uploadPhotoProfile(Long id, byte[] perfil) {
    String query = "UPDATE Person SET perfil = :photo WHERE id = :personId";
    Query queryObjPhoto = entityManager.createQuery(query);
    queryObjPhoto.setParameter("photo", perfil);
    queryObjPhoto.setParameter("personId", id);
    
    int updatedCount = queryObjPhoto.executeUpdate();
        return updatedCount;
    

   }
    /**
     *
     * @param id
     * @param portada
     * @return
     */
    @Override
    @Modifying
    @Transactional
   public int uploadNewPhotoBanner(Long id, byte[] portada) {
    String query = "UPDATE Person SET portada = :photo WHERE id = :personId";
    Query queryObjPhoto = entityManager.createQuery(query);
    queryObjPhoto.setParameter("photo", portada);
    queryObjPhoto.setParameter("personId", id);
    
    int updatedCount = queryObjPhoto.executeUpdate();
        return updatedCount;
    

   }
 @Override
public byte[] FindIdBySQLProfileBLOB(Long id) {
    String query = "SELECT perfil FROM Person WHERE id = :personId";

    Query queryObjID = entityManager.createQuery(query);
    queryObjID.setParameter("personId", id);

    byte[] blobData = null;
    try {
        Object result = queryObjID.getSingleResult();
        if (result instanceof byte[] bs) {
            blobData = bs;
        } else if (result instanceof Blob blob) {
            try {
                int blobLength = (int) blob.length();
                blobData = blob.getBytes(1, blobLength);
            } catch (SQLException ex) {
                // Manejar la excepción de SQLException específica
            } finally {
                try {
                    blob.free();
                } catch (SQLException ex) {
                    Logger.getLogger(PersonService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    } catch (NoResultException e) {
        // Manejo de errores cuando no se encuentra ningún resultado
    } catch (PersistenceException e) {
        // Manejo de errores de persistencia
    }

    return blobData;
}
}
    



