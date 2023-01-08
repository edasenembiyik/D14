package com.aicitizen.d14.service;

import com.aicitizen.d14.dto.RequestDto.AddCitizenRequestDto;
import com.aicitizen.d14.dto.RequestDto.UpdateCitizenRequestDto;
import com.aicitizen.d14.entity.Citizen;
import com.aicitizen.d14.exception.CitizenServiceException;
import com.aicitizen.d14.exception.ErrorType;
import com.aicitizen.d14.mapper.CitizenMapper;
import com.aicitizen.d14.repository.CitizenRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CitizenService {

    private final CitizenRepository citizenRepository;

    public CitizenService(CitizenRepository citizenRepository) {
        this.citizenRepository = citizenRepository;
    }

    public List<Citizen> findAllWithParameters(Boolean isCitizen, String name, Integer numChildren, Boolean hasDrivingLicense) {
       return citizenRepository.findAll();
//        List<Citizen> matchingCitizens = new ArrayList<>();
//        for (Citizen citizen : citizenRepository.findAll()) {
//            if (isCitizen == null && name == null && numChildren == null && hasDrivingLicense == null) {
//                return citizenRepository.findAll();
//            }
//            if (isCitizen != null && citizen.isCitizen() != isCitizen) {
//                continue;
//            }
//            if (name != null && !citizen.getName().contains(name)) {
//                continue;
//            }
//            if (numChildren != null && citizen.getNumberOfChildren() != numChildren) {
//                continue;
//            }
//            if (hasDrivingLicense != null && citizen.isHasDrivingLicense() != hasDrivingLicense) {
//                continue;
//            }
//            matchingCitizens.add(citizen);
//        }
//
//        return matchingCitizens;
    }

    public Citizen findCitizenById(Long id) {
        Optional<Citizen> citizen = citizenRepository.findById(id);
        if (citizen.isPresent()) {
            return citizen.get();
        } else {
            throw new CitizenServiceException(ErrorType.CITIZEN_NOT_FOUND);
        }

    }

    @Transactional
    public Citizen addCitizen(AddCitizenRequestDto citizenDto) {
        List<Long> childrenIdList = new ArrayList<>();
        List<Citizen> childrenList = new ArrayList<>();

        try {
            Citizen citizen = CitizenMapper.INSTANCE.toCitizen(citizenDto);
            for (Long id : citizenDto.getChidrenId()) {
                Citizen child = findCitizenById(id);
                childrenList.add(child);
                childrenIdList.add(child.getId());
            }
            citizen.setChildren(childrenList);
            citizen.setNumberOfChildren(childrenIdList.size());
            Citizen save = citizenRepository.save(citizen);
            if (childrenList.size() > 0)
                citizenRepository.updateChildren(save, childrenIdList);

            return save;
        } catch (Exception e) {
            throw new CitizenServiceException(ErrorType.CITIZEN_NOT_CREATED);
        }
    }

    @Transactional
    public Citizen updateCitizen(Long id, UpdateCitizenRequestDto citizenDto) {
        Optional<Citizen> citizen = citizenRepository.findById(id);
        if (citizen.isPresent()) {
            citizen.get().setCitizen(citizenDto.isCitizen());
            citizen.get().setName(citizenDto.getName());
//            citizen.get().setChildren(citizenDto.getChildren());
            citizenRepository.save(citizen.get());
            return citizen.get();
        } else {
            throw new CitizenServiceException(ErrorType.CITIZEN_NOT_UPDATED);
        }

    }

    public void deleteCitizen(Long id) {
        Optional<Citizen> citizen = citizenRepository.findById(id);
        if (citizen.isPresent()) {
            citizenRepository.delete(citizen.get());
        } else {
            throw new CitizenServiceException(ErrorType.CITIZEN_NOT_DELETED);
        }
    }


}
