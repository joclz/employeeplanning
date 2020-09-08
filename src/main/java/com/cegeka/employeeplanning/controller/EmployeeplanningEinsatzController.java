package com.cegeka.employeeplanning.controller;

import com.cegeka.employeeplanning.data.Einsatz;
import com.cegeka.employeeplanning.data.dto.EinsatzDTO;
import com.cegeka.employeeplanning.data.enums.Enums;
import com.cegeka.employeeplanning.repositories.EinsatzRepository;
import com.cegeka.employeeplanning.service.EinsatzService;
import com.cegeka.employeeplanning.data.util.EinsatzSuche;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@CrossOrigin
@RestController
public class EmployeeplanningEinsatzController {
    @Autowired
    private EinsatzRepository einsatzRepository;
    @Autowired
    private EinsatzService einsatzService;

    @PostMapping(path = "/addEinsatz", consumes = "application/json")
    public Einsatz addEinsatz(@RequestBody EinsatzDTO einsatzDTO) {
        final Einsatz einsatz = einsatzService.convertToEntity(einsatzDTO);
        einsatzService.save(einsatz);
        return einsatzService.getEinsatzById(einsatz.getId());
    }

    @PostMapping(path = "/addEinsatz", consumes = "application/x-www-form-urlencoded")
    public Einsatz addEinsatzUrlencoded(EinsatzDTO einsatzDTO) {
        final Einsatz einsatz = einsatzService.convertToEntity(einsatzDTO);
        einsatzService.save(einsatz);
        return einsatzService.getEinsatzById(einsatz.getId());
    }

    @PostMapping(path = "/deleteEinsatz")
    public void deleteEinsatz(@RequestParam("einsatzId") Integer id) {
        einsatzService.deleteById(id);
    }

    @PostMapping(path = "/updateEinsatz")
    public Einsatz updateEinsatz(@RequestBody Einsatz einsatz) {
        einsatzService.save(einsatz);
        return einsatzService.getEinsatzById(einsatz.getId());
    }

    @GetMapping("/findEinsaetzeByEinsatzStatus")
    public Iterable<Einsatz> findEinsaetzeByEinsatzStatus(@RequestParam("status") String status) {
        Enums.EinsatzStatus einsatzStatus;
        try {
            einsatzStatus = Enums.EinsatzStatus.valueOf(status);
        } catch (IllegalArgumentException ex) {
            return einsatzRepository.findAll();
        }
        return einsatzRepository.findEinsaetzeByEinsatzStatus(einsatzStatus);
    }

    @GetMapping("/findEinsaetzeByMitarbeiterVertrieb")
    public Iterable<Einsatz> findEinsaetzeByMitarbeiterVertrieb(@RequestParam("mitarbeiterVertriebId") Integer id) {
        return einsatzService.findEinsaetzeByMitarbeiterVertriebId(id);
    }

    @PostMapping(path = "/findEinsaetzeBySuchkriterien", consumes = "application/json")
    public Iterable<Einsatz> findEinsaetzeBySuchkriterien(@RequestBody EinsatzSuche einsatzSuche) {
        return einsatzService.findEinsaetzeBySuchkriterien(einsatzSuche);
    }

    @PostMapping(path = "/findEinsaetzeBySuchkriterien", consumes = "application/x-www-form-urlencoded")
    public Iterable<Einsatz> findEinsaetzeBySuchkriterienUrlencoded(EinsatzSuche einsatzSuche) {
        return einsatzService.findEinsaetzeBySuchkriterien(einsatzSuche);
    }

    @GetMapping("/getDeckungsbeitrag")
    public double getDeckungsbeitrag() {
        return einsatzService.getDeckungsbeitrag();
    }

    @GetMapping("/getEinsatzById")
    public Einsatz getEinsatzById(@RequestParam("einsatzId") Integer id) {
        return einsatzService.getEinsatzById(id);
    }

    @GetMapping("/listEinsaetze")
    public Iterable<Einsatz> getEinsaetze() {
        return einsatzRepository.findAll();
    }

    // Beim POST-Request addEinsatz gab es ein Problem, dass das Datumsformat nicht korrekt durchgereicht werden konnte.
    // Mit diesem initBinder konnte dies behoben werden.
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}
