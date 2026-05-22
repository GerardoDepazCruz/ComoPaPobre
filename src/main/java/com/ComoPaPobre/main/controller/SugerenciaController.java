package com.ComoPaPobre.main.controller;

import com.ComoPaPobre.main.models.Sugerencia;
import com.ComoPaPobre.main.services.SugerenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/sugerencias")
public class SugerenciaController {

    @Autowired
    private SugerenciaService sugerenciaService;

    @GetMapping("/formulario")
    public String mostrarFormulario(Model model) {
        model.addAttribute("sugerencia", new Sugerencia());
        return "sugerencias/formulario";
    }

    @PostMapping("/enviar")
    public String enviarSugerencia(@ModelAttribute Sugerencia sugerencia) {
        sugerencia.setFechaCreacion(new java.util.Date());
        sugerenciaService.saveSugerencia(sugerencia);
        return "redirect:/";
    }

    // Método para eliminar sugerencia por id
    @DeleteMapping("/eliminar/{id}")
    @ResponseBody
    public ResponseEntity<Void> eliminarSugerencia(@PathVariable Long id) {
        boolean eliminado = sugerenciaService.eliminarPorId(id);
        if (eliminado) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
