package com.cegeka.employeeplanning;

import com.cegeka.employeeplanning.data.Mitarbeiter;
import com.cegeka.employeeplanning.data.MitarbeiterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeplanningController {
    @Autowired
    private MitarbeiterRepository mitarbeiterRepository;

    @GetMapping("/listMitarbeiter")
    public Iterable<Mitarbeiter> getMitarbeiter() {
        return mitarbeiterRepository.findAll();
    }
}
