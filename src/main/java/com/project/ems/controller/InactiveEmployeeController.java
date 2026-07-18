package com.project.ems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.ems.model.User;
import com.project.ems.model.UserRepository;

import org.springframework.ui.Model;

@Controller
public class InactiveEmployeeController {

    @Autowired
    UserRepository userRepository;

    // id getter
    @GetMapping({ "/inactive_employee/{id}" })
    @ResponseBody
    public User getUser(@PathVariable Long id) {

        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    @GetMapping({ "/inactive_employee" })
    public String inactive_employee(Model model) {
        model.addAttribute("activePage", "inactive_employee");
        model.addAttribute("user", userRepository.findByStatusAndRole("Inactive", "EMPLOYEE"));
        // model.addAttribute("user", userRepository.findByRole("EMPLOYEE"));
        return "pages/inactive_employee";
    }

    @PostMapping({ "/reactivate" })
    public String reactivate(@ModelAttribute User user) {
        User existing = userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        existing.setStatus("Active");

        userRepository.save(existing);
        return "redirect:/inactive_employee?reactivate_success";
    }

    @PostMapping({ "/delete" })
    public String deleted(@RequestParam Long id) {
        userRepository.deleteById(id);
        return "redirect:/inactive_employee?delete_success";
    }

}
