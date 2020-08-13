package com.cegeka.employeeplanning.data;

import com.cegeka.employeeplanning.data.enums.Enums;
import org.assertj.core.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class EinsatzService {
    @Autowired
    private EinsatzRepository einsatzRepository;

    @Autowired
    private MitarbeiterVertriebRepository mitarbeiterVertriebRepository;

    @Autowired
    private MitarbeiterRepository mitarbeiterRepository;

    public <S extends Einsatz> S save(S einsatz) {
        einsatz = calcEinsatzWerte(einsatz);
        return einsatzRepository.save(einsatz);
    }

    @VisibleForTesting
    public <S extends Einsatz> S calcEinsatzWerte(S einsatz) {
        //Todo - Mitarbeiter und Mitarbeitervertrieb setzen
        Integer vertriebMitarbeiterId = einsatz.getMitarbeiterVertrieb().getId();
        Integer mitarbeiterId = einsatz.getMitarbeiter().getId();
        if (vertriebMitarbeiterId != null) {
            Optional<MitarbeiterVertrieb> byId = mitarbeiterVertriebRepository.findById(vertriebMitarbeiterId);
            einsatz.setMitarbeiterVertrieb(byId.get());
        }
        if (mitarbeiterId != null) {
            Optional<Mitarbeiter> byId1 = mitarbeiterRepository.findById(mitarbeiterId);
            einsatz.setMitarbeiter(byId1.get());
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
        if (einsatzSuche.getEinsatzStatus() != null && !einsatzSuche.getEinsatzStatus().isEmpty()) {
            Set<Integer> einsatzIdsStatus = new HashSet<>();
            einsatzRepository.findEinsaetzeByEinsatzStatus(Enums.EinsatzStatus.valueOf(
                    einsatzSuche.getEinsatzStatus())).forEach(id -> einsatzIdsStatus.add(id.getId()));
            einsatzIds.retainAll(einsatzIdsStatus);
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        if (einsatzSuche.getBeginn() != null && !einsatzSuche.getBeginn().isEmpty()) {
            Set<Integer> einsatzIdsBeginn = new HashSet<>();
            Date beginnDate = null;
            beginnDate = parseDate(einsatzSuche.getBeginn(), formatter, beginnDate);
            einsatzRepository.findEinsaetzeByBeginnGreaterThanEqual(beginnDate).forEach(id -> einsatzIdsBeginn.add(id.getId()));
            einsatzIds.retainAll(einsatzIdsBeginn);
        }
        if (einsatzSuche.getEnde() != null && !einsatzSuche.getEnde().isEmpty()) {
            Set<Integer> einsatzIdsEnde = new HashSet<>();
            Date beginnEnde = null;
            beginnEnde = parseDate(einsatzSuche.getEnde(), formatter, beginnEnde);
            einsatzRepository.findEinsaetzeByEndeIsLessThanEqual(beginnEnde).forEach(id -> einsatzIdsEnde.add(id.getId()));
            einsatzIds.retainAll(einsatzIdsEnde);
        }

        return einsatzRepository.findAllById(einsatzIds);
    }

    private Date parseDate(String dateString, SimpleDateFormat formatter, Date date) {
        try {
            date = formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
