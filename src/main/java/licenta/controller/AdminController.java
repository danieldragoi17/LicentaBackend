package licenta.controller;

import licenta.model.UserEntity;
import licenta.service.AdminService;
import licenta.service.EmailService;
import licenta.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Resource
    EmailService emailService;

    private final AdminService adminService;
    private final UserService userService;

    public AdminController(AdminService adminService, UserService userService) {
        this.adminService = adminService;
        this.userService = userService;
    }

    @GetMapping("/get-accounts")
    public ResponseEntity<?> findAllUsers() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/save-account")
    public ResponseEntity<?> saveAccount(@RequestBody UserEntity user) {
        try{
            System.out.println("Save account, email: " + user.getEmail());
            adminService.saveAccount(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Eroare la datele pentru user!");
        }
    }

    @PutMapping("/update-account/{id}")
    public ResponseEntity<?> updateAccount(@PathVariable Long id, @RequestBody UserEntity updatedUser) {
        System.out.println("Update account, id: " + id);
        Optional<UserEntity> userFound = userService.findById(id);
        if (userFound.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        UserEntity user = userFound.get();
        adminService.updateAccount(user, updatedUser);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        System.out.println("Delete account, id: " + id);
        Optional<UserEntity> userOptional = userService.findById(id);
        if (userOptional.isPresent()) {
            userService.delete(userOptional.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
