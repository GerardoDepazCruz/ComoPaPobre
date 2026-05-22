package com.ComoPaPobre.main.services;

import com.ComoPaPobre.main.models.Hamburguesa;
import com.ComoPaPobre.main.repositories.HamburguesaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HamburguesaServicio {
    private final HamburguesaRepository repository;

    public HamburguesaServicio(HamburguesaRepository repository) {
        this.repository = repository;
    }

    public List<Hamburguesa> obtenerTodas() {
        return repository.findAll();
    }
}
