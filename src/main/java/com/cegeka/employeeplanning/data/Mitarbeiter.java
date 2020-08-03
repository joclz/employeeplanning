package com.cegeka.employeeplanning.data;

import com.cegeka.employeeplanning.data.enums.Enums;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Mitarbeiter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private Enums.MitarbeiterStatus mitarbeiterStatus;
    private double stundensatzEK;
    private String name;
    private String vorname;
    private Enums.MitarbeiterUnit mitarbeiterUnit;
}
