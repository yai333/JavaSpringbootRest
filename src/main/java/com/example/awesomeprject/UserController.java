package com.example.awesomeprject;

import java.util.logging.Logger;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    public static Logger logger = Logger.getLogger("global");

    private final UserRepository userRepository;

    UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    Map home() {
		return Collections.singletonMap("response","Hello Docker World");
	}

    @GetMapping("/users/all")
    List<User> findAll() {
        return userRepository.findAll();
    }

    @GetMapping("/users/filter")
    List<User> findByLastName(@RequestParam(value = "lastName", required = true) String lastName) {
        logger.info(lastName);
        return userRepository.findByLastName(lastName);
    }

    @PostMapping(value = "user", consumes = "application/json", produces = "application/json")
    Map addUser(@RequestBody User user) {
        if (userRepository.existsByFirstNameAndLastName(user.getFirstName(), user.getLastName())) {
            return Collections.singletonMap("response", "Author or Author ID Already Exists");
            
        } else {
            userRepository.save(user);
            return Collections.singletonMap("response", "User Created");
        }
    }

}
