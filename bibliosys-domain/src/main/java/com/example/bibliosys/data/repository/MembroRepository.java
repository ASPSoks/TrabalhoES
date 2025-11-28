package com.example.bibliosys.data.repository;

import com.example.bibliosys.domain.entity.Membro;
import com.example.bibliosys.data.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

/**
 * Repositório para operações de persistência da entidade Membro.
 */
public class MembroRepository {

    private final Connection connection;

    public MembroRepository() {
        this.connection = DataSource.getInstance().getConnection();
    }

    /**
     * Salva um novo membro no banco de dados.
     * @param membro O membro a ser salvo.
     */
    public void save(Membro membro) {
        String sql = "INSERT INTO Usuario (nome, cpf, email, senha, perfil, matricula, possui_pendencias) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, membro.getNome());
            stmt.setString(2, membro.getCpf());
            stmt.setString(3, membro.getEmail());
            stmt.setString(4, membro.getSenha());
            stmt.setString(5, "MEMBRO");
            stmt.setString(6, membro.getMatricula());
            stmt.setBoolean(7, membro.hasPendencias());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    membro.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar membro.", e);
        }
    }
    
    /**
     * Busca um membro pelo seu ID.
     * @param id O ID do membro.
     * @return Um Optional contendo o membro, ou vazio se não encontrado.
     */
    public Optional<Membro> findById(Long id) {
        String sql = "SELECT * FROM Usuario WHERE id = ? AND perfil = 'MEMBRO'";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Membro membro = new Membro(
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("email"),
                        rs.getString("matricula")
                    );
                    membro.setId(rs.getLong("id"));
                    membro.setPossuiPendencias(rs.getBoolean("possui_pendencias"));
                    return Optional.of(membro);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar membro por ID.", e);
        }
        return Optional.empty();
    }

    /**
     * Busca um membro pela sua matrícula.
     * @param matricula A matrícula do membro.
     * @return Um Optional contendo o membro, ou vazio se não encontrado.
     */
    public Optional<Membro> findByMatricula(String matricula) {
        String sql = "SELECT * FROM Usuario WHERE matricula = ? AND perfil = 'MEMBRO'";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, matricula);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Membro membro = new Membro(
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("email"),
                        rs.getString("matricula")
                    );
                    membro.setId(rs.getLong("id"));
                    membro.setPossuiPendencias(rs.getBoolean("possui_pendencias"));
                    return Optional.of(membro);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar membro por matrícula.", e);
        }
        return Optional.empty();
    }
    
    /**
     * Atualiza um membro existente no banco de dados.
     * @param membro O membro com os dados atualizados.
     */
    public void update(Membro membro) {
        String sql = "UPDATE Usuario SET nome = ?, cpf = ?, email = ?, possui_pendencias = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, membro.getNome());
            stmt.setString(2, membro.getCpf());
            stmt.setString(3, membro.getEmail());
            stmt.setBoolean(4, membro.hasPendencias());
            stmt.setLong(5, membro.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar membro.", e);
        }
    }
}
