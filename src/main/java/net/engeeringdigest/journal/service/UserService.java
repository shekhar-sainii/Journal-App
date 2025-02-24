package net.engeeringdigest.journal.service;

import lombok.extern.slf4j.Slf4j;
import net.engeeringdigest.journal.entity.User;
import net.engeeringdigest.journal.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncode = new BCryptPasswordEncoder();

    public void saveNewUser(User user){
        try {
            user.setPassword(passwordEncode.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
        } catch (Exception e) {
            log.error("Exception ",e);
        }
    }

    public void saveAdmin(User user){
        try {
            user.setPassword(passwordEncode.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER","ADMIN"));
            userRepository.save(user);
        } catch (Exception e) {
            log.error("Exception ",e);
        }
    }
    public void saveUser(User user){
        try {
            userRepository.save(user);
        } catch (Exception e) {
            log.error("Exception ",e);
        }
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public Optional<User> findById(ObjectId id){
        return userRepository.findById(id);
    }

    public void deleteById(ObjectId id){
        userRepository.deleteById(id);
    }

    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }
}
