package com.ComoPaPobre.main.services;

import com.ComoPaPobre.main.models.Reclamacion;
import com.ComoPaPobre.main.repositories.ReclamacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReclamacionService {

    @Autowired
    private ReclamacionRepository reclamacionRepository;

    public Reclamacion saveReclamacion(Reclamacion reclamacion) {
        return reclamacionRepository.save(reclamacion);
    }

    public List<Reclamacion> getAllReclamaciones() {
        return reclamacionRepository.findAll();
    }

    public Reclamacion getReclamacionById(Long id) {
        return reclamacionRepository.findById(id).orElse(null);
    }

    public Reclamacion updateReclamacion(Reclamacion reclamacion) {
        return reclamacionRepository.save(reclamacion);
    }

    public void deleteReclamacion(Long id) {
        reclamacionRepository.deleteById(id);
    }

    public boolean eliminarPorId(Long id) {
        if (reclamacionRepository.existsById(id)) {
            reclamacionRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
