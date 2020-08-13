package com.cegeka.employeeplanning.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EinsatzSuche {
    Integer mitarbeiterVertriebId;
    Integer mitarbeiterId;
    String einsatzStatus;
    String beginn;
    String ende;

    public EinsatzSuche(Integer mitarbeiterVertriebId, Integer mitarbeiterId, String einsatzStatus,
                        String beginn, String ende) {
        this.mitarbeiterVertriebId = mitarbeiterVertriebId;
        this.mitarbeiterId = mitarbeiterId;
        this.einsatzStatus = einsatzStatus;
        this.beginn = beginn;
        this.ende = ende;
    }
}
