package hu.elte.eventcenter.controller;

import hu.elte.eventcenter.model.Event;
import hu.elte.eventcenter.model.User;
import hu.elte.eventcenter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(User.UserRole.ROLE_USER);
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity modifyUser(@RequestBody User user, @PathVariable Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        User userToModify = optionalUser.get();
        if (user.getName() != null) {
            userToModify.setName(user.getName());
        }

        if (user.getPassword() != null) {
            userToModify.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        if (user.getRole() != null) {
            userToModify.setRole(user.getRole());
        }

        User savedUser = userRepository.save(userToModify);
        return ResponseEntity.ok(savedUser);
    }

    @DeleteMapping("/{id}")
    @Secured({ "ROLE_ADMIN" })
    public ResponseEntity delete(@PathVariable Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            userRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}