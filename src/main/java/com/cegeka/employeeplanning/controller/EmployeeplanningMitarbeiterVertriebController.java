package com.cegeka.employeeplanning.controller;

import com.cegeka.employeeplanning.data.MitarbeiterVertrieb;
import com.cegeka.employeeplanning.repositories.MitarbeiterVertriebRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class EmployeeplanningMitarbeiterVertriebController {
    @Autowired
    private MitarbeiterVertriebRepository mitarbeiterVertriebRepository;

    @PostMapping(path = "/addMitarbeiterVertrieb")
    public MitarbeiterVertrieb addMitarbeiterVertrieb(MitarbeiterVertrieb mitarbeiterVertrieb) {
        mitarbeiterVertriebRepository.save(mitarbeiterVertrieb);
        return mitarbeiterVertrieb;
    }

    @PostMapping(path = "/deleteMitarbeiterVertrieb")
    public void deleteMitarbeiterVertrieb(@RequestParam("mitarbeiterVertriebId") int id) {
        mitarbeiterVertriebRepository.deleteById(id);
    }

    @PostMapping(path = "/updateMitarbeiterVertrieb")
    public MitarbeiterVertrieb updateMitarbeiterVertrieb(MitarbeiterVertrieb mitarbeiterVertrieb) {
        mitarbeiterVertriebRepository.save(mitarbeiterVertrieb);
        return mitarbeiterVertrieb;
    }

    @GetMapping("/listMitarbeiterVertrieb")
    public Iterable<MitarbeiterVertrieb> getMitarbeiterVertrieb() {
        return mitarbeiterVertriebRepository.findAll();
    }
}
