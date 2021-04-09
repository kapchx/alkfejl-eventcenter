package hu.elte.eventcenter.controller;

import hu.elte.eventcenter.model.Location;
import hu.elte.eventcenter.repository.LocationRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/locations")
public class LocationController {
    private LocationRepository locationRepository;
    public LocationController(@Autowired LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @GetMapping("")
    public Iterable<Location> getLocations(@RequestParam Optional<String> name) {
        Iterable<Location> locations;
        if(name.isPresent()) {
            locations = locationRepository.findTop10ByNameContains(name.get());
        } else {
            locations = locationRepository.findTop10ByNameContains("");
        }
        return locations;
    }

    @PostMapping("")
    public ResponseEntity<Location> createLocation(@RequestBody Location location){
        Optional<Location> optionalLocation = locationRepository.findOneByNameEqualsIgnoreCase(location.getName());
        if (optionalLocation.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(optionalLocation.get());
        }
        Location createdLocation = locationRepository.save(location);
        return ResponseEntity.ok(createdLocation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteLocation(@PathVariable Integer id){
        Optional<Location> optionalLocation = locationRepository.findById(id);
        if (optionalLocation.isPresent()) {
            locationRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }
}
