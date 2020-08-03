package com.cegeka.employeeplanning;

import com.cegeka.employeeplanning.data.Einsatz;
import com.cegeka.employeeplanning.data.EinsatzRepository;
import com.cegeka.employeeplanning.data.Mitarbeiter;
import com.cegeka.employeeplanning.data.MitarbeiterRepository;
import com.cegeka.employeeplanning.data.enums.Enums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeplanningController {
    @Autowired
    private MitarbeiterRepository mitarbeiterRepository;
    @Autowired
    private EinsatzRepository einsatzRepository;

    @GetMapping("/listMitarbeiter")
    public Iterable<Mitarbeiter> getMitarbeiter() {
        return mitarbeiterRepository.findAll();
    }

    @GetMapping("/listEinsaetze")
    public Iterable<Einsatz> getEinsaetze() {
        return einsatzRepository.findAll();
    }

    @GetMapping("/findEinsaetzeByEinsatzStatus")
    public Iterable<Einsatz> findEinsaetzeByEinsatzStatus(@RequestParam("status") Enums.EinsatzStatus status) {
        return einsatzRepository.findEinsaetzeByEinsatzStatus(status);
    }
}
