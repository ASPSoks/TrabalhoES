package com.example.bibliosys.domain.entity;
// src/main/java/com/example/bibliosys/domain/entity/Membro.java


import com.example.bibliosys.domain.enums.PerfilUsuario;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa um membro da comunidade acadêmica (aluno, professor)
 * que pode realizar empréstimos de livros.
 */
public class Membro extends Usuario {

    private String matricula;
    private boolean possuiPendencias = false;
    private List<Emprestimo> emprestimos;

    public Membro(String nome, String cpf, String email, String matricula) {
        super(nome, cpf, email, PerfilUsuario.MEMBRO);
        this.matricula = matricula;
        this.emprestimos = new ArrayList<>();
    }

    // Getters e Setters
    public String getMatricula() {
        return matricula;
    }

    public List<Emprestimo> getEmprestimos() {
        return emprestimos;
    }
    
    public boolean hasPendencias() {
        return possuiPendencias;
    }
    
    public void setPossuiPendencias(boolean status) {
        this.possuiPendencias = status;
    }

    // Métodos de negócio

    /**
     * Adiciona um empréstimo à lista do membro.
     * @param emprestimo O empréstimo a ser adicionado.
     */
    public void adicionarEmprestimo(Emprestimo emprestimo) {
        this.emprestimos.add(emprestimo);
    }
    
    /**
     * Conta quantos empréstimos ativos (não devolvidos) o membro possui.
     * @return O número de empréstimos ativos.
     */
    public long contarEmprestimosAtivos() {
        return this.emprestimos.stream()
            .filter(Emprestimo::isAtivo)
            .count();
    }
}