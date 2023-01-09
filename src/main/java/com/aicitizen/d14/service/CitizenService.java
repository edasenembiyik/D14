package com.aicitizen.d14.service;

import com.aicitizen.d14.dto.RequestDto.AddCitizenRequestDto;
import com.aicitizen.d14.dto.RequestDto.UpdateCitizenRequestDto;
import com.aicitizen.d14.entity.Citizen;
import com.aicitizen.d14.entity.SubCitizenDto;
import com.aicitizen.d14.exception.CitizenServiceException;
import com.aicitizen.d14.exception.ErrorType;
import com.aicitizen.d14.mapper.CitizenMapper;
import com.aicitizen.d14.repository.CitizenRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CitizenService {

    private final CitizenRepository citizenRepository;
    private final EntityManager entityManager;

    public CitizenService(CitizenRepository citizenRepository, EntityManager entityManager) {
        this.citizenRepository = citizenRepository;
        this.entityManager = entityManager;
    }

    List<Citizen> findBooksByAuthorNameAndTitle(Boolean isCitizen, String name, Integer numChildren, Boolean hasDrivingLicense) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Citizen> cq = cb.createQuery(Citizen.class);

        Root<Citizen> citizenRoot = cq.from(Citizen.class);
        List<Predicate> predicates = new ArrayList<>();

        if (name != null) {
            predicates.add(cb.like(citizenRoot.get("name"), "%" +name + "%"));
        }
        if (isCitizen != null) {
            predicates.add(cb.equal(citizenRoot.get("isCitizen"), isCitizen));
        }
        if (hasDrivingLicense != null) {
            predicates.add(cb.equal(citizenRoot.get("hasDrivingLicense"), hasDrivingLicense));
        }
        if (numChildren != null && numChildren > 0) {
            predicates.add(cb.equal(citizenRoot.get("numberOfChildren"), numChildren));
        }

        cq.where(predicates.toArray(new Predicate[0]));
        List<Citizen> resultList = entityManager.createQuery(cq).getResultList();
        return resultList;
    }

    public List<Citizen> findAllWithParameters(Boolean isCitizen, String name, Integer numChildren, Boolean hasDrivingLicense) {
        return findBooksByAuthorNameAndTitle(isCitizen, name, numChildren, hasDrivingLicense);
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
        List<Citizen> childList = new ArrayList<>();
        List<SubCitizenDto> childrenList = new ArrayList<>();

        try {
            Citizen citizen = CitizenMapper.INSTANCE.toCitizen(citizenDto);
            for (Long id : citizenDto.getChidrenId()) {
                Citizen child = findCitizenById(id);
                childrenList.add(new SubCitizenDto(child.getId(), child.getName()));
                childList.add(child);
            }
            citizen.setChildren(childrenList);
            citizen.setNumberOfChildren(childrenList.size());
            Citizen save = citizenRepository.save(citizen);
            if (childList.size() > 0)
                for (Citizen child : childList) {
                    List<SubCitizenDto> parent = child.getParent();
                    parent.add(new SubCitizenDto(save.getId(), save.getName()));
                    child.setParent(parent);
                    citizenRepository.save(child);
                }
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
