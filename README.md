# 📱 Repositório de Projetos Android

Repositório colaborativo contendo 3 aplicativos Android desenvolvidos individualmente, cada um em sua própria subpasta. O objetivo é centralizar os projetos da equipe, mantendo organização, versionamento e boas práticas de colaboração via Git/GitHub.

## 📂 Estrutura do Repositório

```
.
├── app-01-gestao-leituras/
├── app-02-treinos-performance/
├── app-03-prazos-faturas/
└── README.md
```

---

## 🚀 Projetos

### 1️⃣ App 01 — Gestão de Leituras & Estudos Pessoais
**Pasta:** `app-01-gestao-leituras/`

Aplicativo para organização de leituras e rotina de estudos, desenvolvido em **Jetpack Compose**.

- **UI:** Jetpack Compose
- **Funcionalidades sugeridas:** cadastro de livros/materiais, metas de leitura, acompanhamento de progresso, anotações de estudo

---

### 2️⃣ App 02 — Registro de Treinos & Performance Física
**Pasta:** `app-02-treinos-performance/`

Aplicativo para registro de treinos e acompanhamento de performance física, utilizando arquitetura **MVVM** com persistência local via **Room**.

- **Arquitetura:** MVVM (Model-View-ViewModel)
- **Persistência:** Room Database
- **Funcionalidades sugeridas:** registro de séries/cargas, histórico de treinos, gráficos de evolução

---

### 3️⃣ App 03 — Rastreador de Prazos, Faturas & Cobranças
**Pasta:** `app-03-prazos-faturas/`

Aplicativo para controle de prazos, faturas e cobranças, com **agendamento de notificações** para lembrar o usuário de vencimentos.

- **Funcionalidades sugeridas:** cadastro de contas/faturas, datas de vencimento, notificações agendadas (WorkManager/AlarmManager)

---

## 🔧 Guia de Git & GitHub para o time

Esta seção documenta o fluxo de trabalho recomendado para contribuir com o repositório de forma organizada.

### 1. Clonar o repositório (se já existir no GitHub)

```bash
git clone https://github.com/Raul-Brasiel/Projeto-Integrado-Mobile.git
cd Projeto-Integrado-Mobile
```

### 2. Criar o repositório do zero (caso ainda não exista)

```bash
# Dentro da pasta do projeto
git init
git add .
git commit -m "chore: commit inicial do repositório"

# Conectar ao repositório remoto criado no GitHub
git remote add origin https://github.com/Raul-Brasiel/Projeto-Integrado-Mobile.git
git branch -M main
git push -u origin main
```

### 3. Criar uma branch para sua tarefa

**Nunca trabalhe direto na `main`.** Sempre crie uma branch específica para a sua alteração.

```bash
git checkout -b tipo/nome-da-tarefa
```

Sugestão de convenção de nomes de branch:

| Prefixo    | Uso                                      | Exemplo                          |
|------------|-------------------------------------------|-----------------------------------|
| `feat/`    | Nova funcionalidade                       | `feat/tela-cadastro-livro`       |
| `fix/`     | Correção de bug                           | `fix/crash-lista-treinos`        |
| `chore/`   | Tarefas de manutenção/configuração        | `chore/atualizar-dependencias`   |
| `docs/`    | Alterações em documentação                | `docs/atualizar-readme`          |

### 4. Fazer alterações e commit

```bash
git add .
git commit -m "feat: adiciona tela de cadastro de leitura"
```

Sugestão de convenção de commits (Conventional Commits):

- `feat:` nova funcionalidade
- `fix:` correção de bug
- `docs:` documentação
- `style:` formatação, sem alteração de lógica
- `refactor:` refatoração de código
- `test:` adição/ajuste de testes
- `chore:` tarefas diversas (build, configs, etc.)

### 5. Enviar a branch para o GitHub

```bash
git push origin tipo/nome-da-tarefa
```

Se for a primeira vez enviando essa branch:

```bash
git push -u origin tipo/nome-da-tarefa
```

### 6. Abrir o Pull Request (PR)

1. Acesse o repositório no GitHub
2. Clique em **"Compare & pull request"** (aparece automaticamente após o push)
3. Preencha:
   - **Título:** curto e descritivo (ex: "feat: tela de cadastro de leitura")
   - **Descrição:** o que foi feito, por que, e como testar
4. Selecione a branch de destino (geralmente `main` ou `develop`)
5. Adicione revisores (colegas do time)
6. Clique em **"Create pull request"**

### 7. Revisão e merge

- Ao menos 1 colega deve revisar e aprovar o PR antes do merge
- Corrija os comentários da revisão, se houver, fazendo novos commits na mesma branch (o PR é atualizado automaticamente)
- Após aprovação, faça o merge (prefira **Squash and merge** para manter o histórico limpo)
- Delete a branch após o merge (o próprio GitHub oferece essa opção)

### 8. Manter sua branch local atualizada

Antes de começar uma nova tarefa, sempre atualize a `main` local:

```bash
git checkout main
git pull origin main
git checkout -b feat/nova-tarefa
```

---

## ✅ Boas práticas gerais

- Sempre criar uma branch por tarefa/funcionalidade
- Commits pequenos e com mensagens claras
- Nunca dar push direto na `main`
- Revisar o PR de um colega antes de aprovar
- Manter cada app isolado em sua subpasta, sem misturar dependências entre projetos
- Adicionar um `README.md` específico dentro de cada subpasta de app, detalhando particularidades daquele projeto (telas, bibliotecas específicas, como rodar, etc.)
- Manter o `.gitignore` adequado para projetos Android (pastas `build/`, `.gradle/`, `local.properties`, etc.)
