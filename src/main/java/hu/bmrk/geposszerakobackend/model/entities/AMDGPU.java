package hu.bmrk.geposszerakobackend.model.entities;

import hu.bmrk.geposszerakobackend.model.enums.AMDCPUSeries;
import hu.bmrk.geposszerakobackend.model.enums.AMDGPUSeries;
import hu.bmrk.geposszerakobackend.model.enums.CPUManufacturer;
import hu.bmrk.geposszerakobackend.model.enums.GPUManufacturer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "amd_gpu")
public class AMDGPU {

    @Id
    @GeneratedValue
    private Long id;

    private String fullName;

    @Enumerated(EnumType.STRING)
    private GPUManufacturer manufacturer;

    @Enumerated(EnumType.STRING)
    private AMDGPUSeries series;

    private String modelName;

    private String cheapestShop;

    private Integer cheapestPrice;

    private LocalDateTime lastUpdated;

    @Override
    public String toString() {
        return modelName;
    }

}
