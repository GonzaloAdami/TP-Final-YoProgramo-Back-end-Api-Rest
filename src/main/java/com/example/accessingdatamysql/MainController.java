package com.example.accessingdatamysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/demo")
@CrossOrigin(origins = {
  "yoprogramo-frontend-2d963.firebaseapp.com",
  "yoprogramo-frontend-2d963.firebaseapp.com"
})
public class MainController {

  @Autowired
  private UserRepository userRepository;

  @GetMapping(path = "/all")
  public Iterable<User> getAllUsers() {
    return userRepository.findAll();
  }

  @PostMapping(path="/add")
  public @ResponseBody String addNewUser (@RequestParam String name, @RequestParam String email) {
    User n = new User();
    n.setName(name);
    n.setEmail(email);
    userRepository.save(n);
    return "Saved";
  }
}
