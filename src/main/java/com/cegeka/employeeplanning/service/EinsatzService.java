package com.cegeka.employeeplanning.service;

import com.cegeka.employeeplanning.data.*;
import com.cegeka.employeeplanning.data.enums.Enums;
import org.assertj.core.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

import static com.cegeka.employeeplanning.util.EmployeeplanningUtil.formateTodayDateToString;
import static com.cegeka.employeeplanning.util.EmployeeplanningUtil.parseDate;

@Service
public class EinsatzService {
    @Autowired
    private EinsatzRepository einsatzRepository;

    @Autowired
    private MitarbeiterVertriebRepository mitarbeiterVertriebRepository;

    @Autowired
    private MitarbeiterRepository mitarbeiterRepository;

    /**
     * Vor der Methode 'save' des Repositories, wird zusätzlich noch die Methode 'calcEinsatzWerte' aufgerufen,
     * um die berechneten Attribute des Einsatzes zu berechnen.
     */
    public <S extends Einsatz> void save(S einsatz) {
        einsatz = calcEinsatzWerte(einsatz);
        einsatzRepository.save(einsatz);
    }

    @VisibleForTesting
    public <S extends Einsatz> S calcEinsatzWerte(S einsatz) {
        Integer vertriebMitarbeiterId = einsatz.getMitarbeiterVertrieb().getId();
        Integer mitarbeiterId = einsatz.getMitarbeiter().getId();
        if (vertriebMitarbeiterId != null) {
            Optional<MitarbeiterVertrieb> maVertriebId = mitarbeiterVertriebRepository.findById(vertriebMitarbeiterId);
            einsatz.setMitarbeiterVertrieb(maVertriebId.orElse(null));
        }
        if (mitarbeiterId != null) {
            Optional<Mitarbeiter> maId = mitarbeiterRepository.findById(mitarbeiterId);
            einsatz.setMitarbeiter(maId.orElse(null));
        }

        double stundensatzEK = 0.;
        if (einsatz.getMitarbeiter() != null) {
            stundensatzEK = einsatz.getMitarbeiter().getStundensatzEK();
        }
        einsatz.setDeckungsbeitrag(einsatz.getStundensatzVK() - einsatz.getZusatzkostenReise() - stundensatzEK);
        if (einsatz.getStundensatzVK() != 0) {
            einsatz.setMarge(einsatz.getDeckungsbeitrag() / einsatz.getStundensatzVK());
        }
        return einsatz;
    }

    public Iterable<Einsatz> findEinsaetzeByMitarbeiterVertriebId(Integer id) {
        Optional<MitarbeiterVertrieb> mitarbeiterVertrieb;
        try {
            mitarbeiterVertrieb = mitarbeiterVertriebRepository.findById(id);
        } catch (Exception ex) {
            return Collections.emptyList();
        }
        return einsatzRepository.findEinsaetzeByMitarbeiterVertrieb(mitarbeiterVertrieb);
    }

    public Iterable<Einsatz> findEinsaetzeByMitarbeiterId(Integer id) {
        Optional<Mitarbeiter> mitarbeiter;
        try {
            mitarbeiter = mitarbeiterRepository.findById(id);
        } catch (Exception ex) {
            return Collections.emptyList();
        }
        return einsatzRepository.findEinsaetzeByMitarbeiter(mitarbeiter);
    }

