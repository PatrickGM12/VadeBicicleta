package com.example.vaidebicicleta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/emprestimos")
public class EmprestimoController {

    @Autowired
    private EmprestimoServico emprestimoServico;

    @PostMapping("/emprestar")
    public ResponseEntity<Emprestimo> emprestarBicicleta(@RequestParam Long usuarioId, @RequestParam Long bicicletaId) {
        Emprestimo emprestimo = emprestimoServico.realizarEmprestimo(usuarioId, bicicletaId);
        return ResponseEntity.ok(emprestimo);
    }

    @PostMapping("/devolver")
    public ResponseEntity<Emprestimo> devolverBicicleta(@RequestParam Long emprestimoId) {
        Emprestimo devolucao = emprestimoServico.realizarDevolucao(emprestimoId);
        return ResponseEntity.ok(devolucao);
    }
}