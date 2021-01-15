package com.cegeka.employeeplanning.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.cegeka.employeeplanning.data.Einsatz;
import com.cegeka.employeeplanning.data.dto.EinsatzDTO;
import com.cegeka.employeeplanning.data.dto.EinsatzSucheDTO;
import com.cegeka.employeeplanning.data.dto.PartialEinsaetzeDTO;
import com.cegeka.employeeplanning.data.enums.Enums;
import com.cegeka.employeeplanning.data.util.EinsatzSuche;
import com.cegeka.employeeplanning.data.util.ItemCriteria;
import com.cegeka.employeeplanning.repositories.EinsatzRepository;
import com.cegeka.employeeplanning.service.EinsatzService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public Einsatz updateEinsatz(@RequestBody EinsatzDTO einsatzDTO) {
        final Einsatz einsatz = einsatzService.convertToEntity(einsatzDTO);
        einsatzService.update(einsatz);
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
    public Iterable<Einsatz> findEinsaetzeBySuchkriterien(@RequestBody EinsatzSucheDTO einsatzSucheDTO) {
        final EinsatzSuche einsatzSuche = einsatzService.convertToEntity(einsatzSucheDTO);
        // TODO hier kommt komischerweiße ein Einsatz zurück obwohl alle Werte null sind,
        //  nur nachdem ich zB alle Einsätze von einem Mitarbeiter in der komplexen Suche suche und danach das Feld vom Mitarbeiter lösche und erneut Suche
        //  Die EinsatzSucheDTO kommt dann nur mit null Werten, aber es wird trotzdem ein Einsatz zurück gegeben

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

    // Weiterer Endpunkt für Liste aller Einsätze
    // mit Paging und Filterung, z.B. liefert folgender Aufruf die ersten zwei Einsätze mit einer Wahrscheinlichkeit zwischen 25 und 50
    // http://localhost:8080/partialEinsaetze?page=0&size=2&filter=wahrscheinlichkeit>=25&filter=wahrscheinlichkeit<=50
    // Die Query-Paramameter werden in einem ItemCriteria abgelegt
    // die PAging-Attribute werden direkt an ein PagingAndSortingRepository weitergegebene (selbsterklärend)
    // Die Filter-Parameter werden auf sogenannte Specifications gemappt und an das JpaSpecification-Repository weitergegeben
    // (daher muss das Einsatz-Repository beide Interfaces implementieren)
    // Derzeit werden die Specifications voll generisch aus den Filtern erzeugt (mögliche Operationen siehe Klasse FilterOperations)
    // dies funktioniert derzeit aber nicht für Enums oder Datums-Klassen, da diese Specifications in einem etwas anderen Format erfolgen müssen.
    // TODO: Hier müsste über den Spaltennamen ermittelt werden, um welchen Datentyp es sich handelt und dann das Erzeugen der Specifications entsprechend angepasst werden.
    @GetMapping("/partialEinsaetze")
    public PartialEinsaetzeDTO getPartialEinsaetze(
            ItemCriteria criteria)
    {
        return einsatzService.getPartialEinsaetze(criteria);
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
