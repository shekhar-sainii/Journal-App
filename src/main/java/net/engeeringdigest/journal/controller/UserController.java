package net.engeeringdigest.journal.controller;

import net.engeeringdigest.journal.entity.User;
import net.engeeringdigest.journal.repository.UserRepository;
import net.engeeringdigest.journal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.fasterxml.jackson.databind.ser.std.StdJdkSerializers.all;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> all = userService.getAll();
        if(all != null && !all().isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



//    @GetMapping("/id/{myId}")
//    public ResponseEntity<User> getJournalEntryById(@PathVariable ObjectId myId){
//        Optional<User> journalEntry = userService.findById(myId);
//        if(journalEntry.isPresent()){
//            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }



    @PutMapping
    public ResponseEntity<User> updateJournalEntryById(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDb = userService.findByUserName(userName);
        if(userInDb != null){
            userInDb.setUserName(user.getUserName());
            userInDb.setPassword(user.getPassword());
            userService.saveNewUser(userInDb);
            return new ResponseEntity<>(userInDb, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

        @DeleteMapping
    public ResponseEntity<?> deleteJournalEntryById(){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            userRepository.deleteByUserName(userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
