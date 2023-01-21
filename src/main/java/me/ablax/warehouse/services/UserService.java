package me.ablax.warehouse.services;

import me.ablax.warehouse.entities.User;
import me.ablax.warehouse.exceptions.AuthenticationException;
import me.ablax.warehouse.models.req.LoginReq;
import me.ablax.warehouse.models.req.RegisterReq;
import me.ablax.warehouse.models.UserDto;
import me.ablax.warehouse.repositories.UserRepository;
import me.ablax.warehouse.utils.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto registerUser(final RegisterReq registerReq) {
        if (doesUserExist(registerReq)) {
            throw new AuthenticationException("User already exists!");
        }

        if(!Objects.equals(registerReq.getPassword(), registerReq.getConfirmPassword())){
            throw new AuthenticationException("Password does not match!");
        }

        final User user = new User();
        user.setUsername(registerReq.getUsername());
        user.setEmail(registerReq.getEmail());
        user.setPhoneNumber(registerReq.getPhoneNumber());

        String generatedPassword = BCrypt.hashpw(registerReq.getPassword().trim(), BCrypt.gensalt(12));
        user.setPassword(generatedPassword);

        return userRepository.save(user).toDto();
    }

    public boolean doesUserExist(final RegisterReq registerReq) {
        return userRepository.findByEmailOrUsername(registerReq.getEmail(), registerReq.getUsername()) != null;
    }


    public User findById(final Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public UserDto loginUser(final LoginReq loginReq) {
        final User user = userRepository.findByEmailOrUsername(loginReq.getUsername(), loginReq.getUsername());
        if (user == null) {
            throw new AuthenticationException("Invalid credentials!");
        }

        final boolean checkpw = BCrypt.checkpw(loginReq.getPassword().trim(), user.getPassword());
        if (!checkpw) {
            throw new AuthenticationException("Invalid credentials!");
        }
        return user.toDto();
    }
}
