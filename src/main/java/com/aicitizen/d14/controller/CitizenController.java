package com.aicitizen.d14.controller;

import com.aicitizen.d14.dto.RequestDto.AddCitizenRequestDto;
import com.aicitizen.d14.dto.RequestDto.UpdateCitizenRequestDto;
import com.aicitizen.d14.entity.Citizen;
import com.aicitizen.d14.service.CitizenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/citizens")
public class CitizenController {
    private final CitizenService citizenService;

    public CitizenController(CitizenService citizenService) {
        this.citizenService = citizenService;
    }

    @GetMapping
    public List<Citizen> getCitizens(@RequestParam(required = false) Boolean isCitizen,
                                     @RequestParam(required = false) String name,
                                     @RequestParam(required = false) Integer numberOfChildren,
                                     @RequestParam(required = false) Boolean hasDrivingLicense) {
        return citizenService.findAllWithParameters(isCitizen,name,numberOfChildren, hasDrivingLicense);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Citizen> getCitizen(@PathVariable Long id) {
        return  ResponseEntity.ok(citizenService.findCitizenById(id));
    }

    @PostMapping
    public ResponseEntity<Citizen> addCitizen(@RequestBody AddCitizenRequestDto citizenDto) {
        Citizen citizen = citizenService.addCitizen(citizenDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(citizen);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Citizen> updateCitizen(@PathVariable Long id, @RequestBody UpdateCitizenRequestDto citizenDto) {
        return ResponseEntity.ok(  citizenService.updateCitizen(id,citizenDto));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCitizen(@PathVariable Long id) {
        citizenService.deleteCitizen(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }
}