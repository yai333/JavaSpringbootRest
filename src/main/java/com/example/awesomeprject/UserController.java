package com.example.awesomeprject;

import java.util.logging.Logger;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
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
    public String addUser(@RequestBody User user) {
        if (userRepository.existsByFirstNameAndLastName(user.getFirstName(), user.getLastName())) {
            return "Author or Author ID Already Exists";
        } else {
            userRepository.save(user);
            return "Created";
        }
    }

}
