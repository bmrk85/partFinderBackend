package hu.bmrk.geposszerakobackend.engine.repositories;

import hu.bmrk.geposszerakobackend.model.entities.AMDProcessor;
import hu.bmrk.geposszerakobackend.model.enums.AMDCPUSeries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AMDCPURepo extends JpaRepository<AMDProcessor, Long> {

    List<AMDProcessor> findAllBySeriesIs(AMDCPUSeries series);

}
