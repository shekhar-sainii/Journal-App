package net.engeeringdigest.journal.controller;

import net.engeeringdigest.journal.entity.User;
import net.engeeringdigest.journal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.fasterxml.jackson.databind.ser.std.StdJdkSerializers.all;

@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("all-users")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> all = userService.getAll();
        if(all != null && !all().isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("create-admin-user")
    public void crateUser(@RequestBody User user){
        userService.saveAdmin(user);
    }
}
