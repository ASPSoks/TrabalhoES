package com.example.bibliosys.domain.entity;
// src/main/java/com/example/bibliosys/domain/entity/Livro.java


import com.example.bibliosys.domain.exception.RegraNegocioException;
import java.time.Year;

/**
 * Representa um livro no acervo da biblioteca.
 * Contém regras de negócio para validação dos seus dados.
 */
public class Livro {

    private String isbn; // ISBN-13
    private String titulo;
    private String autor;
    private String editora;
    private int anoPublicacao;
    private int numeroDeCopias;

    public Livro(String isbn, String titulo, String autor, String editora, int anoPublicacao, int numeroDeCopias) {
        setIsbn(isbn);
        setTitulo(titulo);
        setAutor(autor);
        setEditora(editora);
        setAnoPublicacao(anoPublicacao);
        setNumeroDeCopias(numeroDeCopias);
    }

    // Getters e Setters com validações

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        if (isbn == null || !isbn.matches("^\\d{13}$")) {
            throw new IllegalArgumentException("ISBN deve conter 13 dígitos numéricos.");
        }
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("Título é obrigatório.");
        }
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        if (autor == null || autor.trim().isEmpty()) {
            throw new IllegalArgumentException("Autor é obrigatório.");
        }
        this.autor = autor;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        if (editora == null || editora.trim().isEmpty()) {
            throw new IllegalArgumentException("Editora é obrigatória.");
        }
        this.editora = editora;
    }

    public int getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(int anoPublicacao) {
        if (anoPublicacao > Year.now().getValue()) {
            throw new IllegalArgumentException("Ano de publicação não pode ser um ano futuro.");
        }
        this.anoPublicacao = anoPublicacao;
    }

    public int getNumeroDeCopias() {
        return numeroDeCopias;
    }

    public void setNumeroDeCopias(int numeroDeCopias) {
        if (numeroDeCopias < 0) {
            throw new IllegalArgumentException("Número de cópias não pode ser negativo.");
        }
        this.numeroDeCopias = numeroDeCopias;
    }

    // Métodos de negócio

    /**
     * Verifica se há cópias disponíveis para empréstimo.
     * @return true se o número de cópias for maior que zero.
     */
    public boolean possuiCopiasDisponiveis() {
        return this.numeroDeCopias > 0;
    }

    /**
     * Decrementa o número de cópias disponíveis ao realizar um empréstimo.
     * Lança uma exceção se não houver cópias disponíveis.
     */
    public void decrementarCopias() {
        if (!possuiCopiasDisponiveis()) {
            throw new RegraNegocioException("Não há cópias disponíveis para empréstimo.");
        }
        this.numeroDeCopias--;
    }

    /**
     * Incrementa o número de cópias disponíveis ao receber uma devolução.
     */
    public void incrementarCopias() {
        this.numeroDeCopias++;
    }
}