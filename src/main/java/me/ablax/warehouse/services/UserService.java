package me.ablax.warehouse.services;

import me.ablax.warehouse.entities.User;
import me.ablax.warehouse.exceptions.AuthenticationException;
import me.ablax.warehouse.models.UserDto;
import me.ablax.warehouse.repositories.UserRepository;
import me.ablax.warehouse.utils.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto registerUser(final String username, final String password, final String email, final String phoneNumber){
        if (doesUserExist(username, email)) {
            throw new AuthenticationException("User already exists!");
        }

        final User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);

        String generatedPassword = BCrypt.hashpw(password.trim(), BCrypt.gensalt(12));
        user.setPassword(generatedPassword);

        return userRepository.save(user).toDto();
    }

    public boolean doesUserExist(final String username, final String email) {
        return userRepository.findByEmailOrUsername(username, username) != null || userRepository.findByEmailOrUsername(email, email) != null;
    }


    public User findById(final Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public UserDto loginUser(final String username, final String password) {
        final User user = userRepository.findByEmailOrUsername(username, username);
        if (user == null) {
            throw new AuthenticationException("Invalid credentials!");
        }

        final boolean checkpw = BCrypt.checkpw(password.trim(), user.getPassword());
        if (!checkpw) {
            throw new AuthenticationException("Invalid credentials!");
        }
        return user.toDto();
    }

}
