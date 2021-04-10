package hu.elte.eventcenter.repository;

import hu.elte.eventcenter.model.Location;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends CrudRepository<Location, Integer> {

    Iterable<Location> findTop10ByNameContains(String name);
    Optional<Location> findOneByNameEqualsIgnoreCase(String name);
}
