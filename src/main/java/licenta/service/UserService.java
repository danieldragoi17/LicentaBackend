package licenta.service;

import licenta.model.UserEntity;
import licenta.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class UserService{
    @Resource
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    public UserEntity save(UserEntity user) {
        return userRepository.save(user);
    }

    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> findById(Long id){
        return userRepository.findById(id);
    }

    public void addUser(UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void delete(UserEntity user) {
        userRepository.delete(user);
    }

    public UserEntity getUserById(Integer userId) {
        System.out.println("Cauta user dupa id");
        UserEntity user =  userRepository
                .findById(Long.valueOf(userId))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No client found with the entered id!"
                ));

        user.setPassword("");
        System.out.println("User gasit!" + user.getEmail());
        return user;
    }
}
