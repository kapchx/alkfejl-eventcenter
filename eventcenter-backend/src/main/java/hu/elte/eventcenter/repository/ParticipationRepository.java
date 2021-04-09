package hu.elte.eventcenter.repository;

import hu.elte.eventcenter.model.Event;
import hu.elte.eventcenter.model.Participation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipationRepository extends CrudRepository<Participation, Integer> {
    Iterable<Participation> findAllByEventEquals(Event event);
}
