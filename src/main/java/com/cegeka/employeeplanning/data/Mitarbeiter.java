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

    //Todo - Es ist mir nicht gelungen die Enum-Werte numerisch zu definieren und korrekt zu initialisieren.
    //Statt dessen habe ich die Enum-Werte als Notlösung als Strings definiert.
    @Enumerated(EnumType.STRING)
    private Enums.MitarbeiterStatus mitarbeiterStatus;
    private double stundensatzEK;
    private String name;
    private String vorname;
    @Enumerated(EnumType.STRING)
    private Enums.MitarbeiterUnit mitarbeiterUnit;
}
