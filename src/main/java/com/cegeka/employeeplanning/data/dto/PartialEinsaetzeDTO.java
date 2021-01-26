package com.cegeka.employeeplanning.data.dto;

import com.cegeka.employeeplanning.data.Einsatz;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PartialEinsaetzeDTO
{
    private Integer anzahl;
    private Iterable<Einsatz> einsaetze;

    public PartialEinsaetzeDTO(Integer anzahl, Iterable<Einsatz> einsaetze)
    {
        this.anzahl = anzahl;
        this.einsaetze = einsaetze;
    }
}
