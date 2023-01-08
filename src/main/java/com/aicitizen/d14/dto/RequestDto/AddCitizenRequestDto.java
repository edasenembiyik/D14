package com.aicitizen.d14.dto.RequestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AddCitizenRequestDto {
    boolean isCitizen;
    String name;
    boolean hasDrivingLicense;

    private List<Long> chidrenId;
}

