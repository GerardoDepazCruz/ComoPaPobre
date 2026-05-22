package com.ComoPaPobre.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class InicioController {

    @GetMapping("/")
    public String mostrarInicio() {
        return "inicio";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/crearcuenta")
    public String crearCuenta() {
        return "crearcuenta";
    }

    //MENU
    @GetMapping("/menuhambur")
    public String mostrarMenuHamburguesas() {
        return "menuhambur"; 
    }

    @GetMapping("/menualit")
    public String mostrarMenuAlitas(Model model) {
        model.addAttribute("titulo", "titulo.menualit");
        return "menualit";
    }

    @GetMapping("/menusalchi")
    public String mostrarMenuSalchipapas() {
        return "menusalchi"; 
    }

    @GetMapping("/menupech")
    public String mostrarMenuPechuga() {
        return "menupech"; 
    }

     @GetMapping("/menubebid")
    public String mostrarMenuBebida() {
        return "menubebid"; 
    }

    @GetMapping("/promociones")
    public String promociones() {
        return "promociones"; 
    }

    @GetMapping("/contacto")
    public String mostrarcontacto() {
        return "contacto"; 
    }

    @GetMapping("/nosotros")
    public String mostrarnosotros() {
        return "nosotros"; 
    }

    @GetMapping("/terminosCondiciones")
    public String mostrarterminosCondiciones() {
        return "terminosCondiciones"; 
    }

    @GetMapping("/preguntasfrecuentes")
    public String mostrarpreguntasfrecuentes() {
        return "preguntasfrecuentes"; 
    }


    

}
