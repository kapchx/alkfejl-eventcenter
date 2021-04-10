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

    @GetMapping("")
    public ResponseEntity<Iterable<Event>> getEvents(@RequestParam(required = false) String location) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<String> roles = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        if (roles.contains("ROLE_ADMIN")) {
            return ResponseEntity.ok(eventRepository.findAll());
        }

        String username = auth.getName();
        Optional<User> user = userRepository.findByUsername(username);
        Iterable<Event> events;
        events = eventRepository.findAll();
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get().getEvents());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<Event> getEvent(@PathVariable Integer eventId) {
        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        if (optionalEvent.isPresent()) {
            return ResponseEntity.ok(optionalEvent.get());
        }  else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("")
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
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
        event.setLocations(locations);
        Event savedEvent = eventRepository.save(event);
        return ResponseEntity.ok(savedEvent);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Event> modifyEvent(@RequestBody Event event, @PathVariable Integer id) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (!optionalEvent.isPresent()) {
            return ResponseEntity.notFound().build();
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

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (!optionalEvent.isPresent()) {
            return ResponseEntity.notFound().build();
        } else {
            participationRepository.deleteAll(
                    participationRepository.findAllByEventEquals(optionalEvent.get())
            );
            eventRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
    }

    @GetMapping("/{id}/locations")
    public ResponseEntity<Iterable<Location>> getLocations(@PathVariable Integer id) {
        Optional<Event> event = eventRepository.findById(id);
        if (event.isPresent()) {
            return ResponseEntity.ok(event.get().getLocations());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/locations")
    public ResponseEntity<Location> addLocation(@PathVariable Integer id, @RequestBody Location location) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            Location newLocation = locationRepository.save(location);
            if (!event.getLocations().contains(location)) {
                event.getLocations().add(newLocation);
            }
            eventRepository.save(event);
            return ResponseEntity.ok(newLocation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{eventId}/locations/{locationId}")
    public ResponseEntity deleteLocation(@PathVariable Integer eventId, @PathVariable Integer locationId) {
        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        if (!optionalEvent.isPresent()) {
            return ResponseEntity.notFound().build();
        } else {
            Event event = optionalEvent.get();
            Optional<Location> locationToDelete= locationRepository.findById(locationId);
            if (event.getLocations().contains(locationToDelete)) {
                List<Location> newLocations = new ArrayList<>();
                for (Location l : event.getLocations()) {
                    if (!l.equals(locationToDelete)) {
                        newLocations.add(l);
                    }
                }
                event.setLocations(newLocations);
            }
            eventRepository.save(event);
            return ResponseEntity.ok().build();
        }
    }
}
