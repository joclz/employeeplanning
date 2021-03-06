package com.cegeka.employeeplanning.data.util;

import java.util.Date;

import com.cegeka.employeeplanning.data.enums.Enums;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
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
    Integer wahrscheinlichkeitVon;
    Integer wahrscheinlichkeitBis;

    public EinsatzSuche(Integer mitarbeiterVertriebId,
                        Integer mitarbeiterId,
                        Enums.MitarbeiterStatus mitarbeiterStatus,
                        Enums.EinsatzStatus einsatzStatus,
                        Date beginnVon, Date beginnBis,
                        Date endeVon, Date endeBis,
                        Integer wahrscheinlichkeitVon, Integer wahrscheinlichkeitBis) {
        this.mitarbeiterVertriebId = mitarbeiterVertriebId;
        this.mitarbeiterId = mitarbeiterId;
        this.mitarbeiterStatus = mitarbeiterStatus;
        this.einsatzStatus = einsatzStatus;
        this.beginnVon = beginnVon;
        this.beginnBis = beginnBis;
        this.endeVon = endeVon;
        this.endeBis = endeBis;
        this.wahrscheinlichkeitVon = wahrscheinlichkeitVon;
        this.wahrscheinlichkeitBis = wahrscheinlichkeitBis;
    }
}
