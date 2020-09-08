package com.cegeka.employeeplanning.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MitarbeiterDTO {
    private String name;
    private String id;

    public MitarbeiterDTO(String name, String id) {
        this.name = name;
        this.id = id;
    }
}
