package hu.bmrk.geposszerakobackend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfoResponseDTO {

    private Integer cheapestPrice;
    private String cheapestShop;

}
