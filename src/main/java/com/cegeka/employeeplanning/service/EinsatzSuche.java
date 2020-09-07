package com.cegeka.employeeplanning.service;

import java.util.Date;

import com.cegeka.employeeplanning.data.enums.Enums;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EinsatzSuche {
    Integer mitarbeiterVertriebId;
    Integer mitarbeiterId;
    Enums.MitarbeiterStatus mitarbeiterStatus;
    Enums.EinsatzStatus einsatzStatus;
    Date beginnVon;
    Date beginnBis;
    Date endeVon;
    Date endeBis;

    public EinsatzSuche(Integer mitarbeiterVertriebId,
                        Integer mitarbeiterId,
                        Enums.MitarbeiterStatus mitarbeiterStatus,
                        Enums.EinsatzStatus einsatzStatus,
                        Date beginnVon, Date beginnBis,
                        Date endeVon, Date endeBis) {
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
