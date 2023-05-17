package com.example.demo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{
	
	List<Person> findByName(@Param("name") String name);
	
}