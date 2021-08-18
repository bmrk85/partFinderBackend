package hu.bmrk.geposszerakobackend.model.entities;

import hu.bmrk.geposszerakobackend.model.enums.AMDCPUSeries;
import hu.bmrk.geposszerakobackend.model.enums.CPUManufacturer;
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
@Entity(name = "amd_processor")
public class AMDProcessor {

    @Id
    @GeneratedValue
    private Long id;

    private String fullName;

    @Enumerated(EnumType.STRING)
    private CPUManufacturer manufacturer;

    @Enumerated(EnumType.STRING)
    private AMDCPUSeries series;

    private String modelName;

    private String cheapestShop;

    private Integer cheapestPrice;

    private LocalDateTime lastUpdated;

    @Override
    public String toString() {
        return manufacturer +
                " " + series +
                " " + modelName;
    }
}
