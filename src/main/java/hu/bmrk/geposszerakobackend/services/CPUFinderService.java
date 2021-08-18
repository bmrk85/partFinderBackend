package hu.bmrk.geposszerakobackend.services;

import hu.bmrk.geposszerakobackend.model.dto.CPUResponseDTO;
import hu.bmrk.geposszerakobackend.model.dto.ProductInfoResponseDTO;

import java.util.List;

public interface CPUFinderService {

    ProductInfoResponseDTO getProductInfo(String productName);

    List<CPUResponseDTO> getCPUs(String manufacturer, String series);


}
