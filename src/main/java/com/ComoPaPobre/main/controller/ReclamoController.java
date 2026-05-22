package com.ComoPaPobre.main.controller;

import com.ComoPaPobre.main.models.Reclamacion;
import com.ComoPaPobre.main.services.ReclamacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/reclamaciones")
public class ReclamoController {

    @Autowired
    private ReclamacionService reclamacionService;

    @GetMapping("/formulario")
    public String mostrarFormulario(Model model) {
        model.addAttribute("reclamacion", new Reclamacion());
        return "reclamaciones/formulario"; // vista para crear reclamación
    }

    @PostMapping("/enviar")
    public String enviarReclamacion(@ModelAttribute Reclamacion reclamacion) {
        reclamacion.setFechaCreacion(new Date());
        reclamacionService.saveReclamacion(reclamacion);
        return "redirect:/";
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarReclamacion(@PathVariable Long id) {
        boolean eliminado = reclamacionService.eliminarPorId(id);
        if (eliminado) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}