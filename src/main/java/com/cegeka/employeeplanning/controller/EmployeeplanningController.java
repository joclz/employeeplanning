package com.cegeka.employeeplanning.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class EmployeeplanningController {

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
}
