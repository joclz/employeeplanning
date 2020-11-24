package com.cegeka.employeeplanning.data;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.cegeka.employeeplanning.data.enums.Enums;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Einsatz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Mitarbeiter mitarbeiter;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MitarbeiterVertrieb mitarbeiterVertrieb;

    @Enumerated(EnumType.STRING)
    private Enums.EinsatzStatus einsatzStatus;
//    @Temporal(TemporalType.DATE)
    private Date beginn;
//    @Temporal(TemporalType.DATE)
    private Date ende;
    private int wahrscheinlichkeit;
    private double zusatzkostenReise;
    private double stundensatzVK;
    private String projektnummerNettime;
    private String beauftragungsnummer;

    private double deckungsbeitrag;
    private double marge;
}
