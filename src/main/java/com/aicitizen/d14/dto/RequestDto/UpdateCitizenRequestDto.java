package com.aicitizen.d14.dto.RequestDto;

import com.aicitizen.d14.entity.Citizen;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UpdateCitizenRequestDto {
    private boolean isCitizen;
   private String name;
   private boolean hasDrivingLicense;
   private List<Citizen> children;
}
