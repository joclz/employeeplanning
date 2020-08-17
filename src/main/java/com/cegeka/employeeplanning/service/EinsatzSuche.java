package com.cegeka.employeeplanning.service;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EinsatzSuche {
    Integer mitarbeiterVertriebId;
    Integer mitarbeiterId;
    String mitarbeiterStatus;
    String einsatzStatus;
    String beginnVon;
    String beginnBis;
    String endeVon;
    String endeBis;

    public EinsatzSuche(Integer mitarbeiterVertriebId, Integer mitarbeiterId, String mitarbeiterStatus,
                        String einsatzStatus,
                        String beginnVon, String beginnBis,
                        String endeVon, String endeBis) {
        this.mitarbeiterVertriebId = mitarbeiterVertriebId;
        this.mitarbeiterId = mitarbeiterId;
        this.mitarbeiterStatus = mitarbeiterStatus;
        this.einsatzStatus = einsatzStatus;
        this.beginnVon = beginnVon;
        this.beginnBis = beginnBis;
        this.endeVon = endeVon;
        this.endeBis = endeBis;
    }
}
