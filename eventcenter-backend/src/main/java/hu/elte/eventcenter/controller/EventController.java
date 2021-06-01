package hu.elte.eventcenter.controller;

import hu.elte.eventcenter.model.Event;
import hu.elte.eventcenter.model.Location;
import hu.elte.eventcenter.model.Participation;
import hu.elte.eventcenter.model.User;
import hu.elte.eventcenter.repository.EventRepository;
import hu.elte.eventcenter.repository.LocationRepository;
import hu.elte.eventcenter.repository.ParticipationRepository;
import hu.elte.eventcenter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/events")
public class EventController {
    private EventRepository eventRepository;
    private LocationRepository locationRepository;
    private ParticipationRepository participationRepository;
    private UserRepository userRepository;

    public EventController (
            @Autowired EventRepository eventRepository,
            @Autowired LocationRepository locationRepository,
            @Autowired ParticipationRepository participationRepository,
            @Autowired UserRepository userRepository ) {
        this.eventRepository = eventRepository;
        this.locationRepository = locationRepository;
        this.participationRepository = participationRepository;
        this.userRepository = userRepository;
    }


    //functioning as expected
    @GetMapping("")
    public ResponseEntity<Iterable<Event>> getEvents() { //@RequestParam(required = false) String location) {

        if (authUserRoles().contains("ROLE_ADMIN")) {

            return ResponseEntity.ok(eventRepository.findAll());

        }
        return ResponseEntity.ok(authUser().getEvents());
    }

    //functioning as expected
    @GetMapping("/{eventId}")
    public ResponseEntity<Event> getEventById(@PathVariable Integer eventId) {

        Optional<Event> optionalEvent = eventRepository.findById(eventId);

        if (! optionalEvent.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        if (!authUserRoles().contains("ROLE_ADMIN")){
            if (!authUser().getEvents().contains(optionalEvent.get())) {
                return ResponseEntity.notFound().build();
            }
        }


        return ResponseEntity.ok(optionalEvent.get());
    }

    //functioning as expected
    @PostMapping("")
    public ResponseEntity<Event> postEvent(@RequestBody Event event) {

        event.setStatus(Event.Status.ACTIVE);

        List<Location> locations = new ArrayList<>();
        for (Location location : event.getLocations()) {
            if (location.getId() == null) {
                return ResponseEntity.badRequest().build();
            }

            Optional<Location> optionalLocation = locationRepository.findById(location.getId());

            if (optionalLocation.isEmpty()) {
                return ResponseEntity.badRequest().build();
            } else {
                locations.add(optionalLocation.get());
            }
        }

        //event.setLocations(locations);
        event.setOrganizer(authUser());
        Event savedEvent = eventRepository.save(event);

        return ResponseEntity.ok(savedEvent);
    }

    //functioning as expected
    @PatchMapping("/{id}")
    public ResponseEntity<Event> pachEvent(@RequestBody Event event, @PathVariable Integer id) {

        Optional<Event> optionalEvent = eventRepository.findById(id);

        if (!optionalEvent.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        if (!authUserRoles().contains("ROLE_ADMIN")){
            if (!authUser().getEvents().contains(optionalEvent.get())) {
                return ResponseEntity.notFound().build();
            }
        }

        Event eventToModify = optionalEvent.get();
        if (event.getStatus() != null) {
            eventToModify.setStatus(event.getStatus());
        }
        if (event.getTitle() != null) {
            eventToModify.setTitle(event.getTitle());
        }
        if (event.getDescription() != null) {
            eventToModify.setDescription(event.getDescription());
        }
        if (event.getStartAt() != null) {
            eventToModify.setStartAt(event.getStartAt());
        }

        if (event.getLocations() != null) {
            List<Location> locations = new ArrayList<>();
            for (Location location : event.getLocations()) {
                if (location.getId() == null) {
                    return ResponseEntity.badRequest().build();
                }
                Optional<Location> optionalLocation = locationRepository.findById(location.getId());
                if (optionalLocation.isEmpty()) {
                    return ResponseEntity.badRequest().build();
                } else {
                    locations.add(optionalLocation.get());
                }
            }
            eventToModify.setLocations(locations);
        }

        Event savedEvent = eventRepository.save(eventToModify);
        return ResponseEntity.ok(savedEvent);
    }

    //functioning as expected
    @DeleteMapping("/{id}")
    public ResponseEntity deleteEvent(@PathVariable Integer id) {

        Optional<Event> optionalEvent = eventRepository.findById(id);



        if (!optionalEvent.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        if (!authUserRoles().contains("ROLE_ADMIN")){
            if (!authUser().getEvents().contains(optionalEvent.get())) {
                return ResponseEntity.notFound().build();
            }
        }

        participationRepository.deleteAll(participationRepository.findAllByEventEquals(optionalEvent.get()));
        eventRepository.deleteById(id);
        return ResponseEntity.ok().build();

    }

    //functioning as expected
    @GetMapping("/{id}/locations")
    public ResponseEntity<Iterable<Location>> getEventLocations(@PathVariable Integer id) {
        Optional<Event> optionalEvent = eventRepository.findById(id);

        if (!optionalEvent.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        if (!authUserRoles().contains("ROLE_ADMIN")){
            if (!authUser().getEvents().contains(optionalEvent.get())) {
                return ResponseEntity.notFound().build();
            }
        }

        return ResponseEntity.ok(optionalEvent.get().getLocations());

    }

    //functioning as expected
    @PostMapping("/{id}/locations")
    public ResponseEntity<Location> postEventLocation(@PathVariable Integer id, @RequestBody Location location) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (!optionalEvent.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        if (!authUserRoles().contains("ROLE_ADMIN")){
            if (!authUser().getEvents().contains(optionalEvent.get())) {
                return ResponseEntity.notFound().build();
            }
        }

        Event event = optionalEvent.get();
        Location newLocation = locationRepository.save(location);
        if (!event.getLocations().contains(location)) {
            event.getLocations().add(newLocation);
        }
        eventRepository.save(event);
        return ResponseEntity.ok(newLocation);



    }

    //functioning as expected
    @DeleteMapping("/{eventId}/locations/{locationId}")
    public ResponseEntity <List<Location>> deleteEventLocation(@PathVariable Integer eventId, @PathVariable Integer locationId) {
        Optional<Event> optionalEvent = eventRepository.findById(eventId);

        if (!optionalEvent.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        if (!authUserRoles().contains("ROLE_ADMIN")){
            if (!authUser().getEvents().contains(optionalEvent.get())) {
                return ResponseEntity.notFound().build();
            }
        }

        Event event = optionalEvent.get();
        Optional<Location> locationToDelete= locationRepository.findById(locationId);
        if (!locationToDelete.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        List<Location> newLocations = new ArrayList<>();
        if (event.getLocations().contains(locationToDelete.get())) {

            for (Location l : event.getLocations()) {
                if (!l.equals(locationToDelete.get())) {
                    newLocations.add(l);
                }
            }
            event.setLocations(newLocations);
        }
        eventRepository.save(event);
        return ResponseEntity.ok(newLocations);

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
