package licenta.controller;

import licenta.model.UserEntity;
import licenta.security.JWTAuthorizationFilter;
import licenta.service.UserDetailsService;
import licenta.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("auth")
@CrossOrigin(origins = "*")
public class UserController {
    @Resource
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public String registerUser(@RequestBody UserEntity user) {
        this.userService.addUser(user);
        return "Account created!\n";
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody UserEntity user) {
        return new ResponseEntity<>(userService.save(user), HttpStatus.OK);
    }

    @GetMapping("/get-user")
    @ResponseStatus(HttpStatus.OK)
    public UserEntity getUserById(@RequestHeader("Authorization") String token) {
        return this.userService.getUserById(JWTAuthorizationFilter.getUserIdFromJwt(token));
    }

    @GetMapping("/get-user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserEntity getUserById(@PathVariable Long id) {
        return this.userService.getUserById(Math.toIntExact(id));
    }

    @GetMapping
    public ResponseEntity<?> findAllUsers() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }
}
