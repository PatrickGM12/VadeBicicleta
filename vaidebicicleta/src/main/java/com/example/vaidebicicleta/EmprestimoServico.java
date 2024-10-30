package com.example.vaidebicicleta;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmprestimoServico {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private BicicletaRepositorio bicicletaRepositorio;
    @Autowired
    private EmprestimoRepositorio emprestimoRepositorio;

        public Emprestimo realizarEmprestimo(Long usuarioId, Long bicicletaId) {
        // Lógica para validar e criar o empréstimo
        Optional<Bicicleta> bicicleta = bicicletaRepositorio.findById(bicicletaId);
        if (bicicleta.isPresent() && bicicleta.get().isDisponivel()) {
            Emprestimo emprestimo = new Emprestimo();
            emprestimo.setUsuarioId(usuarioId);
            emprestimo.setBicicletaId(bicicletaId);
            emprestimo.setDataEmprestimo(LocalDateTime.now());
            emprestimo.setDevolvido(false);
            bicicleta.get().setDisponivel(false);
            bicicletaRepositorio.save(bicicleta.get());
            return emprestimoRepositorio.save(emprestimo);
        }
        throw new RuntimeException("Bicicleta não disponível para empréstimo.");
    }

    public Emprestimo realizarDevolucao(Long emprestimoId) {
        // Lógica para processar a devolução
        Emprestimo emprestimo = emprestimoRepositorio.findById(emprestimoId)
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado"));
        emprestimo.setDataDevolucao(LocalDateTime.now());
        emprestimo.setDevolvido(true);

        Bicicleta bicicleta = bicicletaRepositorio.findById(emprestimo.getBicicletaId())
                .orElseThrow(() -> new RuntimeException("Bicicleta não encontrada"));
        bicicleta.setDisponivel(true);
        bicicletaRepositorio.save(bicicleta);

        return emprestimoRepositorio.save(emprestimo);
    }
}