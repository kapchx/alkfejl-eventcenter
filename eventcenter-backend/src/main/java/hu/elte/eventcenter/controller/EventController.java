package hu.elte.eventcenter.controller;

import hu.elte.eventcenter.model.Event;
import hu.elte.eventcenter.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/events")
public class EventController {

    private EventRepository eventRepository;

    public EventController(@Autowired EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @GetMapping("")
    public ResponseEntity<Iterable<Event>> getEvents(@RequestParam(required = false) String location) {
        Iterable<Event> events;
        events = eventRepository.findAll();
        return ResponseEntity.ok(events);
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
}
