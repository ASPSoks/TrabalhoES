# Protótipos - BIBLIO-01: Sistema de Gestão de Biblioteca

Este repositório contém os protótipos navegáveis (HTML/CSS/JS) para as principais funcionalidades do sistema de gestão de biblioteca **BIBLIO-01**. O objetivo é simular a interface do usuário e o fluxo de trabalho para os requisitos definidos.

## Funcionalidades Principais

Os protótipos cobrem os seguintes requisitos funcionais:

* **RFB002 - Manter Cadastro de Livros:** Permite o cadastro e a gestão de novos títulos no acervo. As validações incluem ISBN-13, ano de publicação (não pode ser futuro) e número de cópias.
* **RFB004 - Consultar Acervo:** Oferece uma interface para buscar e visualizar livros do acervo. A busca pode ser feita por Título, Autor ou ISBN.
* **RFB005 - Realizar Empréstimo:** Simula o processo de empréstimo de livros a um membro, com validações de regra de negócio, como limite de empréstimos por membro e disponibilidade de cópias.

## Como Visualizar

Para interagir com os protótipos, abra o arquivo `index.html` em qualquer navegador web moderno (Chrome, Firefox, Edge).

## Notas Técnicas

* **Sem Backend:** Todos os dados são simulados diretamente no navegador usando JavaScript e armazenados "in-memory". As informações não são salvas de forma permanente após o fechamento da página.
* **Regras de Negócio Simuladas:** As seguintes regras foram implementadas:
    * **ISBN:** deve ter 13 dígitos numéricos e ser único.
    * **Ano de Publicação:** não pode ser posterior a 2025.
    * **Cópias:** o número de cópias deve ser um inteiro maior ou igual a 1.
    * **Limite de Empréstimos:** cada membro pode ter um máximo de 5 livros emprestados.
    * **Disponibilidade:** o empréstimo só é permitido se houver cópias disponíveis do livro.
* **Usabilidade:** O protótipo foi projetado para ser intuitivo e consistente, atendendo aos requisitos de usabilidade e compatibilidade. A performance é simulada para ser imediata, em linha com o requisito de resposta em até 3 segundos.