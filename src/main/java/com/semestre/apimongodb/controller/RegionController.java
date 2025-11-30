package com.semestre.apimongodb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.semestre.apimongodb.model.Region;
import com.semestre.apimongodb.service.RegionService;

@RestController
@RequestMapping("/regions")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @PostMapping
    @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    public Region createRegion(@RequestBody Region region) {
        return regionService.addRegion(region);
    }

    @GetMapping
    public List<Region> getAllRegions() {
        return regionService.findAllRegions();
    }

    @GetMapping("/{id}")
    public Region getRegionById(@PathVariable String id) {
        return regionService.findRegionById(id);
    }

    @PutMapping
    public Region updateRegion(@RequestBody Region region) {
        return regionService.updateRegion(region);
    }

    @DeleteMapping("/{id}")
    public String deleteRegion(@PathVariable String id) {
        return regionService.deleteRegion(id);
    }
}