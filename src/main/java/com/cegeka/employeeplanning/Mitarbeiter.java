package com.cegeka.employeeplanning;

import com.cegeka.employeeplanning.enums.Enums;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Mitarbeiter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Enums.MitarbeiterStatus mitarbeiterStatus;
    private double stundensatzEK;
    private String name;
    private String vorname;
    private Enums.MitarbeiterUnit mitarbeiterUnit;
}
