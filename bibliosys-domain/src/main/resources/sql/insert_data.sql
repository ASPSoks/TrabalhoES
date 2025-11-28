-- Inserção de dados iniciais na tabela Livro
INSERT INTO Livro (isbn, titulo, autor, editora, ano_publicacao, numero_de_copias) VALUES
('9788535914848', 'Dom Quixote de La Mancha', 'Miguel de Cervantes', 'Companhia das Letras', 2002, 5),
('9788532509176', 'O Senhor dos Anéis: A Sociedade do Anel', 'J.R.R. Tolkien', 'Martins Fontes', 2001, 3),
('9788535914855', 'Crime e Castigo', 'Fiódor Dostoiévski', 'Editora 34', 2007, 2),
('9788535914862', '1984', 'George Orwell', 'Companhia das Letras', 2009, 1);

-- Inserção de dados iniciais na tabela Usuario (Membros)
-- A senha 'senha123' deve ser tratada com um hash em um sistema de produção
INSERT INTO Usuario (nome, cpf, email, senha, perfil, matricula, possui_pendencias) VALUES
('João da Silva', '111.111.111-11', 'joao.silva@example.com', 'senha123', 'MEMBRO', '2023001', FALSE),
('Maria Souza', '222.222.222-22', 'maria.souza@example.com', 'senha123', 'MEMBRO', '2023002', FALSE);

-- Inserção de dados de exemplo na tabela Emprestimo
-- Um empréstimo de João da Silva para o livro 'Dom Quixote'
INSERT INTO Emprestimo (id_membro, isbn_livro, data_emprestimo, data_devolucao_prevista, data_devolucao_real) VALUES
(1, '9788535914848', '2023-10-25', '2023-11-09', NULL);
