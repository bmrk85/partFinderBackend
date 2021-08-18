package hu.bmrk.geposszerakobackend.engine.repositories;

import hu.bmrk.geposszerakobackend.model.entities.AMDGPU;
import hu.bmrk.geposszerakobackend.model.entities.AMDProcessor;
import hu.bmrk.geposszerakobackend.model.enums.AMDCPUSeries;
import hu.bmrk.geposszerakobackend.model.enums.AMDGPUSeries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AMDGPURepo extends JpaRepository<AMDGPU, Long> {

    List<AMDGPU> findAllBySeriesIs(AMDGPUSeries series);
}
