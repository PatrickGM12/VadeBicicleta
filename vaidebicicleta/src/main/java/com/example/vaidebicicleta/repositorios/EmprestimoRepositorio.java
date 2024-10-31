package com.example.vaidebicicleta.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.vaidebicicleta.Emprestimo;

public interface EmprestimoRepositorio extends JpaRepository<Emprestimo, Long> {}