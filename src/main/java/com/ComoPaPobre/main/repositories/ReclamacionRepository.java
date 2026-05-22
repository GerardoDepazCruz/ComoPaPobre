package com.ComoPaPobre.main.repositories;

import com.ComoPaPobre.main.models.Reclamacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReclamacionRepository extends JpaRepository<Reclamacion, Long> {
}