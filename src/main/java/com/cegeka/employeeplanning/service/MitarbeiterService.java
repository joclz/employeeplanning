package com.cegeka.employeeplanning.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.cegeka.employeeplanning.data.Einsatz;
import com.cegeka.employeeplanning.data.Mitarbeiter;
import com.cegeka.employeeplanning.data.enums.Enums.EinsatzStatus;
import com.cegeka.employeeplanning.data.enums.Enums.MitarbeiterStatus;
import com.cegeka.employeeplanning.data.util.EinsatzSuche;
import com.cegeka.employeeplanning.repositories.EinsatzRepository;
import com.cegeka.employeeplanning.repositories.MitarbeiterRepository;
import com.cegeka.employeeplanning.util.EmployeeplanningUtil;

import com.cegeka.employeeplanning.data.dto.MitarbeiterDTO;
import org.assertj.core.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MitarbeiterService extends EmployeeplanningUtil {
    @Autowired
    private EinsatzService einsatzService;
    @Autowired
    private EinsatzRepository einsatzRepository;
    @Autowired
    private MitarbeiterRepository mitarbeiterRepository;

    /**
     * Frage: Wie lange ist Mitarbeiter noch im Einsatz?
     * Gibt das letzte Ende Datum aller Einsätze für einen bestimmten Mitarbeiter zurück.
     * Hierbei werden nur Einsätze mit dem Status = BEAUFTRAGT berücksichtigt.
     */
    public Date getLastEndDateForMitarbeiter(Integer mitarbeiterId) {
        Set<Integer> einsatzIds = new HashSet<>();
        einsatzService.findEinsaetzeByMitarbeiterId(mitarbeiterId).forEach(id -> einsatzIds.add(id.getId()));

        Set<Integer> einsatzIdsStatus = new HashSet<>();
        einsatzRepository.findEinsaetzeByEinsatzStatus(EinsatzStatus.BEAUFTRAGT).forEach(id -> einsatzIdsStatus.add(id.getId()));
        einsatzIds.retainAll(einsatzIdsStatus);

        Iterable<Einsatz> einsaetzeByMitarbeiterId = einsatzRepository.findAllById(einsatzIds);

        Date lastEndDate = null;
        List<Date> listEndDate = new ArrayList<Date>() {
        };
        einsaetzeByMitarbeiterId.forEach(it -> listEndDate.add(it.getEnde()));
        listEndDate.sort(Collections.reverseOrder());
        if (!listEndDate.isEmpty()) {
            lastEndDate = listEndDate.get(0);
        }
        return lastEndDate;
    }

    /**
     * Frage: Wie hoch ist die Chance für Verlängerung?
     * Gibt den Einsatz mit der höchsten Wahrscheinlichkeit für einen bestimmten Mitarbeiter zurück.
     * Hierbei werden nur Einsätze mit dem Status = ANGEBOTEN berücksichtigt.
     */
    public Integer getChanceForMitarbeiter(Integer mitarbeiterId) {
        Set<Integer> einsatzIds = new HashSet<>();
        einsatzService.findEinsaetzeByMitarbeiterId(mitarbeiterId).forEach(id -> einsatzIds.add(id.getId()));

        Set<Integer> einsatzIdsStatus = new HashSet<>();
        einsatzRepository.findEinsaetzeByEinsatzStatus(EinsatzStatus.ANGEBOTEN).forEach(id -> einsatzIdsStatus.add(id.getId()));
        einsatzIds.retainAll(einsatzIdsStatus);

        Iterable<Einsatz> einsaetzeByMitarbeiterId = einsatzRepository.findAllById(einsatzIds);

        int biggestChance = 0;
        List<Integer> listChance = new ArrayList<Integer>() {
        };
        einsaetzeByMitarbeiterId.forEach(it -> listChance.add(it.getWahrscheinlichkeit()));
        listChance.sort(Collections.reverseOrder());
        if (!listChance.isEmpty()) {
            biggestChance = listChance.get(0);
        }
        return biggestChance;
    }

    /**
     * Frage: Welche Mitarbeiter sitzen auf der Bank?
     * Gibt zurück welche Mitarbeiter aktuell keinen Einsatz mit dem Status "Beauftrag" haben.
     * Das Ende des Einsatzes muss dabei in der Zukunft liegen (bzw. nach dem Datum "todayString" falls die Methode
     * mit Parameter aufgerufen wird).
     */
    public Iterable<Mitarbeiter> getMitarbeiterBank() {
        Date today = today();
        return getMitarbeiterBank(today);
    }

    /**
     * Frage: Welche internen Mitarbeiter sind aktuell nicht im Einsatz bzw. sitzen auf der Bank?
     * Gibt zurück welche internen Mitarbeiter aktuell keinen Einsatz mit dem Status "Beauftrag" haben.
     * Das Ende des Einsatzes muss dabei in der Zukunft liegen (bzw. nach dem Datum "todayString" falls die Methode
     * mit Parameter aufgerufen wird).
     */
    public Iterable<Mitarbeiter> getMitarbeiterInternBank() {
        Date today = today();
        return getMitarbeiterBank(true, today);
    }

    @VisibleForTesting
    public Iterable<Mitarbeiter> getMitarbeiterBank(Date today) {
        return getMitarbeiterBank(false, today);
    }

    @VisibleForTesting
    public Iterable<Mitarbeiter> getMitarbeiterBank(boolean intern, Date today) {
        EinsatzSuche einsatzSuche = new EinsatzSuche(null, null, null, EinsatzStatus.BEAUFTRAGT, null, null, today, null);
        Iterable<Einsatz> einsaetze = einsatzService.findEinsaetzeBySuchkriterien(einsatzSuche);

        Set<Integer> mitarbeiterIdBeauftragtSet = new HashSet<>();
        einsaetze.forEach(id -> mitarbeiterIdBeauftragtSet.add(id.getMitarbeiter().getId()));

        Set<Integer> mitarbeiterIdSet = new HashSet<>();
        Iterable<Mitarbeiter> mitarbeiterRepositoryAll;

        if (intern) {
            mitarbeiterRepositoryAll = mitarbeiterRepository.findMitarbeiterByMitarbeiterStatus(MitarbeiterStatus.ANGESTELLT);
        } else {
            mitarbeiterRepositoryAll = mitarbeiterRepository.findAll();
        }
        mitarbeiterRepositoryAll.forEach(id -> mitarbeiterIdSet.add(id.getId()));

        mitarbeiterIdSet.removeAll(mitarbeiterIdBeauftragtSet);
        return mitarbeiterRepository.findAllById(mitarbeiterIdSet);
    }

    /**
     * Frage: Wieviele Mitarbeiter bzw. Subunternehmer sind im Einsatz?
     * Gibt zurück welche Mitarbeiter (Intern oder Subunternehmer) aktuell einen Einsatz mit dem Status "Beauftrag" haben.
     * Das Ende des Einsatzes muss dabei in der Zukunft liegen (bzw. nach dem Datum "todayString" falls die Methode
     * mit Parameter aufgerufen wird).
     */
    public int countMitarbeiterImEinsatz(MitarbeiterStatus mitarbeiterStatus) {
        Date today = today();
        return countMitarbeiterImEinsatz(mitarbeiterStatus, today);
    }

    @VisibleForTesting
    public int countMitarbeiterImEinsatz(MitarbeiterStatus mitarbeiterStatus, Date today) {
        EinsatzSuche einsatzSuche = new EinsatzSuche(null, null, mitarbeiterStatus,
                EinsatzStatus.BEAUFTRAGT, null, today, today, null);
        Iterable<Einsatz> einsaetze = einsatzService.findEinsaetzeBySuchkriterien(einsatzSuche);

        Set<Integer> mitarbeiterIdSet = new HashSet<>();
        einsaetze.forEach(id -> mitarbeiterIdSet.add(id.getId()));
        return mitarbeiterIdSet.size();
    }

    public List<MitarbeiterDTO> getMitarbeiterListOrderByName() {
        Iterable<Mitarbeiter> mitarbeiterOrderByName = mitarbeiterRepository.findMitarbeiterByOrderByName();
        List<MitarbeiterDTO> mitarbeiterDTOList = new ArrayList<MitarbeiterDTO>();

        for (Mitarbeiter mitarbeiter : mitarbeiterOrderByName) {
            String name = mitarbeiter.getName() + ", " + mitarbeiter.getVorname();
            MitarbeiterDTO mitarbeiterDTO = new MitarbeiterDTO(name, mitarbeiter.getId().toString());
            mitarbeiterDTOList.add(mitarbeiterDTO);
        }
        return mitarbeiterDTOList;
    }
}
