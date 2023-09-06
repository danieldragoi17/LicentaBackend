package licenta.service;

import licenta.model.UserEntity;
import licenta.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AdminService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    public AdminService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveAccount(UserEntity user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void updateAccount(UserEntity user, UserEntity updatedUser){
        user.setFirstname(updatedUser.getFirstname());
        user.setLastname(updatedUser.getLastname());
        user.setCnp(updatedUser.getCnp());
        user.setRole(updatedUser.getRole());
        user.setEmail(updatedUser.getEmail());
        user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        userRepository.save(user);
    }
}
