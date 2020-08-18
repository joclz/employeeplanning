package com.cegeka.employeeplanning;

import com.cegeka.employeeplanning.data.Einsatz;
import com.cegeka.employeeplanning.data.EinsatzRepository;
import com.cegeka.employeeplanning.data.enums.Enums;
import com.cegeka.employeeplanning.service.EinsatzService;
import com.cegeka.employeeplanning.service.EinsatzSuche;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@RestController
public class EmployeeplanningEinsatzController {
    @Autowired
    private EinsatzRepository einsatzRepository;
    @Autowired
    private EinsatzService einsatzService;

    @PostMapping(path = "/addEinsatz")
    public Einsatz addEinsatz(Einsatz einsatz) {
        einsatzService.save(einsatz);
        return einsatz;
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

    @GetMapping("/findEinsaetzeBySuchkriterien")
    public Iterable<Einsatz> findEinsaetzeBySuchkriterien(EinsatzSuche einsatzSuche) {
        return einsatzService.findEinsaetzeBySuchkriterien(einsatzSuche);
    }

    @GetMapping("/getDeckungsbeitrag")
    public double getDeckungsbeitrag() {
        return einsatzService.getDeckungsbeitrag();
    }

    @GetMapping("/getEinsatzById")
    public Optional<Einsatz> getEinsatzById(@RequestParam("einsatzId") Integer id) {
        return einsatzRepository.findById(id);
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
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
}
