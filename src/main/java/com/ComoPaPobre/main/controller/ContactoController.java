package com.ComoPaPobre.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ContactoController {

    @PostMapping("/formContacto")
    public ModelAndView recibirContacto(@RequestParam String nombre,
                                        @RequestParam String email,
                                        @RequestParam String mensaje) {
        ModelAndView mav = new ModelAndView("Resultados/resultadoContacto");
        mav.addObject("nombre", nombre);
        mav.addObject("email", email);
        mav.addObject("mensaje", mensaje);
        return mav;
    }
}
