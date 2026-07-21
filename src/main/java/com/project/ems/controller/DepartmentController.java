package com.project.ems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;
import com.project.ems.model.Department;
import com.project.ems.model.DepartmentRepository;
import java.util.Optional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DepartmentController {

    @Autowired
    public DepartmentRepository departmentRepository;

    @GetMapping({ "/department" })
    public String departments(Model model) {
        model.addAttribute("activePage", "department");
        model.addAttribute("department", departmentRepository.findAll());
        return "pages/department";
    }

    // create
    @PostMapping({ "/create_department" })
    public String create(@ModelAttribute Department department) {
        Optional<Department> existingDepartment = departmentRepository
                .findByDepartmentName(department.getDepartmentName());

        if (existingDepartment.isPresent()) {
            return "redirect:/department?error";
        }
        departmentRepository.save(department);
        return "redirect:department?success";
    }

    // id getter
    @GetMapping({ "/department/{id}" })
    @ResponseBody
    public Department getDepartment(@PathVariable Long id) {

        return departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
    }

    // update
    @PostMapping({ "/update_department" })
    public String update(@ModelAttribute Department department) {
        Department existing = departmentRepository.findById(department.getId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        Optional<Department> existingDepartment = departmentRepository
                .findByDepartmentName(department.getDepartmentName());

        if (existingDepartment.isPresent() && !existingDepartment.get().getId().equals(department.getId())) {
            return "redirect:/department?error";
        }

        existing.setDepartmentName(department.getDepartmentName());
        departmentRepository.save(existing);
        return "redirect:/department?update_success";

    }

    // delete
    @PostMapping({ "delete_department" })
    public String delete(@RequestParam Long id) {
        departmentRepository.deleteById(id);
        return "redirect:/department?delete_success";

    }
}
