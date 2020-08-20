package com.cegeka.employeeplanning.controller;

import java.util.Date;

import com.cegeka.employeeplanning.data.Mitarbeiter;
import com.cegeka.employeeplanning.data.enums.Enums;
import com.cegeka.employeeplanning.data.enums.Enums.MitarbeiterStatus;
import com.cegeka.employeeplanning.repositories.MitarbeiterRepository;
import com.cegeka.employeeplanning.service.MitarbeiterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class EmployeeplanningMitarbeiterController {
    @Autowired
    private MitarbeiterRepository mitarbeiterRepository;
    @Autowired
    private MitarbeiterService mitarbeiterService;

    @PostMapping(path = "/addMitarbeiter")
    //Todo - Der Unit-Test benötigt offensichtlich @RequestBody. Beim Aufruf innerhalb der Anwendung stört dies aber.
    //public Mitarbeiter addMitarbeiter(@RequestBody Mitarbeiter mitarbeiter) {
    public Mitarbeiter addMitarbeiter(Mitarbeiter mitarbeiter) {
        mitarbeiterRepository.save(mitarbeiter);
        return mitarbeiter;
    }

    @PostMapping(path = "/deleteMitarbeiter")
    public void deleteMitarbeiter(@RequestParam("mitarbeiterId") int id) {
        mitarbeiterRepository.deleteById(id);
    }

    @PostMapping(path = "/updateMitarbeiter")
    public Mitarbeiter updateMitarbeiter(Mitarbeiter mitarbeiter) {
        mitarbeiterRepository.save(mitarbeiter);
        return mitarbeiter;
    }

    @PostMapping("/addMitarbeiterMitEinzelnenWerten")
    public Mitarbeiter addMitarbeiterMitEinzelnenWerten(@RequestParam String vorname, @RequestParam String name,
                                                        @RequestParam String status, @RequestParam String unit,
                                                        @RequestParam String stundensatzEK) {
        Mitarbeiter mitarbeiter = new Mitarbeiter();
        mitarbeiter.setVorname(vorname);
        mitarbeiter.setName(name);
        mitarbeiter.setMitarbeiterStatus(Enums.MitarbeiterStatus.valueOf(status));
        mitarbeiter.setMitarbeiterUnit(Enums.MitarbeiterUnit.valueOf(unit));
        mitarbeiter.setStundensatzEK(Double.parseDouble(stundensatzEK));
        mitarbeiterRepository.save(mitarbeiter);
        return mitarbeiter;
    }

    @GetMapping("/countMitarbeiterImEinsatz")
    public int countMitarbeiterImEinsatz(@RequestParam("mitarbeiterStatus") String status) {
        MitarbeiterStatus mitarbeiterStatus = Enums.MitarbeiterStatus.valueOf(status);
        return mitarbeiterService.countMitarbeiterImEinsatz(mitarbeiterStatus);
    }

    @GetMapping("/getChanceForMitarbeiter")
    public Integer getChanceForMitarbeiter(@RequestParam("mitarbeiterId") Integer id) {
        return mitarbeiterService.getChanceForMitarbeiter(id);
    }

    @GetMapping("/getLastEndDateForMitarbeiter")
    public Date getLastEndDateForMitarbeiter(@RequestParam("mitarbeiterId") Integer id) {
        return mitarbeiterService.getLastEndDateForMitarbeiter(id);
    }

    @GetMapping("/listMitarbeiter")
    public Iterable<Mitarbeiter> getMitarbeiter() {
        return mitarbeiterRepository.findAll();
    }

    @GetMapping("/listMitarbeiterBank")
    public Iterable<Mitarbeiter> getMitarbeiterBank() {
        return mitarbeiterService.getMitarbeiterBank();
    }

    @GetMapping("/listMitarbeiterInternBank")
    public Iterable<Mitarbeiter> getMitarbeiterInternBank() {
        return mitarbeiterService.getMitarbeiterInternBank();
    }
}
