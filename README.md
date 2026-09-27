# Projeto Integrado de Desenvolvimento Mobile

Repositório colaborativo contendo 3 aplicativos Android desenvolvidos por uma equipe de 3 alunos. O objetivo é centralizar os projetos da equipe, mantendo organização, versionamento e boas práticas de colaboração via Git/GitHub.

## 👥 Equipe e Colaboração
- **Raul** ([Raul-Brasiel](https://github.com/Raul-Brasiel))
- **Paulo Henrique** ([Paulo-Silva18](https://github.com/Paulo-Silva18))
- **Sâmara Ahyeska** ([Samara-Ahyeska](https://github.com/Samara-Ahyeska))

**Dinâmica de Trabalho:** O escopo original do projeto previa duplas, mas foi adaptado para o nosso trio. A maior parte do desenvolvimento de cada aplicativo foi liderada por um integrante específico. Contudo, para garantir o aspecto cooperativo, cada membro realizou contribuições cruzadas, efetuando commits nos projetos liderados pelos colegas (adicionando novas funções ou realizando correções).

## 📁 Estrutura do Repositório e Aplicativos

O ecossistema é composto por 3 aplicativos complementares que compartilham a mesma identidade visual e estão organizados em módulos independentes.

### 📚 App 01: Gestão de Leituras & Estudos Pessoais
- **Diretório:** `app-01-gestao-leituras/`
- **Foco:** Jetpack Compose + MVVM + Room
- **Objetivo:** Interfaces 100% declarativas integradas a um fluxo reativo de dados e persistência local.
- **Principais Funcionalidades:**
  - **Catálogo e Cadastro:** Cadastro completo de livros (título, autor, páginas, gênero e capa).
  - **Status de Progresso:** Controle de leitura (Quero Ler, Lendo e Lido).
  - **Diário & Notas:** Registro de anotações, insights e citações associadas.
  - **Filtros e Persistência:** Consulta filtrada por gênero/status offline via Room.

### 🏋️ App 02: Registro de Treinos & Performance Física
- **Diretório:** `app-02-treinos-performance/`
- **Foco:** MVVM + Room Relacional + Listagens Dinâmicas
- **Objetivo:** Modelagem relacional em banco de dados, estados assíncronos e renderização de listas complexas.
- **Principais Funcionalidades:**
  - **Fichas de Treino:** Cadastro de rotinas (Treino A, B, C) e exercícios.
  - **Histórico de Cargas:** Edição e registro de cargas, séries e repetições.
  - **Módulo de Corrida:** Registro de atividades aeróbicas (distância, tempo, pace).
  - **Filtros e Busca:** Pesquisa e filtros por grupamento muscular.

### ⏰ App 03: Rastreador de Prazos, Faturas & Cobranças
- **Diretório:** `app-03-prazos-faturas/`
- **Foco:** WorkManager + Notificações Locais + Date/TimePickers
- **Objetivo:** Agendamento de tarefas em segundo plano e disparos de notificações respeitando as diretrizes do Android.
- **Principais Funcionalidades:**
  - **Registro de Obrigações:** Cadastro de prazos e faturas a pagar/receber.
  - **Seleção Interativa:** Seletores nativos de data e horário.
  - **Agendamento em Segundo Plano:** Notificações preventivas (WorkManager/AlarmManager).
  - **Liquidação & Status:** Marcação de status e cancelamento de alertas.

## 🛠️ Tecnologias Utilizadas
- **Linguagem:** Kotlin
- **Arquitetura:** MVVM
- **UI:** Jetpack Compose, XML (RecyclerView)
- **Armazenamento:** Room Database (DAO, Entities, 1:N), DataStore
- **Reatividade:** StateFlow, LiveData
- **Tarefas em Background:** WorkManager, AlarmManager
- **Sistema:** NotificationManager (Android 13+), DatePicker, TimePicker

## 🎥 Vídeo Demonstrativo e Figma
- **Design System no Figma:** ([Figma](https://www.figma.com/design/gp3ZUjtDyFs6NrZFJZSuZT/Sem-t%C3%ADtulo?node-id=0-1&t=RK8mxmutyX30pNk8-1))
