package com.example.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class RoleController {
    @Autowired private RoleService service;

    @GetMapping("/roles")
    public String showRoleList(Model model){
        List<Role> listRoles = service.listAll();
        model.addAttribute("listRoles",listRoles);
        return "roles";
    }

    @GetMapping("/roles/new")
    public String showNewForm(Model model){
        model.addAttribute("role", new Role());
        model.addAttribute("pageTitle","Add New Role");
        return "role_form";
    }

    @PostMapping("/roles/save")
    public String saveRole(Role role, RedirectAttributes ra){
        service.save(role);
        ra.addFlashAttribute("message", "The role has been saved successfully.");
        return "redirect:/roles";
    }

    @GetMapping("/roles/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra){
        try {
            Role role = service.get(id);
            model.addAttribute("role", role);
            model.addAttribute("pageTitle", "Edit Role (ID : " + id + ")");
            return "role_form";
        }catch(RoleNotFoundException e){
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/roles";
        }
    }

    @GetMapping("/roles/delete/{id}")
    public String deleteRole(@PathVariable("id") Integer id, Model model, RedirectAttributes ra){
        try {
            service.delete(id);
            ra.addFlashAttribute("message", "The role ID " + id + " has been deleted.");
        }catch(RoleNotFoundException e){
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/roles";
    }
}
