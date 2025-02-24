package net.engeeringdigest.journal.service;

import net.engeeringdigest.journal.entity.User;
import net.engeeringdigest.journal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        net.engeeringdigest.journal.entity.User user = userRepository.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        System.out.println("Stored Password: " + user.getPassword());
        System.out.println("Matches?: " + passwordEncoder.matches("ram", user.getPassword()));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUserName())
                .password(user.getPassword())
                .roles(user.getRoles().toArray(new String[0]))
                .build();
    }

}
