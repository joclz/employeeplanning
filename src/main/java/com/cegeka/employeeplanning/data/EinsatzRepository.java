package com.cegeka.employeeplanning.data;

import com.cegeka.employeeplanning.data.enums.Enums;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface EinsatzRepository extends CrudRepository<Einsatz, Integer> {

    Iterable<Einsatz> findEinsaetzeByEinsatzStatus(Enums.EinsatzStatus status);
    Iterable<Einsatz> findEinsaetzeByMitarbeiterVertrieb(Optional<MitarbeiterVertrieb> mitarbeiterVertrieb);
}
