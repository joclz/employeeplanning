package com.cegeka.employeeplanning.data;

import com.cegeka.employeeplanning.data.enums.Enums;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.Optional;

public interface EinsatzRepository extends CrudRepository<Einsatz, Integer> {

    Iterable<Einsatz> findEinsaetzeByEinsatzStatus(Enums.EinsatzStatus status);

    Iterable<Einsatz> findEinsaetzeByMitarbeiterVertrieb(Optional<MitarbeiterVertrieb> mitarbeiterVertrieb);

    Iterable<Einsatz> findEinsaetzeByMitarbeiter(Optional<Mitarbeiter> mitarbeiter);

    Iterable<Einsatz> findEinsaetzeByBeginnGreaterThanEqual(Date beginn);

    Iterable<Einsatz> findEinsaetzeByBeginnLessThanEqual(Date beginn);

    Iterable<Einsatz> findEinsaetzeByEndeGreaterThanEqual(Date ende);

    Iterable<Einsatz> findEinsaetzeByEndeLessThanEqual(Date ende);

    Iterable<Einsatz> findEinsaetzeByMitarbeiter_MitarbeiterStatus(Enums.MitarbeiterStatus status);
}
