package hu.elte.eventcenter.controller;

import hu.elte.eventcenter.model.Location;
import hu.elte.eventcenter.model.Participation;
import hu.elte.eventcenter.model.User;
import hu.elte.eventcenter.model.Event;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/participations")
public class ParticipationController {

    @Autowired
    private ParticipationRepository participationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @GetMapping("")
    public ResponseEntity<Iterable<Participation>> getEvents() { //@RequestParam(required = false) String location) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<String> roles = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        if (roles.contains("ROLE_ADMIN")) {
            return ResponseEntity.ok(participationRepository.findAll());
        }

        String username = auth.getName();
        Optional<User> user = userRepository.findByUsername(username);
        Iterable<Participation> participations;
        participations = participationRepository.findAll();
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get().getParticipation());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("")
    public ResponseEntity<Participation> participate(@RequestBody Participation participation) {

        Optional<Event> optionalEvent = eventRepository.findById(participation.getEvent().getId());
        if (optionalEvent.isEmpty()) {
            return ResponseEntity.badRequest().build();
        } else {

            participation.setEvent(optionalEvent.get());
        }

        participation.setApproval(Participation.Approval.APPLIED);
        participation.setUser(authUser());
        participationRepository.save(participation);
        return ResponseEntity.ok(participation);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Participation> modifyApproval(@RequestBody Participation.Approval approval, @PathVariable Integer id){
        Optional<Participation> optionalParticipation = participationRepository.findById(id);
        if (optionalParticipation.isPresent()) {
            Participation editedParticipation = optionalParticipation.get();
            editedParticipation.setApproval(approval);
            participationRepository.save(editedParticipation);
            return ResponseEntity.ok(editedParticipation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //itt maradtam
    @Secured({ "ROLE_ADMIN" })
    @DeleteMapping("/{id}")
    public ResponseEntity deleteParicipation(@PathVariable Integer id) {
        Optional<Participation> optionalParticipation = participationRepository.findById(id);
        if (optionalParticipation.isPresent()) {
            participationRepository.deleteById(id);
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
