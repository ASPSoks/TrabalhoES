# Projeto BIBLIO-SYS: Camada de Domínio

Esta pasta contém o código-fonte da camada de domínio para o Sistema de Gestão de Biblioteca (BIBLIO-SYS), implementado em Java. A camada de domínio é o coração do software, encapsulando as regras de negócio, as entidades e a lógica central do sistema.

## Estrutura de Pacotes

-   `com.example.bibliosys.domain.entity`: Contém as classes que representam as entidades principais do sistema.
-   `com.example.bibliosys.domain.enums`: Contém os tipos enumerados usados no domínio.
-   `com.example.bibliosys.domain.exception`: Contém exceções customizadas para o tratamento de erros de negócio.
-   `com.example.bibliosys.domain.service`: Contém os serviços de domínio, que orquestram a lógica de negócio que envolve múltiplas entidades.

## Descrição das Classes

### Entidades (`/entity`)

-   **`Livro.java`**: Representa um livro no acervo. Contém atributos como `isbn`, `titulo`, `autor` e `numeroDeCopias`. Inclui métodos de negócio para gerenciar a disponibilidade de cópias (`decrementarCopias`, `incrementarCopias`). As validações dos campos (ex: ISBN-13, ano não futuro) são aplicadas nos construtores e setters.

-   **`Usuario.java`**: Classe abstrata que serve como base para os diferentes tipos de usuários do sistema. Define atributos comuns como `nome`, `cpf` e `perfil`.

-   **`Membro.java`**: Especialização de `Usuario`. Representa um membro da comunidade acadêmica (aluno, professor) apto a realizar empréstimos. Gerencia uma lista de `Emprestimo` e controla o status de pendências, além de possuir um método para verificar o limite de empréstimos ativos.

-   **`Emprestimo.java`**: Modela a transação de empréstimo, estabelecendo um relacionamento entre um `Membro` e um `Livro`. Controla as datas de empréstimo e devolução e o estado do empréstimo (ativo ou finalizado).

### Enums (`/enums`)

-   **`PerfilUsuario.java`**: Define os perfis de acesso do sistema (`BIBLIOTECARIO`, `MEMBRO`), conforme o requisito de autenticação.

### Exceções (`/exception`)

-   **`RegraNegocioException.java`**: Exceção customizada utilizada para sinalizar violações das regras de negócio do domínio (ex: tentar emprestar um livro indisponível).

### Serviços de Domínio (`/service`)

-   **`EmprestimoService.java`**: Classe de serviço que contém a lógica para a operação de `realizarEmprestimo`. Ela valida todas as regras de negócio que dependem de múltiplas entidades (Membro e Livro), como verificar pendências, limite de livros e disponibilidade de cópias, orquestrando as mudanças de estado nos objetos de domínio.