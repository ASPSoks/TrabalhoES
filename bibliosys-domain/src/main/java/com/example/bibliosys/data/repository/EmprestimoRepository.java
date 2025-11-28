package com.example.bibliosys.data.repository;

import com.example.bibliosys.domain.entity.Emprestimo;
import com.example.bibliosys.domain.entity.Livro;
import com.example.bibliosys.domain.entity.Membro;
import com.example.bibliosys.data.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repositório para operações de persistência da entidade Emprestimo.
 */
public class EmprestimoRepository {

    private final Connection connection;

    public EmprestimoRepository() {
        this.connection = DataSource.getInstance().getConnection();
    }

    /**
     * Salva um novo empréstimo no banco de dados.
     * @param emprestimo O empréstimo a ser salvo.
     */
    public void save(Emprestimo emprestimo) {
        String sql = "INSERT INTO Emprestimo (id_membro, isbn_livro, data_emprestimo, data_devolucao_prevista) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, emprestimo.getMembro().getId());
            stmt.setString(2, emprestimo.getLivro().getIsbn());
            stmt.setDate(3, Date.valueOf(emprestimo.getDataEmprestimo()));
            stmt.setDate(4, Date.valueOf(emprestimo.getDataDevolucaoPrevista()));
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    emprestimo.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar empréstimo.", e);
        }
    }

    /**
     * Atualiza um empréstimo existente, geralmente para registrar a data de devolução.
     * @param emprestimo O empréstimo com os dados atualizados.
     */
    public void update(Emprestimo emprestimo) {
        String sql = "UPDATE Emprestimo SET data_devolucao_real = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, emprestimo.getDataDevolucaoReal() != null ? Date.valueOf(emprestimo.getDataDevolucaoReal()) : null);
            stmt.setLong(2, emprestimo.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar empréstimo.", e);
        }
    }

    /**
     * Busca um empréstimo pelo seu ID.
     * @param id O ID do empréstimo.
     * @return Um Optional contendo o empréstimo, ou vazio se não encontrado.
     */
    public Optional<Emprestimo> findById(Long id) {
        String sql = "SELECT * FROM Emprestimo WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // É necessário carregar o Membro e o Livro para criar o objeto de Emprestimo
                    MembroRepository membroRepository = new MembroRepository();
                    LivroRepository livroRepository = new LivroRepository();
                    
                    Optional<Membro> membroOpt = membroRepository.findById(rs.getLong("id_membro"));
                    Optional<Livro> livroOpt = livroRepository.findByIsbn(rs.getString("isbn_livro"));

                    if (membroOpt.isPresent() && livroOpt.isPresent()) {
                        Emprestimo emprestimo = new Emprestimo(membroOpt.get(), livroOpt.get());
                        emprestimo.setId(rs.getLong("id"));
                        emprestimo.setDataDevolucaoReal(rs.getDate("data_devolucao_real") != null ? rs.getDate("data_devolucao_real").toLocalDate() : null);
                        return Optional.of(emprestimo);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar empréstimo por ID.", e);
        }
        return Optional.empty();
    }
    
    /**
     * Busca todos os empréstimos ativos para um determinado membro.
     * @param membro O membro.
     * @return Uma lista de empréstimos ativos.
     */
    public List<Emprestimo> findActiveEmprestimosByMembro(Membro membro) {
        List<Emprestimo> emprestimos = new ArrayList<>();
        String sql = "SELECT * FROM Emprestimo WHERE id_membro = ? AND data_devolucao_real IS NULL";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, membro.getId());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    LivroRepository livroRepository = new LivroRepository();
                    Optional<Livro> livroOpt = livroRepository.findByIsbn(rs.getString("isbn_livro"));
                    
                    if (livroOpt.isPresent()) {
                        Emprestimo emprestimo = new Emprestimo(membro, livroOpt.get());
                        emprestimo.setId(rs.getLong("id"));
                        emprestimos.add(emprestimo);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar empréstimos ativos por membro.", e);
        }
        
        return emprestimos;
    }
}
