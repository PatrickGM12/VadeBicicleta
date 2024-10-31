package com.example.vaidebicicleta.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.vaidebicicleta.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {}