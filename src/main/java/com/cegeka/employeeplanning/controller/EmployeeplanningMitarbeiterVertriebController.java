package com.cegeka.employeeplanning.controller;

import com.cegeka.employeeplanning.data.MitarbeiterVertrieb;
import com.cegeka.employeeplanning.data.util.MitarbeiterItem;
import com.cegeka.employeeplanning.repositories.MitarbeiterVertriebRepository;
import com.cegeka.employeeplanning.service.MitarbeiterVertriebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class EmployeeplanningMitarbeiterVertriebController {
    @Autowired
    private MitarbeiterVertriebRepository mitarbeiterVertriebRepository;
    @Autowired
    private MitarbeiterVertriebService mitarbeiterVertriebService;

    @PostMapping(path = "/addMitarbeiterVertrieb", consumes = "application/json")
    public MitarbeiterVertrieb addMitarbeiterVertrieb(@RequestBody MitarbeiterVertrieb mitarbeiterVertrieb) {
        mitarbeiterVertriebRepository.save(mitarbeiterVertrieb);
        return mitarbeiterVertrieb;
    }

    @PostMapping(path = "/addMitarbeiterVertrieb", consumes = "application/x-www-form-urlencoded")
    public MitarbeiterVertrieb addMitarbeiterVertriebUrlencoded(MitarbeiterVertrieb mitarbeiterVertrieb) {
        mitarbeiterVertriebRepository.save(mitarbeiterVertrieb);
        return mitarbeiterVertrieb;
    }

    @PostMapping(path = "/deleteMitarbeiterVertrieb")
    public void deleteMitarbeiterVertrieb(@RequestParam("mitarbeiterVertriebId") int id) {
        mitarbeiterVertriebRepository.deleteById(id);
    }

    @PostMapping(path = "/updateMitarbeiterVertrieb")
    public MitarbeiterVertrieb updateMitarbeiterVertrieb(@RequestBody MitarbeiterVertrieb mitarbeiterVertrieb) {
        mitarbeiterVertriebRepository.save(mitarbeiterVertrieb);
        return mitarbeiterVertrieb;
    }

    @GetMapping("/listMitarbeiterVertrieb")
    public Iterable<MitarbeiterVertrieb> getMitarbeiterVertrieb() {
        return mitarbeiterVertriebRepository.findAll();
    }

    @GetMapping("/getMitarbeiterVertriebListOrderByName")
    public List<MitarbeiterItem> getMitarbeiterVertriebListOrderByName() {
        return mitarbeiterVertriebService.getMitarbeiterVertriebListOrderByName();
    }
}
