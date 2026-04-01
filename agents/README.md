# 🤖 AI Agents Index

This directory contains GitHub Copilot agent definitions for the **Loyalty Rewards** application. Each agent acts as a specialised AI persona with a focused role, a curated system prompt, usage examples, and expected outputs.

---

## 📖 What Is an Agent?

An agent is a **reusable Copilot prompt definition** — a system prompt you activate in GitHub Copilot Chat to make Copilot behave as a domain expert for a specific task. Think of each agent as a specialist you can call on demand.

To use an agent:
1. Open **GitHub Copilot Chat** in VS Code or your IDE
2. Copy the prompt from the **"GitHub Copilot Prompt / Instructions"** section of the agent file
3. Paste it at the start of your Copilot Chat session
4. Then ask your task-specific question

---

## 🗂️ Available Agents

| Agent | File | Role | Best For |
|-------|------|------|----------|
| 💻 Code Implementation | [code-implementation-agent.md](code-implementation-agent.md) | Senior Java Developer | Completing TODO methods, implementing business logic |
| 🔍 PR Review | [pr-review-agent.md](pr-review-agent.md) | Senior Code Reviewer | Reviewing pull requests for bugs, quality, security |
| 📚 Documentation | [documentation-agent.md](documentation-agent.md) | Technical Writer | JavaDoc, OpenAPI annotations, README sections |
| 🧪 Testing | [testing-agent.md](testing-agent.md) | QA Engineer | JUnit 5 + Mockito unit tests, coverage improvement |
| 🏛️ Design & Architecture | [design-architecture-agent.md](design-architecture-agent.md) | Software Architect | Architecture reviews, pattern recommendations |
| ♻️ Refactoring | [refactoring-agent.md](refactoring-agent.md) | Code Quality Specialist | Eliminating code smells, applying design patterns |

---

## 🚀 Quick Start

### Example: Implement a TODO method

1. Open `src/main/java/com/loyalty/rewards/service/EligibilityService.java`
2. Open Copilot Chat
3. Paste the prompt from `code-implementation-agent.md`
4. Ask: *"Complete the `isEligibleForOffer` method using the helper methods already in the class"*

### Example: Review your own changes

1. Paste the changed file content into Copilot Chat
2. Paste the prompt from `pr-review-agent.md`
3. Ask: *"Review this code and produce a structured report"*

---

## 🔗 Related Resources

- [Scenarios](../scenarios/README.md) — Hands-on exercises that use these agents
- [Setup Guide](../docs/setup-guide.md) — How to run the application and configure Copilot
- [Domain Model](../docs/domain-model.md) — Business context and entity descriptions
- [API Reference](../docs/api-reference.md) — REST endpoint documentation
