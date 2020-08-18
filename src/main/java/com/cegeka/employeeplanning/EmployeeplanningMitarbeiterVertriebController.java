package com.cegeka.employeeplanning;

import com.cegeka.employeeplanning.data.MitarbeiterVertrieb;
import com.cegeka.employeeplanning.data.MitarbeiterVertriebRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeplanningMitarbeiterVertriebController {
    @Autowired
    private MitarbeiterVertriebRepository mitarbeiterVertriebRepository;

    @PostMapping(path = "/addMitarbeiterVertrieb")
    public MitarbeiterVertrieb addMitarbeiterVertrieb(MitarbeiterVertrieb mitarbeiterVertrieb) {
        mitarbeiterVertriebRepository.save(mitarbeiterVertrieb);
        return mitarbeiterVertrieb;
    }

    @GetMapping("/listMitarbeiterVertrieb")
    public Iterable<MitarbeiterVertrieb> getMitarbeiterVertrieb() {
        return mitarbeiterVertriebRepository.findAll();
    }
}
