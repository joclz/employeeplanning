package com.cegeka.employeeplanning.controller;

import java.util.List;

import com.cegeka.employeeplanning.data.MitarbeiterVertrieb;
import com.cegeka.employeeplanning.data.dto.MitarbeiterDTO;
import com.cegeka.employeeplanning.repositories.MitarbeiterVertriebRepository;
import com.cegeka.employeeplanning.service.MitarbeiterVertriebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        return mitarbeiterVertriebService.getMitarbeiterVertriebById(mitarbeiterVertrieb.getId());
    }

    @PostMapping(path = "/addMitarbeiterVertrieb", consumes = "application/x-www-form-urlencoded")
    public MitarbeiterVertrieb addMitarbeiterVertriebUrlencoded(MitarbeiterVertrieb mitarbeiterVertrieb) {
        mitarbeiterVertriebRepository.save(mitarbeiterVertrieb);
        return mitarbeiterVertriebService.getMitarbeiterVertriebById(mitarbeiterVertrieb.getId());
    }

    @PostMapping(path = "/deleteMitarbeiterVertrieb")
    public void deleteMitarbeiterVertrieb(@RequestParam("mitarbeiterVertriebId") Integer id) {
        mitarbeiterVertriebService.deleteById(id);
    }

    @PostMapping(path = "/updateMitarbeiterVertrieb")
    public MitarbeiterVertrieb updateMitarbeiterVertrieb(@RequestBody MitarbeiterVertrieb mitarbeiterVertrieb) {
        mitarbeiterVertriebService.update(mitarbeiterVertrieb);
        return mitarbeiterVertriebService.getMitarbeiterVertriebById(mitarbeiterVertrieb.getId());
    }

    @GetMapping("/listMitarbeiterVertrieb")
    public Iterable<MitarbeiterVertrieb> getMitarbeiterVertrieb() {
        return mitarbeiterVertriebRepository.findAll();
    }

    @GetMapping("/getMitarbeiterVertriebListOrderByName")
    public List<MitarbeiterDTO> getMitarbeiterVertriebListOrderByName() {
        return mitarbeiterVertriebService.getMitarbeiterVertriebListOrderByName();
    }
}
