package com.cegeka.employeeplanning.data.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MitarbeiterItem {
    private String name;
    private String id;

    public MitarbeiterItem(String name, String id) {
        this.name = name;
        this.id = id;
    }
}
