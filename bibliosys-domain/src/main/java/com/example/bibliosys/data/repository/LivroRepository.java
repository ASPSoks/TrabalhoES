package com.example.bibliosys.data.repository;

import com.example.bibliosys.domain.entity.Livro;
import com.example.bibliosys.data.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Repositório para operações de persistência da entidade Livro.
 */
public class LivroRepository {

    private final Connection connection;

    public LivroRepository() {
        this.connection = DataSource.getInstance().getConnection();
    }

    /**
     * Salva um novo livro no banco de dados.
     * @param livro O livro a ser salvo.
     */
    public void save(Livro livro) {
        String sql = "INSERT INTO Livro (isbn, titulo, autor, editora, ano_publicacao, numero_de_copias) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, livro.getIsbn());
            stmt.setString(2, livro.getTitulo());
            stmt.setString(3, livro.getAutor());
            stmt.setString(4, livro.getEditora());
            stmt.setInt(5, livro.getAnoPublicacao());
            stmt.setInt(6, livro.getNumeroDeCopias());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar livro.", e);
        }
    }

    /**
     * Busca um livro pelo seu ISBN.
     * @param isbn O ISBN do livro.
     * @return Um Optional contendo o livro, ou vazio se não encontrado.
     */
    public Optional<Livro> findByIsbn(String isbn) {
        String sql = "SELECT * FROM Livro WHERE isbn = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, isbn);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Livro livro = new Livro(
                        rs.getString("isbn"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getString("editora"),
                        rs.getInt("ano_publicacao"),
                        rs.getInt("numero_de_copias")
                    );
                    return Optional.of(livro);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar livro por ISBN.", e);
        }
        return Optional.empty();
    }

    /**
     * Atualiza um livro existente no banco de dados.
     * @param livro O livro com os dados atualizados.
     */
    public void update(Livro livro) {
        String sql = "UPDATE Livro SET titulo = ?, autor = ?, editora = ?, ano_publicacao = ?, numero_de_copias = ? WHERE isbn = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setString(3, livro.getEditora());
            stmt.setInt(4, livro.getAnoPublicacao());
            stmt.setInt(5, livro.getNumeroDeCopias());
            stmt.setString(6, livro.getIsbn());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar livro.", e);
        }
    }
}
