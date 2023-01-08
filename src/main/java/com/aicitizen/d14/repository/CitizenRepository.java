package com.aicitizen.d14.repository;

import com.aicitizen.d14.entity.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CitizenRepository extends JpaRepository<Citizen, Long> {

    @Modifying
    @Query("UPDATE Citizen c SET c.parent = :parent WHERE c.id IN :ids")
    int updateChildren(@Param("parent") Citizen parent, @Param("ids") List<Long> ids);
}