package com.ComoPaPobre.main.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ComoPaPobre.main.models.Hamburguesa;

public interface HamburguesaRepository extends JpaRepository<Hamburguesa, Long>{
}

