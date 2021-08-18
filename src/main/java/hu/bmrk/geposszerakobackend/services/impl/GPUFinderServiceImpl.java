package hu.bmrk.geposszerakobackend.services.impl;

import hu.bmrk.geposszerakobackend.engine.arukereso.PriceAndShopFinderModule;
import hu.bmrk.geposszerakobackend.engine.arukereso.ProductFinderModule;
import hu.bmrk.geposszerakobackend.engine.repositories.AMDCPURepo;
import hu.bmrk.geposszerakobackend.engine.repositories.AMDGPURepo;
import hu.bmrk.geposszerakobackend.model.dto.CPUResponseDTO;
import hu.bmrk.geposszerakobackend.model.dto.GPUResponseDTO;
import hu.bmrk.geposszerakobackend.model.dto.ProductInfoResponseDTO;
import hu.bmrk.geposszerakobackend.model.entities.AMDGPU;
import hu.bmrk.geposszerakobackend.model.entities.AMDProcessor;
import hu.bmrk.geposszerakobackend.model.entities.IntelProcessor;
import hu.bmrk.geposszerakobackend.model.enums.AMDCPUSeries;
import hu.bmrk.geposszerakobackend.model.enums.AMDGPUSeries;
import hu.bmrk.geposszerakobackend.services.GPUFinderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GPUFinderServiceImpl implements GPUFinderService {

    @Autowired
    PriceAndShopFinderModule priceAndShopFinderModule;

    @Autowired
    ProductFinderModule productFinderModule;

    @Autowired
    AMDGPURepo amdgpuRepo;



    @Override
    public List<GPUResponseDTO> getGPUs(String manufacturer, String series) {
        if (manufacturer.equalsIgnoreCase("AMD")) {
            List<AMDGPU> amdGPUs = amdgpuRepo.findAllBySeriesIs(AMDGPUSeries.valueOf(series.replace(" ", "")));
            return amdGPUs.stream().parallel().map(gpu -> {
                if(gpu.getLastUpdated() == null || gpu.getLastUpdated().plusDays(1).isBefore(LocalDateTime.now())){
                    gpu = refreshPriceForAMDGPU(gpu);
                }

                return GPUResponseDTO.builder()
                        .manufacturer(gpu.getManufacturer().getName())
                        .series(gpu.getSeries().getName())
                        .modelName(gpu.getModelName())
                        .fullName(gpu.getFullName())
                        .cheapestPrice(gpu.getCheapestPrice())
                        .cheapestShop(gpu.getCheapestShop())
                        .lastUpdated(gpu.getLastUpdated())
                        .build();
            }).collect(Collectors.toList());
        } /*else if (manufacturer.equalsIgnoreCase("Nvidia")) {
            List<IntelProcessor> amdProcessors = intelCPURepo.findAll();
            return amdProcessors.stream().map(cpu -> CPUResponseDTO.builder()
                    .manufacturer(cpu.getManufacturer().getName())
                    .series(cpu.getSeries().getName())
                    .modelName(cpu.getModelName())
                    .cheapestPrice(cpu.getCheapestPrice())
                    .cheapestShop(cpu.getCheapestShop())
                    .lastUpdated(cpu.getLastUpdated())
                    .build()).collect(Collectors.toList());
        } */else {
            throw new UnsupportedOperationException("nincs ilyen");
        }
    }

    private AMDGPU refreshPriceForAMDGPU(AMDGPU gpu) {
        try {
            String url = productFinderModule.search(gpu.getFullName(), gpu.toString());
            ProductInfoResponseDTO response = priceAndShopFinderModule.findPriceAndShop(url);
            return amdgpuRepo.save(AMDGPU.builder()
                    .cheapestPrice(response.getCheapestPrice())
                    .cheapestShop(response.getCheapestShop())
                    .lastUpdated(LocalDateTime.now())
                    .manufacturer(gpu.getManufacturer())
                    .modelName(gpu.getModelName())
                    .series(gpu.getSeries())
                    .fullName(gpu.getFullName())
                    .id(gpu.getId())
                    .build());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }




}
