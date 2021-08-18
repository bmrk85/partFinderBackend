package hu.bmrk.geposszerakobackend.model.entities;

import hu.bmrk.geposszerakobackend.model.enums.CPUManufacturer;
import hu.bmrk.geposszerakobackend.model.enums.IntelCPUSeries;
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
@Entity(name = "intel_processor")
public class IntelProcessor {

    @Id
    @GeneratedValue
    private Long id;

    private String fullName;

    @Enumerated(EnumType.STRING)
    private CPUManufacturer manufacturer;

    @Enumerated(EnumType.STRING)
    private IntelCPUSeries series;

    private String modelName;

    private String cheapestShop;

    private Integer cheapestPrice;

    private LocalDateTime lastUpdated;


}
