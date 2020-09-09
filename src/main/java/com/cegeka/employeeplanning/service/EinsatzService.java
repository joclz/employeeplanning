package com.cegeka.employeeplanning.service;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import com.cegeka.employeeplanning.data.Einsatz;
import com.cegeka.employeeplanning.data.Mitarbeiter;
import com.cegeka.employeeplanning.data.MitarbeiterVertrieb;
import com.cegeka.employeeplanning.data.dto.EinsatzDTO;
import com.cegeka.employeeplanning.data.dto.EinsatzSucheDTO;
import com.cegeka.employeeplanning.data.enums.Enums.EinsatzStatus;
import com.cegeka.employeeplanning.data.util.EinsatzSuche;
import com.cegeka.employeeplanning.exceptions.NoSuchEinsatzException;
import com.cegeka.employeeplanning.repositories.EinsatzRepository;
import com.cegeka.employeeplanning.repositories.MitarbeiterRepository;
import com.cegeka.employeeplanning.repositories.MitarbeiterVertriebRepository;
import com.cegeka.employeeplanning.util.EmployeeplanningUtil;

import org.assertj.core.util.VisibleForTesting;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EinsatzService {
    @Autowired
    private EinsatzRepository einsatzRepository;

    @Autowired
    private MitarbeiterVertriebRepository mitarbeiterVertriebRepository;

    @Autowired
    private MitarbeiterRepository mitarbeiterRepository;

    public Einsatz getEinsatzById(Integer id)
    {
        return checkEntityExist(id);
    }

    public void deleteById(Integer id)
    {
        Einsatz einsatz = checkEntityExist(id);
        einsatzRepository.delete(einsatz);
    }

    /**
     * Konvertiert das EinsatzDTO zu einem Einsatz.
     * Die meisten Felder werden hierbei automatisch gemapt.
     * Die Daten für Mitarbeiter und MitarbeiterVertrieb müssen an hand der Id ermittelt werden.
     */
    public Einsatz convertToEntity(EinsatzDTO einsatzDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Einsatz einsatz = modelMapper.map(einsatzDTO, Einsatz.class);
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
        return einsatz;
    }

    public EinsatzSuche convertToEntity(EinsatzSucheDTO einsatzSucheDTO) {
        ModelMapper modelMapper = new ModelMapper();
        EinsatzSuche einsatzSuche = modelMapper.map(einsatzSucheDTO, EinsatzSuche.class);
        return einsatzSuche;
    }

    /**
     * Vor der Methode 'save' des Repositories, wird zusätzlich noch die Methode 'calcEinsatzWerte' aufgerufen,
     * um die berechneten Attribute des Einsatzes zu berechnen.
     */
    public <S extends Einsatz> void save(S einsatz) {
        einsatz = calcEinsatzWerte(einsatz);
        einsatzRepository.save(einsatz);
    }

    public <S extends Einsatz> void update(S einsatz)
    {
        checkEntityExist(einsatz.getId());
        save(einsatz);
    }

    @VisibleForTesting
    public <S extends Einsatz> S calcEinsatzWerte(S einsatz) {
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

    /**
     * Liefert alle Einsätze für einen Vertriebsmitarbeiter.
     * Hierbei wird das Datum nicht berücksichtigt.
     */
    public Iterable<Einsatz> findEinsaetzeByMitarbeiterVertriebId(Integer id) {
        Optional<MitarbeiterVertrieb> mitarbeiterVertrieb;
        try {
            mitarbeiterVertrieb = mitarbeiterVertriebRepository.findById(id);
        } catch (Exception ex) {
            return Collections.emptyList();
        }
        return einsatzRepository.findEinsaetzeByMitarbeiterVertrieb(mitarbeiterVertrieb);
    }

    /**
     * Liefert alle Einsätze für einen Mitarbeiter.
     * Hierbei wird das Datum nicht berücksichtigt.
     */
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

        return einsatzRepository.findEinsaetzeBySuchkriterien(
                einsatzSuche.getMitarbeiterVertriebId(),
                einsatzSuche.getMitarbeiterId(),
                einsatzSuche.getMitarbeiterStatus(),
                einsatzSuche.getEinsatzStatus(),
                einsatzSuche.getBeginnVon(),
                einsatzSuche.getBeginnBis(),
                einsatzSuche.getEndeVon(),
                einsatzSuche.getEndeBis());
    }

    /**
     * Frage: Welchen Umsatz bzw. Deckungsbeitrag haben wir (aktueller Monat)
     * Es werden die Deckungsbeiträge aller Beauftragten Einsätze, die aktuell sind, addiert.
     */
    public double getDeckungsbeitrag() {
        Date today = EmployeeplanningUtil.today();
        return getDeckungsbeitrag(today);
    }

    @VisibleForTesting
    public double getDeckungsbeitrag(Date today) {
        EinsatzSuche einsatzSuche = new EinsatzSuche(null, null, null, EinsatzStatus.BEAUFTRAGT, null, today, today, null);
        Iterable<Einsatz> einsaetze = findEinsaetzeBySuchkriterien(einsatzSuche);
        double summeDeckungsbeitrag = 0.;
        for (Einsatz einsatz : einsaetze) {
            summeDeckungsbeitrag += einsatz.getDeckungsbeitrag();
        }
        return summeDeckungsbeitrag;
    }

    private Einsatz checkEntityExist(Integer id)
    {
        Optional<Einsatz> einsatz = einsatzRepository.findById(id);
        if (!einsatz.isPresent())
        {
            throw new NoSuchEinsatzException();
        }
        return einsatz.get();
    }

}
