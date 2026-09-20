# Aplicativo 3 — Controle de Obrigações

Projeto Android (Kotlin) com as 6 telas do protótipo do Figma:

1. **Home/Lista de Obrigações** — lista com status colorido (pendente/atrasado/paga) + botão flutuante para nova obrigação
2. **Cadastro de Obrigação** — formulário com descrição, valor, favorecido, tipo (pagar/receber) e vencimento
3. **Seleção de Data/Hora** — usa `CalendarView` + `TimePicker` nativos do Android
4. **Detalhes da Obrigação** — mostra os dados completos, com botão "Marcar como paga" e "Configurar notificação"
5. **Configurar Notificação** — escolha de intervalo (no dia / 1 dia antes / 3 dias antes / personalizado)
6. **Lista de Notificações Ativas** — lista das notificações agendadas, com opção de cancelar

A cor e o layout foram feitos para bater com o protótipo enviado (`Aplicativo 1 - Telas | Paulo`): fundo bege, header azul (#185FA5), badges verde/laranja/vermelho para status.

## Como abrir no Android Studio

1. Extraia o `.zip` em uma pasta.
2. No Android Studio: **File > Open** e selecione a pasta `Obrigacoes`.
3. O projeto **não inclui o `gradle-wrapper.jar`** (arquivo binário — não é possível gerá-lo aqui). Na primeira sincronização o Android Studio deve oferecer para baixar o wrapper automaticamente ("Gradle wrapper is missing, would you like to use the Gradle wrapper?" → clique em **OK**). Se isso não aparecer, vá em **File > Settings > Build Tools > Gradle** e selecione uma instalação local do Gradle (versão 8.4), ou rode `gradle wrapper` no terminal dentro da pasta do projeto (precisa ter o Gradle instalado).
4. Depois do sync, rode o app em um emulador ou celular físico (botão ▶️ ou Shift+F10).

## Persistência

Os dados ficam em memória (`ObligationRepository`), reiniciando ao fechar o app — suficiente para demonstrar a navegação e o funcionamento das telas. Se seu professor pedir persistência real, o próximo passo natural é trocar o objeto `ObligationRepository` por um Room Database (SQLite).

## Notificações

Ao ativar uma notificação, o app agenda um alarme real via `AlarmManager` (igual ao pedido original no card do Trello) que dispara uma notificação do sistema no horário calculado. Em Android 12+ pode ser necessário liberar a permissão de "alarmes exatos" nas configurações do sistema para o `AlarmManager.setExactAndAllowWhileIdle` funcionar.
