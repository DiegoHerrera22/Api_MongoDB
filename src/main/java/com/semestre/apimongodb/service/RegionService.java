package com.semestre.apimongodb.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.semestre.apimongodb.model.Region;
import com.semestre.apimongodb.repository.RegionRepository;

@Service
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;

    public Region addRegion(Region region) {
        region.setId(UUID.randomUUID().toString().split("-")[0]);
        return regionRepository.save(region);
    }

    public List<Region> findAllRegions() {
        return regionRepository.findAll();
    }

    public Region findRegionById(String id) {
        return regionRepository.findById(id).orElse(null);
    }

    public Region updateRegion(Region region) {
        Region existingRegion = regionRepository.findById(region.getId()).orElse(null);
        if (existingRegion != null) {
            existingRegion.setName(region.getName());
            existingRegion.setCountry(region.getCountry());
            existingRegion.setAbbreviation(region.getAbbreviation());
            return regionRepository.save(existingRegion);
        }
        return null;
    }

    public String deleteRegion(String id) {
        regionRepository.deleteById(id);
        return "Region with id " + id + " has been deleted.";
    }
}