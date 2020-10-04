package com.tinydoc.controller;

import com.tinydoc.model.Usuario;
import com.tinydoc.service.IUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private IUsuario iUsuario;

    @Autowired
    private HttpSession session;

    @RequestMapping(value = "/register")
    public String crear(Model model) {
        Usuario usuario = new Usuario();
        model.addAttribute("titulo", "Ingrese datos del nuevo usuario");
        model.addAttribute("usuario", usuario);
        model.addAttribute("action", 0);
        return "newUser";
    }

    @PostMapping(value = {"/register"})
    public String guardar(@RequestParam int action, Model model, Usuario usuario) {
        if (0 == action) {
            model.addAttribute("msje", iUsuario.create(usuario) ?
                    "Usuario creado con exito" : "Falló la crecion del usuario");
            return "redirect:index";
        }
        Usuario user = (Usuario) model.getAttribute("usuario");
        iUsuario.update(user);
        return "redirect:index";
    }

    @RequestMapping(value = "/update/{id}")
    public String editar(@PathVariable(value = "id") String id, Map<String, Object> model) {
        if (id == null || !iUsuario.exists(id)) {
            return "redirect:index";
        }
        Usuario usuario = new Usuario();
        usuario = iUsuario.find(id);
        model.put("usuario", usuario);
        model.put("titulo", "Editar usuario");
        model.put("action", 1);
        return "newUser";
    }

    @RequestMapping(value = "/delete/{id}")
    public String eliminar(@PathVariable(value = "id") String id, Model model) {
        if (iUsuario.find(id) == null) {
            model.addAttribute("msje", "No es posible eliminar un registro inexistente...");
        } else {
            iUsuario.delete(id);
        }
        return "redirect:/index";
    }

    @GetMapping(value = "/login")
    public String ingresar(@RequestParam(value = "error", required = false) String error,
                           Model model, Principal principal) {
        if (principal != null) {
            return "redirect:/index";
        }
        if (error != null) {
            model.addAttribute("error", "Usuario o contraseña incorrecto, reintente...");
        }
        return "login";
    }

}
