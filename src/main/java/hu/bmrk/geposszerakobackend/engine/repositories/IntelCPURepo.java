package hu.bmrk.geposszerakobackend.engine.repositories;

import hu.bmrk.geposszerakobackend.model.entities.IntelProcessor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntelCPURepo extends JpaRepository<IntelProcessor, Long> {
}
