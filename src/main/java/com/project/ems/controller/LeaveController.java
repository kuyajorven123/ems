package com.project.ems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;

import org.springframework.ui.Model;
import com.project.ems.model.LeaveRepository;
import com.project.ems.model.UserRepository;
import com.project.ems.model.User;

@Controller
public class LeaveController {

    @Autowired
    private LeaveRepository leaveRepository;
    private UserRepository userRepository;

    public LeaveController(LeaveRepository leaveRepository, UserRepository userRepository) {
        this.leaveRepository = leaveRepository;
        this.userRepository = userRepository;
    }

    @GetMapping({ "/apply_leave" })
    public String apply_leave(Model model, Authentication authentication) {

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not Found"));
        model.addAttribute("activePage", "apply_leave");
        model.addAttribute("leave", leaveRepository.findByUserId(user));
        return "/pages/apply_leave";
    }

}
