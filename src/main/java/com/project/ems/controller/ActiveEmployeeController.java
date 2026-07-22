package com.project.ems.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.ems.model.User;
import com.project.ems.model.UserRepository;
import com.project.ems.model.DepartmentRepository;

import org.springframework.security.crypto.password.PasswordEncoder;



@Controller
public class ActiveEmployeeController {

    @Autowired
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private DepartmentRepository departmentRepository;

    public ActiveEmployeeController(UserRepository userRepository, PasswordEncoder passwordEncoder, DepartmentRepository departmentRepository){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.departmentRepository = departmentRepository;
    }

    @GetMapping({"/active_employee"})
        public String active_employee(Model model){
            model.addAttribute("activePage", "active_employee");
            model.addAttribute("user", userRepository.findByStatusAndRole("Active", "EMPLOYEE"));
            model.addAttribute("departments", departmentRepository.findAllByOrderByDepartmentNameAsc());
            // model.addAttribute("user", userRepository.findByRole("EMPLOYEE"));
            return "pages/active_employee";
        }
    

    // create
    @PostMapping({"/create"})
    public String create(@ModelAttribute User user){
        Optional<User> existingUser =
                userRepository.findByEmail(user.getEmail());

        if (existingUser.isPresent()){
            return "redirect:/active_employee?error";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus("Active");
        user.setRole("EMPLOYEE");
        userRepository.save(user);
        return "redirect:/active_employee?success";
    }


    // id getter
    @GetMapping({"/active_employee/{id}"})
    @ResponseBody
    public User getUser(@PathVariable Long id) {

        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    // edit
    @PostMapping({"/update"})
    public String update(@ModelAttribute User user){

        User existing = userRepository.findById(user.getId())
            .orElseThrow(() -> new RuntimeException("Employee not found"));


            // email checker
        Optional<User> existingUser =
                userRepository.findByEmail(user.getEmail());

        if (existingUser.isPresent()
            && !existingUser.get().getId().equals(user.getId())) {

        return "redirect:/active_employee?error";
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
                passwordEncoder.encode(user.getPassword())
        );
    }


        userRepository.save(existing);
        return "redirect:/active_employee?update_success";
    }


    // for the deactivate
    @PostMapping({"/deactivate"})
    public String deactivate(@ModelAttribute User user){
        User existing = userRepository.findById(user.getId())
            .orElseThrow(() -> new RuntimeException("Employee not found"));

     existing.setStatus("Inactive");

     userRepository.save(existing);
     return "redirect:/active_employee?deactivate_success";
    }

    
}
