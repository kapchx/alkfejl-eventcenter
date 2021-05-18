package hu.elte.eventcenter.controller;

import hu.elte.eventcenter.model.Location;
import hu.elte.eventcenter.repository.LocationRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/locations")
public class LocationController {
    private LocationRepository locationRepository;
    public LocationController(@Autowired LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    //functioning as expected
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

    //functioning as expected
    @Secured({ "ROLE_ADMIN" })
    @PostMapping("")
    public ResponseEntity<Location> postLocation(@RequestBody Location location){
        Optional<Location> optionalLocation = locationRepository.findOneByNameEqualsIgnoreCase(location.getName());
        if (optionalLocation.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(optionalLocation.get());
        }
        Location postedLocation = locationRepository.save(location);
        return ResponseEntity.ok(postedLocation);
    }

    //functioning as expected
    @Secured({ "ROLE_ADMIN" })
    @DeleteMapping("/{locationId}")
    public ResponseEntity deleteLocation(@PathVariable Integer locationId){

        Optional<Location> optionalLocation = locationRepository.findById(locationId);

        if (!optionalLocation.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        locationRepository.deleteById(locationId);
        return ResponseEntity.ok().build();

    }
}
