package net.vedant.journalApp.controller;

import net.vedant.journalApp.entity.User;
import net.vedant.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            userService.createEntry(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        List<User> all = userService.getAll();

        if(all != null && !all.isEmpty()) {
            return ResponseEntity.ok(all);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/userName/{userName}")
    public ResponseEntity<User> findByUserName(@PathVariable String userName) {
        Optional<User> userById = userService.getUserByUserName(userName);
        if (userById.isPresent()) {
            return new ResponseEntity<>(userById.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/userName/{userName}")
    public ResponseEntity<User> deleteUserById(@PathVariable String userName) {
        userService.deleteUserByUserName(userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/userName/{userName}")
    public ResponseEntity<User> updateUser(@PathVariable String userName, @RequestBody User user) {
        User user1 = userService.updateUser(userName, user);
        if (user1 == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user1);
    }
}
