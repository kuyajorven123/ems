package com.project.ems.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.core.Authentication;

import org.springframework.ui.Model;

import com.project.ems.model.Leave;
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
        model.addAttribute("leave", leaveRepository.findByUser(user));
        return "/pages/apply_leave";
    }

    @PostMapping({ "/leave_apply" })
    public String leave_apply(@ModelAttribute Leave leave, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName())
            .orElseThrow(() -> new RuntimeException("User not Found"));
        leave.setUser(user);
        leave.setStatus("Pending");
        leave.setDateApplied(LocalDate.now());
        leaveRepository.save(leave);
        return "redirect:/apply_leave?success";
    }

    @GetMapping({"/leave_applications"})
    public String leave_application(Model model){
        model.addAttribute("activePage", "leave_applications");
        model.addAttribute("leave", leaveRepository.findAllForApplications());
        return "/pages/leave_applications";
    }
}
