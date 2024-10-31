package com.example.vaidebicicleta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/emprestimos")
public class EmprestimoController {

    @Autowired
    private EmprestimoServico emprestimoServico;

    // Endpoint para emprestar bicicleta
    @PostMapping("/emprestar")
    public ResponseEntity<?> emprestarBicicleta(@RequestParam Long usuarioId, @RequestParam Long bicicletaId) {
        System.out.println("Recebendo requisição de empréstimo para usuário " + usuarioId + " e bicicleta " + bicicletaId);
        try {
            Emprestimo emprestimo = emprestimoServico.realizarEmprestimo(usuarioId, bicicletaId);
            return ResponseEntity.ok(emprestimo);
        } catch (Exception e) {
            System.err.println("Erro ao realizar empréstimo: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao realizar empréstimo: " + e.getMessage());
        }
    }

    // Endpoint para devolver bicicleta
    @PostMapping("/devolver")
    public ResponseEntity<Emprestimo> devolverBicicleta(@RequestParam Long emprestimoId) {
        try {
            Emprestimo devolucao = emprestimoServico.realizarDevolucao(emprestimoId);
            return ResponseEntity.ok(devolucao);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Endpoint para listar todos os empréstimos
    @GetMapping("/listar")
    public ResponseEntity<List<Emprestimo>> listarEmprestimos() {
        List<Emprestimo> emprestimos = emprestimoServico.listarEmprestimos();
        return ResponseEntity.ok(emprestimos);
    }
}
