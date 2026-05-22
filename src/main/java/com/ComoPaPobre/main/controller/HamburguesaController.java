package com.ComoPaPobre.main.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import com.ComoPaPobre.main.models.Hamburguesa;
import com.ComoPaPobre.main.services.HamburguesaServicio;
import java.util.List;

@Controller
@RequestMapping
@CrossOrigin(origins = "*")
public class HamburguesaController {
    private final HamburguesaServicio hamburguesaServicio;

    public HamburguesaController(HamburguesaServicio hamburguesaServicio) {
        this.hamburguesaServicio = hamburguesaServicio;
    }

    // API REST JSON
    @GetMapping("/api/hamburguesas")
    @ResponseBody
    public List<Hamburguesa> obtenerHamburguesas() {
        return hamburguesaServicio.obtenerTodas();
    }
}
