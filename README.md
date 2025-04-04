# GameTracker

![Java CI](https://img.shields.io/badge/Java-11-blue)
![License: MIT](https://img.shields.io/badge/License-MIT-green)

GameTracker é uma aplicação desktop desenvolvida em Java que permite gerenciar seus jogos e conquistas de maneira simples, moderna e prática. Com uma interface gráfica refinada e responsiva, você pode acompanhar o seu progresso, salvar suas informações e retomar de onde parou sempre que desejar.

---

## Sumário

- [Funcionalidades](#funcionalidades)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Instalação](#instalação)
- [Uso](#uso)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Melhorias Futuras](#melhorias-futuras)
- [Licença](#licença)
- [Contato](#contato)

---

## Funcionalidades

- **Gerenciamento de Jogos:** Adicione, visualize e remova jogos com facilidade.
- **Gerenciamento de Conquistas:** Associe conquistas aos jogos e marque-as como concluídas.
- **Persistência de Dados:** O progresso é salvo automaticamente em um arquivo serializado, permitindo que suas informações sejam recuperadas mesmo após fechar o programa.
- **Interface Moderna e Responsiva:** Utiliza o Nimbus LookAndFeel para uma experiência visual aprimorada, com menus intuitivos, renderizadores customizados e uma barra de status para feedback em tempo real.

---

## Tecnologias Utilizadas

- **Java SE**
- **Swing** (para a interface gráfica)
- **Serialização** (para persistência de dados)

---

## Instalação

### Pré-requisitos

- **JDK 8 ou superior**

### Passos para Compilar e Executar

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/seuusuario/GameTracker.git
   ```

2. **Navegue até o diretório do projeto:**
   ```bash
   cd GameTracker
   ```

3. **Compile os arquivos Java:**
   ```bash
   javac *.java
   ```

4. **Execute a aplicação:**
   ```bash
   java Main
   ```

---

## Uso

Ao iniciar o GameTracker, a interface gráfica moderna será exibida, permitindo que você:

- **Adicione Jogos:** Insira o nome do jogo e comece a gerenciar suas conquistas.
- **Adicione Conquistas:** Associe conquistas específicas aos seus jogos e acompanhe o progresso.
- **Marque Conquistas como Concluídas:** Clique para atualizar o status das conquistas e visualize o progresso atualizado na barra de status.
- **Salvamento Automático:** Todos os dados são salvos automaticamente quando a aplicação é fechada, garantindo a continuidade do seu progresso.

---

## Estrutura do Projeto(Ainda não implementado)

```plaintext
GameTracker/
├── Achievement.java         // Representa uma conquista (com persistência).
├── Game.java                // Representa um jogo e gerencia suas conquistas.
├── GameTracker.java         // Gerencia a lista de jogos.
├── GameCellRenderer.java    // Renderizador customizado para exibir jogos.
├── AchievementCellRenderer.java // Renderizador customizado para exibir conquistas.
├── GameTrackerGUI.java      // Constrói e gerencia a interface gráfica.
└── Main.java                // Ponto de entrada da aplicação.
```

---

## Melhorias Futuras

- **Edição de Itens:** Permitir a edição dos nomes de jogos e conquistas.
- **Filtros e Relatórios:** Adicionar filtros para visualizar jogos por status e gerar relatórios estatísticos do progresso.
- **Temas Dinâmicos:** Implementar a troca de temas para personalização da interface.
- **Integração com Banco de Dados:** Migrar a persistência para um banco de dados para maior escalabilidade e segurança.

---


