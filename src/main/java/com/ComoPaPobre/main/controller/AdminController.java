package com.ComoPaPobre.main.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import com.ComoPaPobre.main.services.SugerenciaService;
import com.ComoPaPobre.main.services.ReclamacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.ComoPaPobre.main.models.Sugerencia;
import com.ComoPaPobre.main.models.Reclamacion;
import com.ComoPaPobre.main.models.Hamburguesa;
import com.ComoPaPobre.main.services.HamburguesaServicio;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private SugerenciaService sugerenciaService;

    @Autowired
    private ReclamacionService reclamacionService;

    @Autowired
    private HamburguesaServicio hamburguesaServicio;

    @GetMapping("/login")
    public String adminLogin() {
        return "admin/login"; // plantilla admin/login.html
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/gestionar")
    public String adminGestion() {
        return "admin/gestionar"; // plantilla admin/gestionar.html
    }

    // Sugerencias management
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/sugerencias/lista")
    public String listarSugerenciasAdmin(Model model) {
        model.addAttribute("sugerencias", sugerenciaService.getAllSugerencias());
        return "sugerencias/lista";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/sugerencias/actualizar")
    @ResponseBody
    public ResponseEntity<?> actualizarSugerenciaAdmin(@RequestBody Sugerencia sugerencia) {
        Sugerencia sugerenciaExistente = sugerenciaService.getSugerenciaById(sugerencia.getId());
        if (sugerenciaExistente != null) {
            sugerencia.setFechaCreacion(sugerenciaExistente.getFechaCreacion());
            sugerenciaService.updateSugerencia(sugerencia);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/sugerencias/eliminar/{id}")
    public String eliminarSugerenciaAdmin(@PathVariable Long id, Model model) {
        sugerenciaService.deleteSugerencia(id);
        model.addAttribute("sugerencias", sugerenciaService.getAllSugerencias());
        return "sugerencias/lista";
    }

    // Reclamaciones management
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/reclamaciones/lista")
    public String listarReclamacionesAdmin(Model model) {
        model.addAttribute("reclamaciones", reclamacionService.getAllReclamaciones());
        return "reclamaciones/lista";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/reclamaciones/actualizar")
    @ResponseBody
    public ResponseEntity<?> actualizarReclamacionAdmin(@RequestBody Reclamacion reclamacion) {
        Reclamacion existente = reclamacionService.getReclamacionById(reclamacion.getId());
        if (existente != null) {
            reclamacion.setFechaCreacion(existente.getFechaCreacion());
            reclamacionService.updateReclamacion(reclamacion);
            return ResponseEntity.ok().body("Actualización exitosa");
        }
        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/reclamaciones/eliminar/{id}")
    public String eliminarReclamacionAdmin(@PathVariable Long id, Model model) {
        reclamacionService.deleteReclamacion(id);
        model.addAttribute("reclamaciones", reclamacionService.getAllReclamaciones());
        return "reclamaciones/lista";
    }

    // Menu management
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/menu/listahambur")
    public String mostrarListaHamburguesasAdmin(Model model) {
        List<Hamburguesa> hamburguesas = hamburguesaServicio.obtenerTodas();
        model.addAttribute("hamburguesas", hamburguesas);
        return "menu/listahambur";
    }
}
