# 🏆 Loyalty Rewards — GitHub Copilot Agents Workshop

![Build Status](https://github.com/your-org/GitHubCopilot-Agents/actions/workflows/ci.yml/badge.svg)
![Java](https://img.shields.io/badge/Java-17-blue?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-brightgreen?logo=springboot)
![License](https://img.shields.io/badge/License-MIT-yellow)

---

## 📖 Overview

This repository is a **hands-on workshop** that teaches you how to use **GitHub Copilot Agents** on a real-world Java Spring Boot application. You'll work with a loyalty rewards system — a points-based programme where customers earn points, unlock tiers, and redeem offers — while practising AI-assisted development across five key workflows:

| Workflow | Agent | What You Learn |
|----------|-------|----------------|
| 💻 Code Implementation | Code Implementation Agent | Complete business logic with Copilot |
| 🔍 Code Review | PR Review Agent | Review PRs and identify bugs with AI |
| 🧪 Test Generation | Testing Agent | Write JUnit 5 + Mockito tests faster |
| ♻️ Refactoring | Refactoring Agent | Eliminate code smells with AI guidance |
| 📚 Documentation | Documentation Agent | Generate JavaDoc and OpenAPI docs |

---

## ⚡ Quick Start

Five steps to get up and running:

```bash
# 1. Clone the repository
git clone https://github.com/your-org/GitHubCopilot-Agents.git
cd GitHubCopilot-Agents

# 2. Build the project
mvn clean package -DskipTests

# 3. Run the application
mvn spring-boot:run

# 4. Verify it works
curl http://localhost:8080/api/customers

# 5. Run the tests
mvn test
```

The application starts at **http://localhost:8080**.  
H2 Database Console: **http://localhost:8080/h2-console** (JDBC URL: `jdbc:h2:mem:loyaltydb`, user: `sa`, no password)

For full installation details, see the [Setup Guide](docs/setup-guide.md).

---

## 📁 Repository Structure

```
GitHubCopilot-Agents/
├── 📂 src/
│   ├── main/java/com/loyalty/rewards/
│   │   ├── controller/          # REST controllers (Customer, Offer, Transaction)
│   │   ├── domain/              # JPA entities (Customer, Offer, Transaction, LoyaltyAccount)
│   │   ├── dto/                 # Request/Response DTOs
│   │   ├── exception/           # Custom exceptions + GlobalExceptionHandler
│   │   ├── repository/          # Spring Data JPA repositories
│   │   └── service/             # Business logic services ⬅️ most exercises happen here
│   ├── main/resources/
│   │   ├── application.properties
│   │   └── data.sql             # Sample data loaded on startup
│   └── test/                    # JUnit 5 + Mockito tests
│
├── 🤖 .github/                  # Copilot customisations (loaded automatically)
│   ├── copilot-instructions.md  # Repo-wide rules injected into every Copilot request
│   ├── agents/                  # Design docs for the six agents + build-it-yourself guides
│   │   ├── README.md
│   │   ├── coding-agent-setup.md       # Customising the cloud Copilot coding agent
│   │   ├── code-implementation-agent.md
│   │   ├── pr-review-agent.md
│   │   ├── documentation-agent.md
│   │   ├── testing-agent.md
│   │   ├── design-architecture-agent.md
│   │   └── refactoring-agent.md
│   ├── prompts/                 # Reference *.prompt.md files (invoked via Chat `/`)
│   └── chatmodes/               # Reference *.chatmode.md files (persona + tool-set)
│
├── 🎓 scenarios/                # Hands-on exercises
│   ├── README.md                # Scenario index with difficulty + time
│   ├── scenario-00-create-an-agent.md    # Author your first Copilot agent
│   ├── scenario-01-complete-feature.md
│   ├── scenario-02-pr-review.md
│   ├── scenario-03-generate-tests.md
│   ├── scenario-04-refactor-code.md
│   └── scenario-05-document-api.md
│
├── 📖 docs/                     # Project documentation
│   ├── setup-guide.md           # Prerequisites, install, troubleshooting
│   ├── domain-model.md          # Business context, entities, rules
│   └── api-reference.md         # REST API reference with examples
│
└── ⚙️ .github/workflows/
    ├── ci.yml                   # Build + test on push/PR to main
    └── pr-validation.yml        # Fast compile + test on every PR
```

---

## 🗺️ Domain Overview

The **Loyalty Rewards System** rewards customers for purchases:

- 🛒 Customers earn **10 points per $1** spent
- 🏅 Points unlock tiers: **BRONZE** (0–999) → **SILVER** (1,000–4,999) → **GOLD** (5,000+)
- 🎁 Customers can redeem points against eligible **Offers**
- 🔐 Offers have eligibility rules: minimum points, target tier, and validity dates

Read the full [Domain Model](docs/domain-model.md) before starting the exercises.

---

## 🤖 Available AI Agents

Each agent has a **design doc** under `.github/agents/` (the spec) and a **reference
prompt file** under `.github/prompts/` (the real, invokable Copilot customisation).
The repo-wide rules all agents share live in [`.github/copilot-instructions.md`](.github/copilot-instructions.md).

| Agent | Design doc | Reference prompt | Purpose |
|-------|------------|------------------|---------|
| 💻 Code Implementation | [design](.github/agents/code-implementation-agent.md) | [prompt](.github/prompts/code-implementation.prompt.md) | Complete TODO methods and implement business logic |
| 🔍 PR Review | [design](.github/agents/pr-review-agent.md) | [prompt](.github/prompts/pr-review.prompt.md) | Identify bugs, quality issues, and missing tests in PRs |
| 📚 Documentation | [design](.github/agents/documentation-agent.md) | [prompt](.github/prompts/documentation.prompt.md) | Generate JavaDoc and OpenAPI annotations |
| 🧪 Testing | [design](.github/agents/testing-agent.md) | [prompt](.github/prompts/testing.prompt.md) | Generate JUnit 5 + Mockito test classes |
| 🏛️ Design & Architecture | [design](.github/agents/design-architecture-agent.md) | [prompt](.github/prompts/design-architecture.prompt.md) | Analyse architecture and recommend patterns |
| ♻️ Refactoring | [design](.github/agents/refactoring-agent.md) | [prompt](.github/prompts/refactoring.prompt.md) | Eliminate code smells and improve code quality |

Two reference **chat modes** are also provided for multi-turn sessions:
[`senior-java-dev.chatmode.md`](.github/chatmodes/senior-java-dev.chatmode.md) and
[`code-reviewer.chatmode.md`](.github/chatmodes/code-reviewer.chatmode.md).

### How to Build an Agent (recommended starting point)

Follow [Scenario 0 — Author your first Copilot agent](scenarios/scenario-00-create-an-agent.md).
By the end of it you will have committed a working `*.prompt.md` and `*.chatmode.md`
to this repo, and you will understand the difference between repo-wide
instructions, prompt files, chat modes, and the Copilot coding agent.

### How to Use an Existing Agent

1. Open **GitHub Copilot Chat** in VS Code (`Ctrl+Shift+I`).
2. **Prompt file:** type `/` in the chat input and pick the prompt you want
   (e.g. `/testing`), then add your task.
3. **Chat mode:** use the chat-mode picker (next to Ask / Edit / Agent) to select
   a persona such as **Senior Java Developer** for a multi-turn session.
4. The repository instructions in [`.github/copilot-instructions.md`](.github/copilot-instructions.md)
   are attached to every request automatically — you never paste them manually.

---

## 🎓 Learning Scenarios

| # | Scenario | Difficulty | Time | Agent |
|---|----------|-----------|------|-------|
| 00 | [Author Your First Copilot Agent](scenarios/scenario-00-create-an-agent.md) | 🟢 Beginner | 25–35 min | *you build one* |
| 01 | [Complete the Eligibility Feature](scenarios/scenario-01-complete-feature.md) | 🟢 Beginner | 20–30 min | Code Implementation |
| 02 | [Review a Pull Request with AI](scenarios/scenario-02-pr-review.md) | 🟡 Intermediate | 25–35 min | PR Review |
| 03 | [Generate Tests for EligibilityService](scenarios/scenario-03-generate-tests.md) | 🟡 Intermediate | 30–40 min | Testing |
| 04 | [Refactor the Transaction Service](scenarios/scenario-04-refactor-code.md) | 🟡 Intermediate | 30–40 min | Refactoring |
| 05 | [Document the Loyalty Rewards API](scenarios/scenario-05-document-api.md) | 🟢 Beginner | 20–30 min | Documentation |

**Recommended order for a full workshop:** **00** → 01 → 03 → 02 → 04 → 05
(build the agents once, then use them throughout the rest of the exercises).

See the [Scenarios Index](scenarios/README.md) for learning paths and prerequisites.

---

## 🚀 How to Use GitHub Copilot Agents

### Inline Completions
Open any `.java` file and start typing. Copilot suggests completions — press `Tab` to accept.

### Copilot Chat
Open the chat panel and ask questions about the code:
- *"Explain what EligibilityService does"*
- *"Why does hasMinimumPoints return true when loyaltyPoints equals minPoints?"*
- *"What tests are missing from CustomerServiceTest?"*

### Using `@workspace`
Prefix your prompt with `@workspace` to give Copilot access to your full project:
```
@workspace Complete the isEligibleForOffer method using the helper methods in the class
```

### Custom Agents (prompt files & chat modes)
Activate one of the six agents shipped in `.github/prompts/` (via the `/` picker)
or `.github/chatmodes/` (via the mode picker). Each agent is a file you can read,
edit, and commit — see [Scenario 0](scenarios/scenario-00-create-an-agent.md) to
build your own from scratch.

---

## 📚 Documentation

| Document | Description |
|----------|-------------|
| [Setup Guide](docs/setup-guide.md) | Installation, running, troubleshooting |
| [Domain Model](docs/domain-model.md) | Business rules, entities, ERD |
| [API Reference](docs/api-reference.md) | REST endpoints with request/response examples |
| [Repository Copilot Instructions](.github/copilot-instructions.md) | Shared project rules attached to every Copilot request |
| [Agents Index](.github/agents/README.md) | Design docs for all six agents + build-it-yourself guides |
| [Reference Prompts](.github/prompts/) | Invokable `*.prompt.md` files |
| [Reference Chat Modes](.github/chatmodes/) | Reusable `*.chatmode.md` personas |
| [Cloud Coding Agent Setup](.github/agents/coding-agent-setup.md) | Customising the autonomous Copilot coding agent |
| [Scenarios Index](scenarios/README.md) | All hands-on exercises |

---

## 🤝 Contributing

Contributions are welcome! To contribute:

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/my-improvement`
3. Make your changes and ensure `mvn test` passes
4. Open a pull request with a clear description

Please use the [PR Review Agent](.github/agents/pr-review-agent.md) to self-review your PR before submitting.

---

## 📄 License

This project is licensed under the **MIT License** — see [LICENSE](LICENSE) for details.

---

*Built for GitHub Copilot Agents workshops. Happy coding! 🤖✨*
