package com.aicitizen.d14.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "citizen")
public class Citizen {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private boolean isCitizen;
    private String name;
    private boolean hasDrivingLicense;
    private Integer numberOfChildren;

    @ElementCollection
    private List<SubCitizenDto> children = new ArrayList<>();

    @ElementCollection
    private List<SubCitizenDto> parent= new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime dataInsertionDateTime;
}
