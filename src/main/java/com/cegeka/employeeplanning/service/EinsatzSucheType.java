package com.cegeka.employeeplanning.service;

import com.cegeka.employeeplanning.data.enums.Enums;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EinsatzSucheType {
    Integer mitarbeiterVertriebId;
    Integer mitarbeiterId;
    Enums.MitarbeiterStatus mitarbeiterStatus;
    Enums.EinsatzStatus einsatzStatus;
    String beginnVon;
    String beginnBis;
    String endeVon;
    String endeBis;

    public EinsatzSucheType(Integer mitarbeiterVertriebId,
                        Integer mitarbeiterId,
                        Enums.MitarbeiterStatus mitarbeiterStatus,
                        Enums.EinsatzStatus einsatzStatus,
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
