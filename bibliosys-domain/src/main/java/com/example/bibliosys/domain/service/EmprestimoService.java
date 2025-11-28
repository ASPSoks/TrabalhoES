// src/main/java/com/example/bibliosys/domain/service/EmprestimoService.java
package com.example.bibliosys.domain.service;

import com.example.bibliosys.domain.entity.Emprestimo;
import com.example.bibliosys.domain.entity.Livro;
import com.example.bibliosys.domain.entity.Membro;
import com.example.bibliosys.domain.exception.RegraNegocioException;

/**
 * Serviço de domínio responsável por orquestrar as operações de empréstimo,
 * aplicando as regras de negócio que envolvem múltiplas entidades.
 */
public class EmprestimoService {

    private static final int LIMITE_MAXIMO_LIVROS_POR_MEMBRO = 5;

    /**
     * Realiza a operação de empréstimo, validando todas as regras de negócio.
     * @param membro O membro que está solicitando o empréstimo.
     * @param livro O livro a ser emprestado.
     * @return A instância do Empréstimo criado.
     */
    public Emprestimo realizarEmprestimo(Membro membro, Livro livro) {
        // Validação RNB005-1
        if (membro.hasPendencias()) {
            throw new RegraNegocioException("Não é possível realizar um empréstimo para um membro com pendências.");
        }

        // Validação RNB005-2
        if (membro.contarEmprestimosAtivos() >= LIMITE_MAXIMO_LIVROS_POR_MEMBRO) {
            throw new RegraNegocioException("Um membro não pode ter mais de " + LIMITE_MAXIMO_LIVROS_POR_MEMBRO + " livros emprestados simultaneamente.");
        }

        // Validação RNB005-3
        if (!livro.possuiCopiasDisponiveis()) {
            throw new RegraNegocioException("Não é possível emprestar um livro que não possui cópias disponíveis.");
        }

        // Se todas as regras forem satisfeitas, o estado dos objetos é modificado
        livro.decrementarCopias();
        Emprestimo novoEmprestimo = new Emprestimo(membro, livro);
        membro.adicionarEmprestimo(novoEmprestimo);

        return novoEmprestimo;
    }

    /**
     * Realiza a operação de devolução de um livro.
     * @param emprestimo O empréstimo a ser finalizado.
     */
    public void realizarDevolucao(Emprestimo emprestimo) {
        if (!emprestimo.isAtivo()) {
            throw new RegraNegocioException("Este empréstimo já foi finalizado.");
        }
        
        emprestimo.finalizar();
        emprestimo.getLivro().incrementarCopias();
    }
}