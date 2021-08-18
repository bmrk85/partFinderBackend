package hu.bmrk.geposszerakobackend.engine.rest;

import hu.bmrk.geposszerakobackend.model.dto.CPUResponseDTO;
import hu.bmrk.geposszerakobackend.model.dto.GPUResponseDTO;
import hu.bmrk.geposszerakobackend.model.dto.ProductInfoResponseDTO;
import hu.bmrk.geposszerakobackend.services.CPUFinderService;
import hu.bmrk.geposszerakobackend.services.GPUFinderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class SearchController {

    @Autowired
    CPUFinderService CPUFinderService;

    @Autowired
    GPUFinderService GPUFinderService;


    @PostMapping("/api/{productName}")
    public ResponseEntity<ProductInfoResponseDTO> getProductInfo(@PathVariable String productName) {

        ProductInfoResponseDTO response = CPUFinderService.getProductInfo(productName);
        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/api/cpu/{manufacturer}/{series}")
    public ResponseEntity<List<CPUResponseDTO>> getCPUs(@PathVariable String manufacturer, @PathVariable String series){
        return new ResponseEntity<>(CPUFinderService.getCPUs(manufacturer, series), HttpStatus.OK);
    }

    @GetMapping("/api/gpu/{manufacturer}/{series}")
    public ResponseEntity<List<GPUResponseDTO>> getGPUs(@PathVariable String manufacturer, @PathVariable String series){
        return new ResponseEntity<>(GPUFinderService.getGPUs(manufacturer, series), HttpStatus.OK);
    }


}
