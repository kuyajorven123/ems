package com.project.ems.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.ui.Model;

@Controller
public class LeaveController {

    @GetMapping({ "/apply_leave" })
    public String apply_leave(Model model) {
        model.addAttribute("activePage", "apply_leave");
        return "/pages/apply_leave";
    }

}
