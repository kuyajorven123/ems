package com.project.ems.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.ems.model.User;
import com.project.ems.model.UserRepository;
import com.project.ems.model.DepartmentRepository;

import org.springframework.ui.Model;

@Controller
public class AdminController {

    @Autowired

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private DepartmentRepository departmentRepository;

    public AdminController(UserRepository userRepository, PasswordEncoder passwordEncoder, DepartmentRepository departmentRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.departmentRepository = departmentRepository;
    }


    // Active Admin
    @GetMapping({ "/active_admin" })
    public String admin(Model model) {
        model.addAttribute("activePage", "active_admin");
        model.addAttribute("user", userRepository.findByStatusAndRole("Active", "ADMIN"));
        model.addAttribute("departments", departmentRepository.findAllByOrderByDepartmentNameAsc());
        // model.addAttribute("employee", userRepository.findByRole("ADMIN"));
        return "pages/active_admin";
    }

    // create
    @PostMapping({ "/create_admin" })
    public String create_admin(@ModelAttribute User user) {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser.isPresent()) {
            return "redirect:/active_admin?error";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus("Active");
        user.setRole("ADMIN");
        userRepository.save(user);
        return "redirect:/active_admin?success";
    }

    // id getter
    @GetMapping({ "/active_admin/{id}" })
    @ResponseBody
    public User getUser(@PathVariable Long id) {

        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
    }

    // update
    @PostMapping({ "/update_admin" })
    public String update(@ModelAttribute User user) {

        User existing = userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        // email checker
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser.isPresent()
                && !existingUser.get().getId().equals(user.getId())) {

            return "redirect:/active_admin?error";
        }

        existing.setFirstname(user.getFirstname());
        existing.setMiddlename(user.getMiddlename());
        existing.setLastname(user.getLastname());
        existing.setAddress(user.getAddress());
        existing.setBirthday(user.getBirthday());
        existing.setPosition(user.getPosition());
        existing.setEmail(user.getEmail());
        existing.setDepartment(user.getDepartment());

        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            existing.setPassword(
                    passwordEncoder.encode(user.getPassword()));
        }

        userRepository.save(existing);
        return "redirect:/active_admin?update_success";
    }


    // deactivate
    @PostMapping({"/deactivate_admin"})
    public String deactivate(@ModelAttribute User user){
        User existing = userRepository.findById(user.getId())
            .orElseThrow(() -> new RuntimeException("Admin not found"));

     existing.setStatus("Inactive");

     userRepository.save(existing);
     return "redirect:/active_admin?deactivate_success";
    }


    


    // Inactive Admin

    @GetMapping({"/inactive_admin"})
    public String inactiveAdmin(Model model){
        model.addAttribute("activePage", "inactive_admin");
        model.addAttribute("user", userRepository.findByStatusAndRole("Inactive", "ADMIN"));
        return "/pages/inactive_admin";
    }

    // id getter
    @GetMapping({ "/inactive_admin/{id}" })
    @ResponseBody
    public User getInactive(@PathVariable Long id) {

        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
    }

    @PostMapping({"/reactivate_admin"})
    public String reactivate(@ModelAttribute User user){
        User existing = userRepository.findById(user.getId())
            .orElseThrow(() -> new RuntimeException("Admin not found"));

     existing.setStatus("Active");

     userRepository.save(existing);
     return "redirect:/inactive_admin?reactivate_success";
    }


    // delete
    @PostMapping({ "/delete_admin" })
    public String deleted(@RequestParam Long id) {
        userRepository.deleteById(id);
        return "redirect:/inactive_admin?delete_success";
    }
}
