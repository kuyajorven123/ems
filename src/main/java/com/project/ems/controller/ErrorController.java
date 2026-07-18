package com.project.ems.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping({"/access_denied"})
    public String access_denied(){
        return "error/access_denied";
    }
}
