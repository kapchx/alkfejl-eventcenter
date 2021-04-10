package hu.elte.eventcenter.controller;

import hu.elte.eventcenter.model.Participation;
import hu.elte.eventcenter.model.User;
import hu.elte.eventcenter.repository.ParticipationRepository;
import hu.elte.eventcenter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/participation")
public class ParticipationController {

    @Autowired
    private ParticipationRepository participationRepository;

    @PostMapping("/{id}")
    public ResponseEntity<Participation> participate(@RequestBody Participation participation) {
        participation.setApproved(Participation.Approval.APPLIED);
        participationRepository.save(participation);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Participation> modifyApproval(@RequestBody Participation.Approval approval, @PathVariable Integer id){
        Optional<Participation> optionalParticipation = participationRepository.findById(id);
        if (optionalParticipation.isPresent()) {
            Participation editedParticipation = optionalParticipation.get();
            editedParticipation.setApproved(approval);
            participationRepository.save(editedParticipation);
            return ResponseEntity.ok(editedParticipation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        Optional<Participation> optionalParticipation = participationRepository.findById(id);
        if (optionalParticipation.isPresent()) {
            participationRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
