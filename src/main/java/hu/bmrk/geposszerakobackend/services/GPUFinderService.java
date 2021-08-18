package hu.bmrk.geposszerakobackend.services;

import hu.bmrk.geposszerakobackend.model.dto.GPUResponseDTO;

import java.util.List;

public interface GPUFinderService {

    List<GPUResponseDTO> getGPUs(String manufacturer, String series);

}