    /**
     * Mit dieser Methode kann gezielt nach Einsätzen gesucht werden.
     * Die Suche wird über die Datenstruktur im Parameter 'EinsatzSuche' gesteuert.
     * Sind die einzelnen Attribute in der Datenstruktur gleich null bzw. leer, wird die Suche nicht eingeschränkt.
     * Sind die Attribute jedoch gefüllt, kann die Suche der Einsätze für bestimmte Vertriebsmitarbeiter, Mitarbeiter,
     * Status, BeginnDatum von bis und EndeDatum von bis erweitert werden.
     */
    public Iterable<Einsatz> findEinsaetzeBySuchkriterien(EinsatzSuche einsatzSuche) {
        Set<Integer> einsatzIds = new HashSet<>();
        einsatzRepository.findAll().forEach(id -> einsatzIds.add(id.getId()));

        if (einsatzSuche.getMitarbeiterVertriebId() != null) {
            Set<Integer> einsatzIdsMaV = new HashSet<>();
            findEinsaetzeByMitarbeiterVertriebId(einsatzSuche.getMitarbeiterVertriebId()).forEach(id -> einsatzIdsMaV.add(id.getId()));
            einsatzIds.retainAll(einsatzIdsMaV);
        }
        if (einsatzSuche.getMitarbeiterId() != null) {
            Set<Integer> einsatzIdsMa = new HashSet<>();
            findEinsaetzeByMitarbeiterId(einsatzSuche.getMitarbeiterId()).forEach(id -> einsatzIdsMa.add(id.getId()));
            einsatzIds.retainAll(einsatzIdsMa);
        }
        if (einsatzSuche.getMitarbeiterStatus() != null && !einsatzSuche.getMitarbeiterStatus().isEmpty()) {
            Set<Integer> einsatzIdsMaStatus = new HashSet<>();
            einsatzRepository.findEinsaetzeByMitarbeiter_MitarbeiterStatus(Enums.MitarbeiterStatus.valueOf(
                    einsatzSuche.getMitarbeiterStatus())).forEach(id -> einsatzIdsMaStatus.add(id.getId()));
            einsatzIds.retainAll(einsatzIdsMaStatus);
        }
        if (einsatzSuche.getEinsatzStatus() != null && !einsatzSuche.getEinsatzStatus().isEmpty()) {
            Set<Integer> einsatzIdsStatus = new HashSet<>();
            einsatzRepository.findEinsaetzeByEinsatzStatus(Enums.EinsatzStatus.valueOf(
                    einsatzSuche.getEinsatzStatus())).forEach(id -> einsatzIdsStatus.add(id.getId()));
            einsatzIds.retainAll(einsatzIdsStatus);
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        if (einsatzSuche.getBeginnVon() != null && !einsatzSuche.getBeginnVon().isEmpty()) {
            Set<Integer> einsatzIdsBeginnVon = new HashSet<>();
            Date beginnDateVon = parseDate(einsatzSuche.getBeginnVon(), formatter);
            einsatzRepository.findEinsaetzeByBeginnGreaterThanEqual(beginnDateVon).forEach(id -> einsatzIdsBeginnVon.add(id.getId()));
            einsatzIds.retainAll(einsatzIdsBeginnVon);
        }
        if (einsatzSuche.getBeginnBis() != null && !einsatzSuche.getBeginnBis().isEmpty()) {
            Set<Integer> einsatzIdsBeginnBis = new HashSet<>();
            Date beginnDateBis = parseDate(einsatzSuche.getBeginnBis(), formatter);
            einsatzRepository.findEinsaetzeByBeginnLessThanEqual(beginnDateBis).forEach(id -> einsatzIdsBeginnBis.add(id.getId()));
            einsatzIds.retainAll(einsatzIdsBeginnBis);
        }
        if (einsatzSuche.getEndeVon() != null && !einsatzSuche.getEndeVon().isEmpty()) {
            Set<Integer> einsatzIdsEndeVon = new HashSet<>();
            Date endeDateVon = parseDate(einsatzSuche.getEndeVon(), formatter);
            einsatzRepository.findEinsaetzeByEndeGreaterThanEqual(endeDateVon).forEach(id -> einsatzIdsEndeVon.add(id.getId()));
            einsatzIds.retainAll(einsatzIdsEndeVon);
        }
        if (einsatzSuche.getEndeBis() != null && !einsatzSuche.getEndeBis().isEmpty()) {
            Set<Integer> einsatzIdsEndeBis = new HashSet<>();
            Date endeDateBis = parseDate(einsatzSuche.getEndeBis(), formatter);
            einsatzRepository.findEinsaetzeByEndeLessThanEqual(endeDateBis).forEach(id -> einsatzIdsEndeBis.add(id.getId()));
            einsatzIds.retainAll(einsatzIdsEndeBis);
        }

        return einsatzRepository.findAllById(einsatzIds);
    }

    /**
     * Frage: Welchen UMsatz bzw. Deckungsbeitrag haben wir (aktueller Monat)
     * Es werden die Deckungsbeiträge aller Beauftragten Einsätze, die aktuell sind, addiert.
     */
    public double getDeckungsbeitrag() {
        String todayString = formateTodayDateToString();
        return getDeckungsbeitrag(todayString);
    }

    @VisibleForTesting
    public double getDeckungsbeitrag(String todayString) {
        EinsatzSuche einsatzSuche = new EinsatzSuche(null, null, null, "BEAUFTRAGT", null, todayString, todayString, null);
        Iterable<Einsatz> einsaetze = findEinsaetzeBySuchkriterien(einsatzSuche);
        double summeDeckungsbeitrag = 0.;
        for (Einsatz einsatz : einsaetze) {
            summeDeckungsbeitrag += einsatz.getDeckungsbeitrag();
        }
        return summeDeckungsbeitrag;
    }
}
