package com.cegeka.employeeplanning.data;

import com.cegeka.employeeplanning.data.enums.Enums;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Getter
@Setter
public class Einsatz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Autowired
    private Mitarbeiter mitarbeiter;

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
