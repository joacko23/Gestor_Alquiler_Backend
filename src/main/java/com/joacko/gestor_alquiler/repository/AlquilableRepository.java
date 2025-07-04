package com.joacko.gestor_alquiler.repository;

import com.joacko.gestor_alquiler.model.Alquilable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlquilableRepository extends JpaRepository<Alquilable, Long> {
}
