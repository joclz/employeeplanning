package com.cegeka.employeeplanning.data;

import org.assertj.core.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
}
