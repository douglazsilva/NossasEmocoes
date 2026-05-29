# NossasEmocoes

Aplicativo mobile gamificado desenvolvido para auxiliar estudantes com Transtorno do Espectro Autista (TEA) no reconhecimento de emoções faciais por meio de interações lúdicas, acessíveis e educativas.

O projeto foi desenvolvido utilizando Android nativo com Jetpack Compose e tem como foco o desenvolvimento socioemocional, inclusão digital e apoio ao Atendimento Educacional Especializado (AEE).

---

# Objetivo do Projeto

O Emotion Explorer Compose busca estimular habilidades de:

* reconhecimento de emoções faciais;
* interpretação visual;
* associação emocional;
* comunicação socioemocional;
* aprendizagem interativa e gamificada.

A proposta surgiu a partir da necessidade observada no contexto escolar de ferramentas acessíveis voltadas ao desenvolvimento emocional de estudantes com TEA.

---

# Público-Alvo

O aplicativo é voltado principalmente para:

* estudantes com Transtorno do Espectro Autista (TEA);
* professores do Atendimento Educacional Especializado (AEE);
* instituições educacionais;
* projetos de inclusão digital;
* profissionais da Educação Especial.

---

# Funcionalidades

## MVP Atual

* Tela inicial interativa;
* Seleção de níveis;
* Sistema de perguntas e respostas;
* Identificação de emoções faciais;
* Feedback visual imediato;
* Sistema gamificado;
* Progressão de fases;
* Sistema de medalhas/conquistas;
* Navegação entre telas;
* Interface moderna e acessível.

## Funcionalidades Futuras

* Reconhecimento facial por câmera;
* Integração com Inteligência Artificial;
* Relatórios pedagógicos;
* Acompanhamento de desempenho;
* Sons e recursos de acessibilidade;
* Expansão de emoções e contextos sociais;
* Versão multiplataforma.

---

# Tecnologias Utilizadas

## Mobile

* Kotlin
* Android SDK
* Jetpack Compose
* Material Design 3
* Android ViewModel
* Navigation Compose

## Ferramentas

* Android Studio
* Gradle Kotlin DSL
* JVM 17

---

# Estrutura do Projeto

```text
app/
 ├── src/main/java/com/our/emotions/
 │
 │   ├── ui/
 │   │   ├── navigation/
 │   │   ├── screens/
 │   │   ├── viewmodel/
 │   │   └── theme/
 │   │
 │   ├── data/
 │   ├── model/
 │   └── MainActivity.kt
 │
 ├── res/
 └── build.gradle.kts
```

---

# Principais Telas

O projeto possui as seguintes telas:

* HomeScreen
* LevelSelectionScreen
* GameplayScreen
* FeedbackScreen
* BadgesScreen
* ProfileScreen
* JourneyScreen

---

# Arquitetura

O aplicativo segue uma arquitetura baseada em:

* separação de responsabilidades;
* gerenciamento de estado com ViewModel;
* navegação desacoplada;
* componentes reutilizáveis;
* interface declarativa com Compose.

---

# Requisitos

## Requisitos mínimos

* Android 7.0 (API 24)
* Android Studio Hedgehog ou superior
* JDK 17

---

# Como Executar o Projeto

## 1. Clone o repositório

```bash
git clone <URL_DO_REPOSITORIO>
```

## 2. Abra no Android Studio

Abra a pasta do projeto:

```text
NossasEmocoes
```

## 3. Sincronize o Gradle

O Android Studio irá baixar automaticamente as dependências.

## 4. Execute o aplicativo

* Conecte um dispositivo Android;
* ou utilize um emulador;
* clique em Run ▶.

---

# Dependências Principais

```kotlin
implementation("androidx.activity:activity-compose:1.8.2")
implementation("androidx.compose.material3:material3")
implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")
implementation("androidx.compose.material:material-icons-extended")
```

---

# Gamificação

O aplicativo utiliza elementos de gamificação para aumentar o engajamento:

* níveis;
* medalhas;
* feedback visual;
* progressão;
* reforço positivo.

Esses elementos auxiliam no aprendizado contínuo de forma lúdica e interativa.

---

# Inclusão e Acessibilidade

O projeto foi pensado considerando:

* interface intuitiva;
* estímulos visuais claros;
* simplicidade de navegação;
* apoio ao desenvolvimento socioemocional;
* tecnologia assistiva aplicada ao ambiente escolar.

---

# Validação Inicial

O MVP foi testado com estudantes atendidos pelo AEE.

Os testes iniciais demonstraram:

* boa aceitação da ferramenta;
* engajamento dos estudantes;
* participação ativa nas atividades;
* potencial de apoio ao reconhecimento emocional.

---

# Impacto Esperado

O projeto busca contribuir para:

* inclusão digital;
* desenvolvimento socioemocional;
* fortalecimento das habilidades de comunicação;
* apoio pedagógico ao AEE;
* utilização de tecnologia assistiva na educação.

---

# Roadmap

## Próximas melhorias

* [ ] Novas emoções
* [ ] Novos níveis
* [ ] Sons e narração
* [ ] Sistema de relatórios
* [ ] Salvamento de progresso
* [ ] Backend para sincronização
* [ ] Versão multiplataforma
* [ ] IA para reconhecimento facial

---

# Equipe

## Desenvolvedores

* Ana Carolina Ribeiro da Cruz
* Luís Isaías Bezerra Magalhães
* Nayra Stefanny Pereira de Carvalho
* Victor Emanuel Da Silva Pereira
* Yasmin Barboza Rodrigues Da Costa Nunes Silva

## Orientação

* Francisco Douglas Castro da Silva

---

# Projeto Acadêmico

Projeto desenvolvido para iniciativas educacionais e tecnológicas voltadas à inclusão digital e acessibilidade.

---

# Licença

## Código-fonte
Este projeto está licenciado sob a MIT License.

Consulte o arquivo LICENSE para mais detalhes.