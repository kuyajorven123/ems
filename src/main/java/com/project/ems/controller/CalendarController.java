package com.project.ems.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;


@Controller 
public class CalendarController {
    @GetMapping({ "/calendar" })
    public String calendar(Model model) {
        model.addAttribute("activePage", "calendar");
        return "pages/calendar";
    }
}
