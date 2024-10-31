package com.example.vaidebicicleta.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.vaidebicicleta.Bicicleta;

public interface BicicletaRepositorio extends JpaRepository<Bicicleta, Long> {}