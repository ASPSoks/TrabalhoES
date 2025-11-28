-- Tabela para a entidade Livro
-- Utiliza o ISBN-13 como chave primária, pois é um identificador único para livros
CREATE TABLE Livro (
    isbn VARCHAR(13) PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    autor VARCHAR(255) NOT NULL,
    editora VARCHAR(255) NOT NULL,
    ano_publicacao INT NOT NULL,
    numero_de_copias INT NOT NULL
);

-- Tabela para a entidade Usuario. A classe Membro herda de Usuario.
-- Aqui, usamos uma única tabela e o campo perfil para diferenciar os tipos de usuário,
-- uma estratégia comum de mapeamento de herança chamada "Table per Class Hierarchy".
CREATE TABLE Usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    perfil VARCHAR(50) NOT NULL, -- Enum de PerfilUsuario
    matricula VARCHAR(50) UNIQUE, -- Matrícula é específica do Membro
    possui_pendencias BOOLEAN NOT NULL DEFAULT FALSE
);

-- Tabela para a entidade Emprestimo
-- Cria um relacionamento entre Membro (como Usuario) e Livro
CREATE TABLE Emprestimo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_membro INT,
    isbn_livro VARCHAR(13),
    data_emprestimo DATE NOT NULL,
    data_devolucao_prevista DATE NOT NULL,
    data_devolucao_real DATE,
    FOREIGN KEY (id_membro) REFERENCES Usuario(id),
    FOREIGN KEY (isbn_livro) REFERENCES Livro(isbn)
);
