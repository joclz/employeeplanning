package com.cegeka.employeeplanning.data;

import com.cegeka.employeeplanning.data.enums.Enums;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class EinsatzDTO {
    private Integer mitarbeiterId;
    private Integer mitarbeiterVertriebId;

    private Enums.EinsatzStatus einsatzStatus;
    private Date beginn;
    private Date ende;
    private int wahrscheinlichkeit;
    private double zusatzkostenReise;
    private double stundensatzVK;
    private String projektnummerNettime;
    private String beauftragungsnummer;

    public EinsatzDTO(Integer mitarbeiterId, Integer mitarbeiterVertriebId,
                      Enums.EinsatzStatus einsatzStatus, Date beginn, Date ende,
                      int wahrscheinlichkeit, double zusatzkostenReise, double stundensatzVK,
                      String projektnummerNettime, String beauftragungsnummer) {
        this.mitarbeiterId = mitarbeiterId;
        this.mitarbeiterVertriebId = mitarbeiterVertriebId;
        this.einsatzStatus = einsatzStatus;
        this.beginn = beginn;
        this.ende = ende;
        this.wahrscheinlichkeit = wahrscheinlichkeit;
        this.zusatzkostenReise = zusatzkostenReise;
        this.stundensatzVK = stundensatzVK;
        this.projektnummerNettime = projektnummerNettime;
        this.beauftragungsnummer = beauftragungsnummer;
    }
}
