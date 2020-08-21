package com.cegeka.employeeplanning.repositories;

import java.util.Date;
import java.util.Optional;

import com.cegeka.employeeplanning.data.Einsatz;
import com.cegeka.employeeplanning.data.Mitarbeiter;
import com.cegeka.employeeplanning.data.MitarbeiterVertrieb;
import com.cegeka.employeeplanning.data.enums.Enums;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface EinsatzRepository extends CrudRepository<Einsatz, Integer> {

    Iterable<Einsatz> findEinsaetzeByEinsatzStatus(Enums.EinsatzStatus status);

    Iterable<Einsatz> findEinsaetzeByMitarbeiterVertrieb(Optional<MitarbeiterVertrieb> mitarbeiterVertrieb);

    Iterable<Einsatz> findEinsaetzeByMitarbeiter(Optional<Mitarbeiter> mitarbeiter);

    Iterable<Einsatz> findEinsaetzeByBeginnGreaterThanEqual(Date beginn);

    Iterable<Einsatz> findEinsaetzeByBeginnLessThanEqual(Date beginn);

    Iterable<Einsatz> findEinsaetzeByEndeGreaterThanEqual(Date ende);

    Iterable<Einsatz> findEinsaetzeByEndeLessThanEqual(Date ende);

    Iterable<Einsatz> findEinsaetzeByMitarbeiter_MitarbeiterStatus(Enums.MitarbeiterStatus status);

    @Query(
            value = "SELECT e FROM Einsatz e "
            + "WHERE (e.mitarbeiterVertrieb.id = :mitarbeitervertriebId or :mitarbeitervertriebId is null)"
            + "  AND (e.mitarbeiter.id = :mitarbeiterId or :mitarbeiterId is null)"
            + "  AND (e.mitarbeiter.mitarbeiterStatus = :mitarbeiterStatus or :mitarbeiterStatus is null)"
            + "  AND (e.einsatzStatus = :einsatzStatus or :einsatzStatus is null)"
            + "  AND (e.beginn >= :beginnVon or :beginnVon is null)"
            + "  AND (e.beginn <= :beginnBis or :beginnBis is null)"
            + "  AND (e.ende >= :endeVon or :endeVon is null)"
            + "  AND (e.ende <= :endeBis or :endeBis is null)"
    )
    Iterable<Einsatz> findEinsaetzeBySuchkriterien(
            @Param("mitarbeitervertriebId") Integer mitarbeitervertriebId,
            @Param("mitarbeiterId") Integer mitarbeiterId,
            @Param("mitarbeiterStatus") Enums.MitarbeiterStatus mitarbeiterStatus,
            @Param("einsatzStatus") Enums.EinsatzStatus einsatzStatus,
            @Param("beginnVon") Date beginnVon,
            @Param("beginnBis") Date beginnBis,
            @Param("endeVon") Date endeVon,
            @Param("endeBis") Date endeBis);
}
