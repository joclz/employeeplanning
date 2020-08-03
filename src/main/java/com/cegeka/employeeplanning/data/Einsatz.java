package com.cegeka.employeeplanning.data;

import com.cegeka.employeeplanning.data.enums.Enums;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class Einsatz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Mitarbeiter mitarbeiter;
    @ManyToOne
    private MitarbeiterVertrieb mitarbeiterVertrieb;

    @Enumerated(EnumType.STRING)
    private Enums.EinsatzStatus einsatzStatus;
    private Date beginn;
    private Date ende;
    private int wahrscheinlichkeit;
    private double zusatzkostenReise;
    private double stundensatzVK;
    private String projektnummerNettime;
    private String beauftragungsnummer;

    private double deckungsbeitrag;
    private double marge;
}
