package net.vedant.journalApp.service;

import net.vedant.journalApp.entity.User;
import net.vedant.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void createEntry(User user) {
        user.setDate(LocalDateTime.now());
        userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> getUserByUserName(String userName) {
        return Optional.ofNullable(userRepository.findByUserName(userName));
    }

    public void deleteUserByUserName(String userName) {
        userRepository.deleteByUserName(userName);
    }

    public User updateUser(String userName, User user) {
        User existing = userRepository.findByUserName(userName);

        if (existing == null) {
            return null;
        }

        existing.setUserName(user.getUserName());
        existing.setPassword(user.getPassword());
        existing.setDate(LocalDateTime.now());

        return userRepository.save(existing);
    }


}
