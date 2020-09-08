package com.cegeka.employeeplanning.data.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EinsatzSucheDTO {
    Integer mitarbeiterVertriebId;
    Integer mitarbeiterId;
    String mitarbeiterStatus;
    String einsatzStatus;
    Date beginnVon;
    Date beginnBis;
    Date endeVon;
    Date endeBis;

    public EinsatzSucheDTO(Integer mitarbeiterVertriebId,
                           Integer mitarbeiterId,
                           String mitarbeiterStatus,
                           String einsatzStatus,
                           Date beginnVon, Date beginnBis,
                           Date endeVon, Date endeBis)
    {
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
