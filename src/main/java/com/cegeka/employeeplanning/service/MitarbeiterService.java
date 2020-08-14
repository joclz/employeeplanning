package com.cegeka.employeeplanning.service;

import com.cegeka.employeeplanning.data.Einsatz;
import com.cegeka.employeeplanning.data.EinsatzRepository;
import com.cegeka.employeeplanning.data.enums.Enums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MitarbeiterService {
    @Autowired
    private EinsatzService einsatzService;
    @Autowired
    private EinsatzRepository einsatzRepository;

    /**
     * Frage: Wie lange ist Mitarbeiter noch im Einsatz?
     * Gibt das letzte Ende Datum aller Einsätze für einen bestimmten Mitarbeiter zurück.
     * Hierbei werden nur Einsätze mit dem Status = BEAUFTRAGT berücksichtigt.
     */
    public Date getLastEndDateForMitarbeiter(Integer mitarbeiterId) {
        Set<Integer> einsatzIds = new HashSet<>();
        einsatzService.findEinsaetzeByMitarbeiterId(mitarbeiterId).forEach(id -> einsatzIds.add(id.getId()));

        Set<Integer> einsatzIdsStatus = new HashSet<>();
        einsatzRepository.findEinsaetzeByEinsatzStatus(Enums.EinsatzStatus.BEAUFTRAGT).forEach(id -> einsatzIdsStatus.add(id.getId()));
        einsatzIds.retainAll(einsatzIdsStatus);

        Iterable<Einsatz> einsaetzeByMitarbeiterId = einsatzRepository.findAllById(einsatzIds);

        Date lastEndDate = null;
        List<Date> listEndDate = new ArrayList<Date>() {};
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
        einsatzRepository.findEinsaetzeByEinsatzStatus(Enums.EinsatzStatus.ANGEBOTEN).forEach(id -> einsatzIdsStatus.add(id.getId()));
        einsatzIds.retainAll(einsatzIdsStatus);

        Iterable<Einsatz> einsaetzeByMitarbeiterId = einsatzRepository.findAllById(einsatzIds);

        int biggestChance = 0;
        List<Integer> listChance = new ArrayList<Integer>() {};
        einsaetzeByMitarbeiterId.forEach(it -> listChance.add(it.getWahrscheinlichkeit()));
        listChance.sort(Collections.reverseOrder());
        if (!listChance.isEmpty()) {
            biggestChance = listChance.get(0);
        }
        return biggestChance;
    }
}
