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
├── 🤖 agents/                   # AI agent definitions (system prompts + examples)
│   ├── README.md                # Agent index
│   ├── code-implementation-agent.md
│   ├── pr-review-agent.md
│   ├── documentation-agent.md
│   ├── testing-agent.md
│   ├── design-architecture-agent.md
│   └── refactoring-agent.md
│
├── 🎓 scenarios/                # Hands-on exercises
│   ├── README.md                # Scenario index with difficulty + time
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

| Agent | File | Purpose |
|-------|------|---------|
| 💻 Code Implementation | [agents/code-implementation-agent.md](agents/code-implementation-agent.md) | Complete TODO methods and implement business logic |
| 🔍 PR Review | [agents/pr-review-agent.md](agents/pr-review-agent.md) | Identify bugs, quality issues, and missing tests in PRs |
| 📚 Documentation | [agents/documentation-agent.md](agents/documentation-agent.md) | Generate JavaDoc and OpenAPI annotations |
| 🧪 Testing | [agents/testing-agent.md](agents/testing-agent.md) | Generate JUnit 5 + Mockito test classes |
| 🏛️ Design & Architecture | [agents/design-architecture-agent.md](agents/design-architecture-agent.md) | Analyse architecture and recommend patterns |
| ♻️ Refactoring | [agents/refactoring-agent.md](agents/refactoring-agent.md) | Eliminate code smells and improve code quality |

### How to Use an Agent

1. Open **GitHub Copilot Chat** in VS Code (`Ctrl+Shift+I`) or IntelliJ
2. Open the agent's `.md` file and copy the text from the **"GitHub Copilot Prompt / Instructions"** section
3. Paste it at the top of a new Copilot Chat conversation
4. Ask your task-specific question

---

## 🎓 Learning Scenarios

| # | Scenario | Difficulty | Time | Agent |
|---|----------|-----------|------|-------|
| 01 | [Complete the Eligibility Feature](scenarios/scenario-01-complete-feature.md) | 🟢 Beginner | 20–30 min | Code Implementation |
| 02 | [Review a Pull Request with AI](scenarios/scenario-02-pr-review.md) | 🟡 Intermediate | 25–35 min | PR Review |
| 03 | [Generate Tests for EligibilityService](scenarios/scenario-03-generate-tests.md) | 🟡 Intermediate | 30–40 min | Testing |
| 04 | [Refactor the Transaction Service](scenarios/scenario-04-refactor-code.md) | 🟡 Intermediate | 30–40 min | Refactoring |
| 05 | [Document the Loyalty Rewards API](scenarios/scenario-05-document-api.md) | 🟢 Beginner | 20–30 min | Documentation |

**Recommended order for a full workshop:** 01 → 03 → 02 → 04 → 05

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

### Agent Mode (Custom Instructions)
Paste an agent's system prompt at the start of a chat to get specialised behaviour — the agent acts as a domain expert with specific instructions for your codebase.

---

## 📚 Documentation

| Document | Description |
|----------|-------------|
| [Setup Guide](docs/setup-guide.md) | Installation, running, troubleshooting |
| [Domain Model](docs/domain-model.md) | Business rules, entities, ERD |
| [API Reference](docs/api-reference.md) | REST endpoints with request/response examples |
| [Agents Index](agents/README.md) | All available AI agents |
| [Scenarios Index](scenarios/README.md) | All hands-on exercises |

---

## 🤝 Contributing

Contributions are welcome! To contribute:

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/my-improvement`
3. Make your changes and ensure `mvn test` passes
4. Open a pull request with a clear description

Please use the [PR Review Agent](agents/pr-review-agent.md) to self-review your PR before submitting.

---

## 📄 License

This project is licensed under the **MIT License** — see [LICENSE](LICENSE) for details.

---

*Built for GitHub Copilot Agents workshops. Happy coding! 🤖✨*
