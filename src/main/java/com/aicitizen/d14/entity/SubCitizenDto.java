package com.aicitizen.d14.entity;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class SubCitizenDto {
    private Long id;
    private String name;

    public SubCitizenDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public SubCitizenDto() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubCitizenDto that = (SubCitizenDto) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
