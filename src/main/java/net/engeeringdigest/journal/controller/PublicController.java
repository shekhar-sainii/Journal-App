package net.engeeringdigest.journal.controller;

import net.engeeringdigest.journal.entity.User;
import net.engeeringdigest.journal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("public")
public class PublicController {


    @Autowired
    private UserService userService;

    @GetMapping
    public String healthCheck(){
        return "Ok";
    }

    @PostMapping("create-user")
    public ResponseEntity<User> createUser(@RequestBody User user){
        try {
            userService.saveNewUser(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
}
