package com.example.service;

import com.example.model.User;
import com.example.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

//@Repository
//public interface UserRepository extends JpaRepository<User, Long> {
//    Optional<User> findByUsername(String username);
//}
@Service
public class UserService {

//    @Autowired
    private final UserRepository userRepository;

//    public User registerUser(User user) {
//        // Encrypt the password using BCrypt and save user
//        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
//        return userRepository.save(user);
//    }
@Autowired
public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
}

    @Transactional
    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return user;
    }

}

