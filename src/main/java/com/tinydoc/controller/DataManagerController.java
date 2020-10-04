package com.tinydoc.controller;

import com.tinydoc.service.IDataManager;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;


@Controller
public class DataManagerController {

    @Autowired
    private IDataManager iDataManager;

    @RequestMapping(value = "/respaldo")
    public String gestionDatos(Model model) {
        model.addAttribute("msg",null);
        return "recovery";
    }

    @RequestMapping(value = "/backup")
    public ResponseEntity<InputStreamResource> generarRespaldo() throws IOException {
        File archivo = iDataManager.generateBackup();
        InputStreamResource resource = new InputStreamResource(new FileInputStream(archivo));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=BACKUP.DMP");
        return ResponseEntity.ok().headers(headers).body(resource);
    }

    @PostMapping(value = "/restore")
    public String restaurarRespaldo(@RequestParam("file") MultipartFile file, Model attributes)
            throws IOException {
        if (file == null || file.isEmpty()
                || !"DMP".equalsIgnoreCase(FilenameUtils.getExtension(file.getOriginalFilename()))) {
            attributes.addAttribute("msg", "Debe seleccionar un archivo con extension DMP.");
            return "recovery";
        }
       iDataManager.applyRestore(file);
        return "recovery";
    }
}
