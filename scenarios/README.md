# 🎓 Learning Scenarios Index

This directory contains hands-on exercises designed to help you practise using **GitHub Copilot Agents** on a real Java Spring Boot application.

Each scenario is self-contained, with clear prerequisites, step-by-step instructions, and acceptance criteria so you can verify your own progress.

---

## 📚 Scenario Overview

| # | Scenario | Difficulty | Time | Agent Used | Skills Practised |
|---|----------|-----------|------|------------|-----------------|
| 00 | [Author Your First Copilot Agent](scenario-00-create-an-agent.md) | 🟢 Beginner | 25–35 min | *you build one* | Prompt files, chat modes, repo instructions |
| 01 | [Complete the Eligibility Feature](scenario-01-complete-feature.md) | 🟢 Beginner | 20–30 min | Code Implementation | Business logic, helper methods, TODO completion |
| 02 | [Review a Pull Request with AI](scenario-02-pr-review.md) | 🟡 Intermediate | 25–35 min | PR Review | Code review, bug identification, security |
| 03 | [Generate Tests for EligibilityService](scenario-03-generate-tests.md) | 🟡 Intermediate | 30–40 min | Testing | JUnit 5, Mockito, boundary testing, coverage |
| 04 | [Refactor the Transaction Service](scenario-04-refactor-code.md) | 🟡 Intermediate | 30–40 min | Refactoring | Extract method, switch expressions, code smells |
| 05 | [Document the Loyalty Rewards API](scenario-05-document-api.md) | 🟢 Beginner | 20–30 min | Documentation | JavaDoc, OpenAPI, Swagger UI |

> 🟢 **Start with Scenario 00.** It builds the Copilot agents the rest of the
> scenarios rely on. You only need to do it once per workshop.

---

## 🎯 Recommended Learning Paths

### 🚀 Complete Beginner (Start Here)
```
Scenario 00 → Scenario 01 → Scenario 05 → Scenario 03
```
Build the agents first, then implement a feature, document it, and test it.

### 🔍 Code Quality Focus
```
Scenario 00 → Scenario 01 → Scenario 02 → Scenario 04
```
Build agents, implement, review, then refactor.

### 🧪 Test-Driven Focus
```
Scenario 00 → Scenario 01 → Scenario 03 → Scenario 02
```
Build agents, implement, write tests, then practice reviewing.

### 🏆 Full Workshop (Complete All)
```
Scenario 00 → Scenario 01 → Scenario 03 → Scenario 02 → Scenario 04 → Scenario 05
```
Follow this order for the most logical progression. Each scenario builds on the last.

---

## ⚙️ Setup Before Starting

Make sure the application builds and runs:

```bash
# Clone and build
git clone <repo-url>
cd GitHubCopilot-Agents
mvn package

# Run the application
mvn spring-boot:run
# Application available at: http://localhost:8080

# Run tests
mvn test
```

See the [Setup Guide](../docs/setup-guide.md) for full installation instructions.

---

## 📖 Domain Background

Before starting, read the [Domain Model](../docs/domain-model.md) to understand:
- The **Loyalty Rewards** business context
- How **BRONZE / SILVER / GOLD** tiers work
- How **points** are earned (10 points per dollar)
- The **Customer → Offer → Transaction** relationships

---

## 🤖 Agents Used in These Scenarios

| Agent | Design doc | Reference prompt | Used In |
|-------|------------|------------------|---------|
| *(any)* | — | — | Scenario 00 (build one yourself) |
| Code Implementation | [design](../agents/code-implementation-agent.md) | [prompt](../.github/prompts/code-implementation.prompt.md) | Scenario 01 |
| PR Review | [design](../agents/pr-review-agent.md) | [prompt](../.github/prompts/pr-review.prompt.md) | Scenario 02 |
| Testing | [design](../agents/testing-agent.md) | [prompt](../.github/prompts/testing.prompt.md) | Scenario 03 |
| Refactoring | [design](../agents/refactoring-agent.md) | [prompt](../.github/prompts/refactoring.prompt.md) | Scenario 04 |
| Documentation | [design](../agents/documentation-agent.md) | [prompt](../.github/prompts/documentation.prompt.md) | Scenario 05 |

---

## 🏅 Difficulty Guide

| Badge | Meaning |
|-------|---------|
| 🟢 Beginner | Clear instructions, one focused task, minimal prerequisite knowledge |
| 🟡 Intermediate | Requires reading and understanding existing code, multi-step task |
| 🔴 Advanced | Open-ended, requires cross-cutting knowledge, design decisions required |
