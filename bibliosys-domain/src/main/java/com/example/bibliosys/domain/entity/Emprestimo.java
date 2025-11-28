package com.example.bibliosys.domain.entity;

import java.time.LocalDate;

/**
 * Representa a transação de empréstimo de um livro para um membro.
 */
public class Emprestimo {

    private Long id;
    private Membro membro;
    private Livro livro;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucaoPrevista;
    private LocalDate dataDevolucaoReal;

    public Emprestimo(Membro membro, Livro livro) {
        if (membro == null || livro == null) {
            throw new IllegalArgumentException("Membro e Livro são obrigatórios para o empréstimo.");
        }
        this.membro = membro;
        this.livro = livro;
        this.dataEmprestimo = LocalDate.now();
        this.dataDevolucaoPrevista = this.dataEmprestimo.plusDays(15);
        this.dataDevolucaoReal = null;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Membro getMembro() {
        return membro;
    }

    public Livro getLivro() {
        return livro;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public LocalDate getDataDevolucaoPrevista() {
        return dataDevolucaoPrevista;
    }

    public LocalDate getDataDevolucaoReal() {
        return dataDevolucaoReal;
    }
    
    public void setDataDevolucaoReal(LocalDate dataDevolucaoReal) {
        this.dataDevolucaoReal = dataDevolucaoReal;
    }

    /**
     * Verifica se o empréstimo ainda está ativo (não foi devolvido).
     * @return true se a data de devolução real for nula.
     */
    public boolean isAtivo() {
        return this.dataDevolucaoReal == null;
    }

    /**
     * Finaliza o empréstimo, registrando a data de devolução como hoje.
     */
    public void finalizar() {
        if(isAtivo()){
            this.dataDevolucaoReal = LocalDate.now();
        }
    }
}
