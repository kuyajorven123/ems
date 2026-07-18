package com.project.ems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;
import com.project.ems.model.Departments;
import com.project.ems.model.DepartmentsRepository;
import java.util.Optional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DepartmentsController {

    @Autowired
    public DepartmentsRepository departmentsRepository;

    @GetMapping({ "/departments" })
    public String departments(Model model) {
        model.addAttribute("activePage", "departments");
        model.addAttribute("department", departmentsRepository.findAll());
        return "pages/departments";
    }

    @PostMapping({ "/create_department" })
    public String create(@ModelAttribute Departments department) {
        Optional<Departments> existingDepartment = departmentsRepository.findByDepartment(department.getDepartment());

        if (existingDepartment.isPresent()) {
            return "redirect:/departments";
        }
        departmentsRepository.save(department);
        return "redirect:departments?success";
    }


    // id getter
    @GetMapping({ "/departments/{id}" })
    @ResponseBody
    public Departments getDepartment(@PathVariable Long id) {

        return departmentsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
    }
}
