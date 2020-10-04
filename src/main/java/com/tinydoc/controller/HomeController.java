package com.tinydoc.controller;

import com.tinydoc.model.Usuario;
import com.tinydoc.service.IUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private IUsuario iUsuario;

    @Autowired
    private HttpSession session;

    @RequestMapping(value = {"/home", "/", "/inicio", "/index"})
    public String index(Model model) {
        model.addAttribute("titulo", "Listado de Usuarios");
        List<Usuario> list = iUsuario.listAll();
        if (list == null || list.isEmpty()) {
            model.addAttribute("msje", "No se encontraron datos para mostrar");
        } else {
            model.addAttribute("lista", list);
        }
        return "index";
    }

    @GetMapping(value = {"/logout"})
    public String logoutDo(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        SecurityContextHolder.clearContext();
        session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        for (Cookie cookie : request.getCookies()) {
            cookie.setMaxAge(0);
        }
        return "redirect:/index";
    }

    @GetMapping(value = "/help")
    public String ayuda(){
        return "help";
    }

    @GetMapping(value = "/doc")
    public ResponseEntity<InputStreamResource> generardocumento() throws IOException {
        File archivo = new File("C:\\Users\\Jonathan\\Desktop\\Ingeniería Informática\\Taller Integracion de Software\\Semana 3\\Manual de apoyo al usuario TinyDoc.docx");
        InputStreamResource resource = new InputStreamResource(new FileInputStream(archivo));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=Manual de ayuda.docx");
        return ResponseEntity.ok().headers(headers).body(resource);
    }
}
