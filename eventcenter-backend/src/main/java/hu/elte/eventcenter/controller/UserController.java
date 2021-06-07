package hu.elte.eventcenter.controller;

import hu.elte.eventcenter.model.Event;
import hu.elte.eventcenter.model.Participation;
import hu.elte.eventcenter.model.User;
import hu.elte.eventcenter.repository.EventRepository;
import hu.elte.eventcenter.repository.ParticipationRepository;
import hu.elte.eventcenter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;
    private EventRepository eventRepository;
    private ParticipationRepository participationRepository;

    @Secured({ "ROLE_ADMIN" })
    @GetMapping("")
    public ResponseEntity<Iterable<User>> getUsers() { //@RequestParam(required = false) String location) {
            return ResponseEntity.ok(userRepository.findAll());
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(User.UserRole.ROLE_USER);
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("")
    public ResponseEntity modifyUser(@RequestBody User user) {
        Integer id = authUser().getId();

        Optional<User> optionalUser = userRepository.findById(id);

        if (!optionalUser.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        User userToModify = optionalUser.get();

        if (user.getName() != null) {
            userToModify.setName(user.getName());
        }

        if (user.getUsername() != null) {
            userToModify.setUsername(user.getUsername());
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

    @DeleteMapping("")
    public ResponseEntity deleteUser() {
        Optional<User> optionalUser = userRepository.findById(authUser().getId());
        if (optionalUser.isPresent()) {
            userRepository.deleteById(optionalUser.get().getId());
            return ResponseEntity.ok().build();

        } else {

            return ResponseEntity.notFound().build();

        }
    }

    public User authUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Optional<User> optionalUser = userRepository.findByUsername(username);
        User user = optionalUser.get();
        return user;
    }

    public List<String> authUserRoles() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<String> roles = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return roles;
    }
}

//OK