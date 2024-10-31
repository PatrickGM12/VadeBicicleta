package com.example.vaidebicicleta;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.vaidebicicleta.repositorios.BicicletaRepositorio;
import com.example.vaidebicicleta.repositorios.EmprestimoRepositorio;
import com.example.vaidebicicleta.repositorios.UsuarioRepositorio;

@Service
public class EmprestimoServico {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private BicicletaRepositorio bicicletaRepositorio;

    @Autowired
    private EmprestimoRepositorio emprestimoRepositorio;

    @Transactional
    public Emprestimo realizarEmprestimo(Long usuarioId, Long bicicletaId) {
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

    @Transactional
    public Emprestimo realizarDevolucao(Long emprestimoId) {
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

    // Método para listar todos os empréstimos
    public List<Emprestimo> listarEmprestimos() {
        return emprestimoRepositorio.findAll();
    }
}
