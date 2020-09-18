package com.cegeka.employeeplanning.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class EmployeeplanningController {

    //Todo
    // Diese ist nicht hübsch, aber der --base-href alleine, führte beim Refresh immer zu einem 404 Fehler.
    // Nur mit diesem zusätzlichen Konstrukt konnte dies verhindert werden.
    // Das geht aber eventuell auch eleganter???
    @GetMapping("/employeeplanning")
    public ModelAndView goToRoot() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index.html");
        return mav;
    }

    @GetMapping("/goToStart")
    public ModelAndView goToStart() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("start.html");
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
        mav.setViewName("mitarbeitervertrieb.html");
        return mav;
    }

    @GetMapping("/goToEinsatz")
    public ModelAndView goToEinsatz() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("einsatz.html");
        return mav;
    }
}
