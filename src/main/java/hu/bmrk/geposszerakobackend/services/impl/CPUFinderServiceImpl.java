package hu.bmrk.geposszerakobackend.services.impl;

import hu.bmrk.geposszerakobackend.engine.repositories.AMDCPURepo;
import hu.bmrk.geposszerakobackend.engine.repositories.IntelCPURepo;
import hu.bmrk.geposszerakobackend.model.dto.CPUResponseDTO;
import hu.bmrk.geposszerakobackend.model.dto.ProductInfoResponseDTO;
import hu.bmrk.geposszerakobackend.engine.arukereso.PriceAndShopFinderModule;
import hu.bmrk.geposszerakobackend.engine.arukereso.ProductFinderModule;
import hu.bmrk.geposszerakobackend.model.entities.AMDProcessor;
import hu.bmrk.geposszerakobackend.model.entities.IntelProcessor;
import hu.bmrk.geposszerakobackend.model.enums.AMDCPUSeries;
import hu.bmrk.geposszerakobackend.services.CPUFinderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CPUFinderServiceImpl implements CPUFinderService {

    @Autowired
    PriceAndShopFinderModule priceAndShopFinderModule;

    @Autowired
    ProductFinderModule productFinderModule;

    @Autowired
    AMDCPURepo amdCPURepo;

    @Autowired
    IntelCPURepo intelCPURepo;

    @Override
    public ProductInfoResponseDTO getProductInfo(String productName) {
        try {
            String productUrl = productFinderModule.search("asd", productName);
            return priceAndShopFinderModule.findPriceAndShop(productUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<CPUResponseDTO> getCPUs(String manufacturer, String series) {
        if (manufacturer.equalsIgnoreCase("AMD")) {
            List<AMDProcessor> amdProcessors = amdCPURepo.findAllBySeriesIs(AMDCPUSeries.valueOf(series.replace(" ", "")));
            return amdProcessors.stream().parallel().map(cpu -> {
                if(cpu.getLastUpdated() == null || cpu.getLastUpdated().plusDays(1).isBefore(LocalDateTime.now())){
                    cpu = refreshPriceForAMDCPU(cpu);
                }

                return CPUResponseDTO.builder()
                    .manufacturer(cpu.getManufacturer().getName())
                    .series(cpu.getSeries().getName())
                    .modelName(cpu.getModelName())
                    .cheapestPrice(cpu.getCheapestPrice())
                    .cheapestShop(cpu.getCheapestShop())
                    .lastUpdated(cpu.getLastUpdated())
                    .build();
            }).collect(Collectors.toList());
        } else if (manufacturer.equalsIgnoreCase("INTEL")) {
            List<IntelProcessor> amdProcessors = intelCPURepo.findAll();
            return amdProcessors.stream().map(cpu -> CPUResponseDTO.builder()
                    .manufacturer(cpu.getManufacturer().getName())
                    .series(cpu.getSeries().getName())
                    .modelName(cpu.getModelName())
                    .cheapestPrice(cpu.getCheapestPrice())
                    .cheapestShop(cpu.getCheapestShop())
                    .lastUpdated(cpu.getLastUpdated())
                    .build()).collect(Collectors.toList());
        } else {
            throw new UnsupportedOperationException("nincs ilyen");
        }
    }

    private AMDProcessor refreshPriceForAMDCPU(AMDProcessor cpu) {
        try {
            String url = productFinderModule.search(cpu.getFullName(), cpu.toString());
            ProductInfoResponseDTO response = priceAndShopFinderModule.findPriceAndShop(url);
            return amdCPURepo.save(AMDProcessor.builder()
                    .cheapestPrice(response.getCheapestPrice())
                    .cheapestShop(response.getCheapestShop())
                    .lastUpdated(LocalDateTime.now())
                    .manufacturer(cpu.getManufacturer())
                    .modelName(cpu.getModelName())
                    .series(cpu.getSeries())
                    .fullName(cpu.getFullName())
                    .id(cpu.getId())
                    .build());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
