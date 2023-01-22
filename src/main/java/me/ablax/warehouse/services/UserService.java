package me.ablax.warehouse.services;

import me.ablax.warehouse.entities.UserEntity;
import me.ablax.warehouse.exceptions.AuthenticationException;
import me.ablax.warehouse.models.UserDto;
import me.ablax.warehouse.models.req.LoginReq;
import me.ablax.warehouse.models.req.RegisterReq;
import me.ablax.warehouse.models.req.ResetReq;
import me.ablax.warehouse.repositories.UserRepository;
import me.ablax.warehouse.utils.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final MailService mailService;

    public UserService(final UserRepository userRepository, final MailService mailService) {
        this.userRepository = userRepository;
        this.mailService = mailService;
    }

    public UserDto registerUser(final RegisterReq registerReq) {
        if (doesUserExist(registerReq)) {
            throw new AuthenticationException("User already exists!");
        }

        if (!Objects.equals(registerReq.getPassword(), registerReq.getConfirmPassword())) {
            throw new AuthenticationException("Password does not match!");
        }

        final UserEntity userEntity = new UserEntity();
        userEntity.setUsername(registerReq.getUsername());
        userEntity.setEmail(registerReq.getEmail());
        userEntity.setPhoneNumber(registerReq.getPhoneNumber());

        String generatedPassword = BCrypt.hashpw(registerReq.getPassword().trim(), BCrypt.gensalt(12));
        userEntity.setPassword(generatedPassword);

        return userRepository.save(userEntity).toDto();
    }

    public UserDto changeUserPass(final ResetReq resetReq) {
        if (!Objects.equals(resetReq.getPassword(), resetReq.getConfirmPassword())) {
            throw new AuthenticationException("Password does not match!");
        }

        final UserEntity userEntity = findById(resetReq.getId());

        String generatedPassword = BCrypt.hashpw(resetReq.getPassword().trim(), BCrypt.gensalt(12));
        userEntity.setPassword(generatedPassword);
        userEntity.setResetToken(null);

        return userRepository.save(userEntity).toDto();
    }

    public boolean doesUserExist(final RegisterReq registerReq) {
        return userRepository.findByEmailOrUsername(registerReq.getEmail(), registerReq.getUsername()) != null;
    }


    public UserEntity findById(final Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public UserDto loginUser(final LoginReq loginReq) {
        final UserEntity userEntity = userRepository.findByEmailOrUsername(loginReq.getUsername(), loginReq.getUsername());
        if (userEntity == null) {
            throw new AuthenticationException("Invalid credentials!");
        }

        final boolean checkpw = BCrypt.checkpw(loginReq.getPassword().trim(), userEntity.getPassword());
        if (!checkpw) {
            throw new AuthenticationException("Invalid credentials!");
        }
        return userEntity.toDto();
    }

    public void resetPassword(final String email) {
        final UserEntity user = userRepository.findByEmailOrUsername(email, email);
        if (user == null) {
            throw new AuthenticationException("User does not exist!");
        }
        if(user.getResetToken() == null){
            user.setResetToken(UUID.randomUUID().toString());
            userRepository.save(user);
        }else{
            mailService.sendEmail(user.getEmail(), user.getResetToken(), user.getUsername());
        }
    }

    public Optional<UserEntity> getUserByToken(final String token) {
        return userRepository.findByResetToken(token);
    }
}
