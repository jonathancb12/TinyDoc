package com.tinydoc.controller;

import com.tinydoc.model.Roles;
import com.tinydoc.service.IRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RoleController {

    @Autowired
    private IRoles iRoles;

    @GetMapping(value = "security")
    public String roles(Model model) {
        model.addAttribute("lista", iRoles.listAll());
        return "security";
    }

    @PostMapping(value = "/security")
    public String nuevoRol(@RequestParam(name = "descripcion") String desc) {
        iRoles.newRole(new Roles("ROLE_" + desc));
        return "redirect:/security";
    }
}
