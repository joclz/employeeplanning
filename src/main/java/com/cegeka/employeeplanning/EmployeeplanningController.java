package com.cegeka.employeeplanning;

import com.cegeka.employeeplanning.data.*;
import com.cegeka.employeeplanning.data.enums.Enums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class EmployeeplanningController {
    @Autowired
    private MitarbeiterRepository mitarbeiterRepository;
    @Autowired
    private MitarbeiterVertriebRepository mitarbeiterVertriebRepository;
    @Autowired
    private EinsatzRepository einsatzRepository;
    @Autowired
    private EinsatzService einsatzService;

    @GetMapping("/goToIndex")
    public ModelAndView goToIndex() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index.html");
        return mav;
    }

    @GetMapping("/goToMitarbeiter")
    public ModelAndView goToMitarbeiter() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("mitarbeiter.html");
        return mav;
    }

    @GetMapping("/goToMitarbeiterVertrieb")
    public ModelAndView goToMitarbeiterVertrieb() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("mitarbeiterVertrieb.html");
        return mav;
    }

    @GetMapping("/goToEinsatz")
    public ModelAndView goToEinsatz() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("einsatz.html");
        return mav;
    }

    @GetMapping("/listMitarbeiter")
    public Iterable<Mitarbeiter> getMitarbeiter() {
        return mitarbeiterRepository.findAll();
    }

    @GetMapping("/listMitarbeiterVertrieb")
    public Iterable<MitarbeiterVertrieb> getMitarbeiterVertrieb() {
        return mitarbeiterVertriebRepository.findAll();
    }

    @GetMapping("/listEinsaetze")
    public Iterable<Einsatz> getEinsaetze() {
        return einsatzRepository.findAll();
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

    @PostMapping(path = "/addMitarbeiter")
    //Todo - Der Unit-Test benötigt offensichtlich @RequestBody. Beim Aufruf innerhalb der Anwendung stört dies aber.
    //public Mitarbeiter addMitarbeiter(@RequestBody Mitarbeiter mitarbeiter) {
    public Mitarbeiter addMitarbeiter(Mitarbeiter mitarbeiter) {
        mitarbeiterRepository.save(mitarbeiter);
        return mitarbeiter;
    }

    @PostMapping(path = "/addEinsatz")
    public Einsatz addEinsatz(Einsatz einsatz) {
        einsatzService.save(einsatz);
        return einsatz;
    }

    // Beim POST-Request addEinsatz gab es ein Problem, dass das Datumsformat nicht korrekt durchgereicht werden konnte.
    // Mit diesem initBinder konnte dies behokben werden.
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
}
