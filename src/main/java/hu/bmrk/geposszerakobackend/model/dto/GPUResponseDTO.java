package hu.bmrk.geposszerakobackend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GPUResponseDTO {

    private String manufacturer;

    private String series;

    private String modelName;

    private String fullName;

    private String cheapestShop;

    private Integer cheapestPrice;

    private LocalDateTime lastUpdated;


}
