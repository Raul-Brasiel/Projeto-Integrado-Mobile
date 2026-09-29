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

## 🖌️ Design no Figma
- **Design System no Figma:** ([Figma](https://www.figma.com/design/gp3ZUjtDyFs6NrZFJZSuZT/Sem-t%C3%ADtulo?node-id=0-1&t=RK8mxmutyX30pNk8-1))

## 🚀 Instalação e Compilação

### 📋 Pré-requisitos
- **Android Studio** (versão Hedgehog 2023.1.1 ou superior, recomendada a mais recente estável)
- **JDK 17** (já incluído no Android Studio)
- **Git** instalado na máquina
- **Android SDK** com a API 34 (ou superior) instalada via SDK Manager
- Um **emulador Android** (AVD) ou um **dispositivo físico** com a Depuração USB ativada

> ⚠️ O App 03 utiliza notificações locais. Para testar em dispositivos com **Android 13+ (API 33)**, é necessário conceder a permissão de notificações quando o aplicativo solicitar.

### 📥 1. Clonando o Repositório
```bash
git clone https://github.com/Raul-Brasiel/Projeto-Integrado-Mobile.git
cd Projeto-Integrado-Mobile
```

### 📂 2. Abrindo um Aplicativo
Cada aplicativo é um projeto Android independente. **Não abra a pasta raiz do repositório**; abra a pasta do app desejado:

| App | Diretório |
|-----|-----------|
| Gestão de Leituras & Estudos | `app-01-gestao-leituras/` |
| Treinos & Performance Física | `app-02-treinos-performance/` |
| Prazos, Faturas & Cobranças | `app-03-prazos-faturas/` |

No Android Studio, vá em **File > Open**, selecione a pasta do app e aguarde a sincronização do Gradle (**Gradle Sync**) terminar. Na primeira vez, o download das dependências pode levar alguns minutos.

### ▶️ 3. Executando pelo Android Studio
1. Selecione o emulador ou dispositivo na barra superior.
2. Clique em **Run ▶️** (ou pressione `Shift + F10`).
3. Aguarde a compilação e a instalação automática do app.

### 🖥️ 4. Compilando pela Linha de Comando
Dentro da pasta do app desejado (exemplo com o App 01):

```bash
cd app-01-gestao-leituras
```

**Linux / macOS:**
```bash
chmod +x gradlew
./gradlew assembleDebug
```

**Windows:**
```bash
gradlew.bat assembleDebug
```

O APK de debug será gerado em:
```bash
app/build/outputs/apk/debug/app-debug.apk
```

Para instalar diretamente em um dispositivo conectado:
```bash
./gradlew installDebug
```

### 📦 5. Gerando um APK de Release (opcional)
```bash
./gradlew assembleRelease
```
O APK de release precisa ser assinado com uma keystore própria. Consulte a [documentação oficial](https://developer.android.com/studio/publish/app-signing) para mais detalhes.

### 🩺 Solução de Problemas
- **Erro de versão do JDK:** em **File > Settings > Build, Execution, Deployment > Build Tools > Gradle**, defina o *Gradle JDK* como JDK 17.
- **SDK não encontrado:** crie ou ajuste o arquivo `local.properties` na pasta do app com `sdk.dir=/caminho/para/o/Android/Sdk`.
- **Falha no Gradle Sync:** use **File > Invalidate Caches / Restart** e tente sincronizar novamente.
- **Permissão negada no `gradlew`** (Linux/macOS): execute `chmod +x gradlew`.
