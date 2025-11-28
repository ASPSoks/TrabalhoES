package com.example.bibliosys.domain.entity;

import com.example.bibliosys.domain.enums.PerfilUsuario;

/**
 * Classe base que representa um usuário do sistema.
 */
public abstract class Usuario {

    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String senha; // Em um sistema real, seria armazenado como hash
    private PerfilUsuario perfil;

    public Usuario(String nome, String cpf, String email, PerfilUsuario perfil) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.perfil = perfil;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public PerfilUsuario getPerfil() {
        return perfil;
    }
}
