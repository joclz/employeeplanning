package com.cegeka.employeeplanning.service;

import com.cegeka.employeeplanning.data.Einsatz;
import com.cegeka.employeeplanning.data.Mitarbeiter;
import com.cegeka.employeeplanning.data.dto.MitarbeiterDTO;
import com.cegeka.employeeplanning.data.enums.Enums.EinsatzStatus;
import com.cegeka.employeeplanning.data.enums.Enums.MitarbeiterStatus;
import com.cegeka.employeeplanning.data.util.EinsatzSuche;
import com.cegeka.employeeplanning.data.util.MitarbeiterEinsatzDate;
import com.cegeka.employeeplanning.exceptions.NoSuchMitarbeiterException;
import com.cegeka.employeeplanning.repositories.EinsatzRepository;
import com.cegeka.employeeplanning.repositories.MitarbeiterRepository;
import com.cegeka.employeeplanning.util.EmployeeplanningUtil;
import org.assertj.core.util.IterableUtil;
import org.assertj.core.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MitarbeiterService extends EmployeeplanningUtil {
    @Autowired
    private EinsatzService einsatzService;
    @Autowired
    private EinsatzRepository einsatzRepository;
    @Autowired
    private MitarbeiterRepository mitarbeiterRepository;

    public Mitarbeiter getMitarbeiterById(Integer id) {
        return checkEntityExist(id);
    }

    public void deleteById(Integer id) {
        Mitarbeiter mitarbeiter = checkEntityExist(id);
        mitarbeiterRepository.delete(mitarbeiter);
    }

    /**
     * Für ein Mitarbeiter-Update wird nicht nur der Mitarbeiter-Datensatz aktualisiert, sondern auch
     * alle Einsätze des jeweiligen Mitarbeiters neu berechnet (Deckungsbeitrag und Marge),
     * deren Ende-Datum noch nicht erreicht ist.
     *
     * @param mitarbeiter Mitarbeiter
     */
    public void update(Mitarbeiter mitarbeiter) {
        checkEntityExist(mitarbeiter.getId());
        mitarbeiterRepository.save(mitarbeiter);

        Date today = today();
        Iterable<Einsatz> einsaetzeOfMitarbeiter = einsatzRepository
                .findEinsaetzeByEndeGreaterThanEqualAndMitarbeiter(today, Optional.of(mitarbeiter));
        for (Einsatz einsatz : einsaetzeOfMitarbeiter) {
            einsatzService.save(einsatz);
        }
    }

    /**
     * Frage: Wie lange ist Mitarbeiter noch im Einsatz?
     * Gibt das letzte Ende Datum aller Einsätze für einen bestimmten Mitarbeiter zurück.
     * Hierbei werden nur Einsätze mit dem Status = BEAUFTRAGT berücksichtigt.
     */
    public Date getLastEndDateForMitarbeiter(Integer mitarbeiterId) {
        checkEntityExist(mitarbeiterId);

        Set<Integer> einsatzIds = new HashSet<>();
        einsatzService.findEinsaetzeByMitarbeiterId(mitarbeiterId).forEach(id -> einsatzIds.add(id.getId()));

        Set<Integer> einsatzIdsStatus = new HashSet<>();
        einsatzRepository.findEinsaetzeByEinsatzStatus(EinsatzStatus.BEAUFTRAGT).forEach(id ->
                einsatzIdsStatus.add(id.getId()));
        einsatzIds.retainAll(einsatzIdsStatus);

        Iterable<Einsatz> einsaetzeByMitarbeiterId = einsatzRepository.findAllById(einsatzIds);

        Date lastEndDate = null;
        List<Date> listEndDate = new ArrayList<>();
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
        checkEntityExist(mitarbeiterId);

        Set<Integer> einsatzIds = new HashSet<>();
        einsatzService.findEinsaetzeByMitarbeiterId(mitarbeiterId).forEach(id -> einsatzIds.add(id.getId()));

        Set<Integer> einsatzIdsStatus = new HashSet<>();
        einsatzRepository.findEinsaetzeByEinsatzStatus(EinsatzStatus.ANGEBOTEN).forEach(id ->
                einsatzIdsStatus.add(id.getId()));
        einsatzIds.retainAll(einsatzIdsStatus);

        Iterable<Einsatz> einsaetzeByMitarbeiterId = einsatzRepository.findAllById(einsatzIds);

        int biggestChance = 0;
        List<Integer> listChance = new ArrayList<>();
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
        EinsatzSuche einsatzSuche = new EinsatzSuche(null, null, null,
                EinsatzStatus.BEAUFTRAGT, null, null, today, null, null, null);
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

    /**
     * Es wird die die Anzahl der Mitarbeiter mit Einsatz (sowohl interne MA als auch Subunternehmer)
     * als auch die Anzahl der Mitarbeiter ohne Einsatz (auch sowohl interne MA als auch Subunternehmer) zurückgegeben.
     *
     * @param month Integer
     * @return MitarbeiterEinsatzDate
     */
    public MitarbeiterEinsatzDate getMitarbeiterEinsatzDate(Integer month) {
        MitarbeiterEinsatzDate mitarbeiterEinsatzDate = new MitarbeiterEinsatzDate();

        int allMaAngestellt = IterableUtil.sizeOf(mitarbeiterRepository
                .findMitarbeiterByMitarbeiterStatus(MitarbeiterStatus.ANGESTELLT));
        int allMaSubunternehmer = IterableUtil.sizeOf(mitarbeiterRepository
                .findMitarbeiterByMitarbeiterStatus(MitarbeiterStatus.SUBUNTERNEHMER));

        Date today = today();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(today);

        //int actualMonth = calendar.get(Calendar.MONTH);
        int newMonth = month;

        if (newMonth < Calendar.JANUARY) {
            newMonth = Calendar.JANUARY;
        } else if (newMonth > Calendar.DECEMBER) {
            newMonth = Calendar.DECEMBER;
        }
        calendar.set(Calendar.MONTH, newMonth);
        today = calendar.getTime();

        int maAngestelltImEinsatz = countMitarbeiterImEinsatz(MitarbeiterStatus.ANGESTELLT, today);
        mitarbeiterEinsatzDate.setMaIntEinsatz(maAngestelltImEinsatz);

        int maSubunternehmerImEinsatz = countMitarbeiterImEinsatz(MitarbeiterStatus.SUBUNTERNEHMER, today);
        mitarbeiterEinsatzDate.setMaExtEinsatz(maSubunternehmerImEinsatz);
        mitarbeiterEinsatzDate.setMaIntOhneEinsatz(allMaAngestellt - maAngestelltImEinsatz);
        mitarbeiterEinsatzDate.setMaExtOhneEinsatz(allMaSubunternehmer - maSubunternehmerImEinsatz);

        mitarbeiterEinsatzDate.setActualMonth(calendar.get(Calendar.MONTH));

        return mitarbeiterEinsatzDate;
    }

    @VisibleForTesting
    public int countMitarbeiterImEinsatz(MitarbeiterStatus mitarbeiterStatus, Date today) {
        EinsatzSuche einsatzSuche = new EinsatzSuche(null, null, mitarbeiterStatus,
                EinsatzStatus.BEAUFTRAGT, null, today, today, null, null, null);
        Iterable<Einsatz> einsaetze = einsatzService.findEinsaetzeBySuchkriterien(einsatzSuche);

        Set<Integer> mitarbeiterIdSet = new HashSet<>();
        einsaetze.forEach(id -> mitarbeiterIdSet.add(id.getId()));
        return mitarbeiterIdSet.size();
    }

    public List<MitarbeiterDTO> getMitarbeiterListOrderByName() {
        Iterable<Mitarbeiter> mitarbeiterOrderByName = mitarbeiterRepository.findMitarbeiterByOrderByName();
        List<MitarbeiterDTO> mitarbeiterDTOList = new ArrayList<>();

        for (Mitarbeiter mitarbeiter : mitarbeiterOrderByName) {
            String name = mitarbeiter.getName() + ", " + mitarbeiter.getVorname();
            MitarbeiterDTO mitarbeiterDTO = new MitarbeiterDTO(name, mitarbeiter.getId().toString());
            mitarbeiterDTOList.add(mitarbeiterDTO);
        }
        return mitarbeiterDTOList;
    }

    private Mitarbeiter checkEntityExist(Integer id) {
        Optional<Mitarbeiter> mitarbeiter = mitarbeiterRepository.findById(id);
        if (!mitarbeiter.isPresent()) {
            throw new NoSuchMitarbeiterException();
        }
        return mitarbeiter.get();
    }
}
