package com.example.demo.repository;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {

    @Test
    public void testGetId() {
        Person instance = new Person();
        assertNull(instance.getId());
    }

    @Test
    public void testSetId() {
        Long id = 1L;
        Person instance = new Person();
        instance.setId(id);
        assertEquals(id, instance.getId());
    }

    @Test
    public void testGetEmail() {
        Person instance = new Person();
        assertNull(instance.getEmail());
    }

    @Test
    public void testSetEmail() {
        String email = "test@example.com";
        Person instance = new Person();
        instance.setEmail(email);
        assertEquals(email, instance.getEmail());
    }

    @Test
    public void testGetPassword() {
        Person instance = new Person();
        assertNull(instance.getPassword());
    }

    @Test
    public void testSetPassword() {
        String password = "testpassword";
        Person instance = new Person();
        instance.setPassword(password);
        assertEquals(password, instance.getPassword());
    }

    @Test
    public void testGetName() {
        Person instance = new Person();
        assertNull(instance.getName());
    }

    @Test
    public void testSetName() {
        String name = "John Doe";
        Person instance = new Person();
        instance.setName(name);
        assertEquals(name, instance.getName());
    }
}
