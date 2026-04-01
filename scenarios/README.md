# 🎓 Learning Scenarios Index

This directory contains hands-on exercises designed to help you practise using **GitHub Copilot Agents** on a real Java Spring Boot application.

Each scenario is self-contained, with clear prerequisites, step-by-step instructions, and acceptance criteria so you can verify your own progress.

---

## 📚 Scenario Overview

| # | Scenario | Difficulty | Time | Agent Used | Skills Practised |
|---|----------|-----------|------|------------|-----------------|
| 01 | [Complete the Eligibility Feature](scenario-01-complete-feature.md) | 🟢 Beginner | 20–30 min | Code Implementation | Business logic, helper methods, TODO completion |
| 02 | [Review a Pull Request with AI](scenario-02-pr-review.md) | 🟡 Intermediate | 25–35 min | PR Review | Code review, bug identification, security |
| 03 | [Generate Tests for EligibilityService](scenario-03-generate-tests.md) | 🟡 Intermediate | 30–40 min | Testing | JUnit 5, Mockito, boundary testing, coverage |
| 04 | [Refactor the Transaction Service](scenario-04-refactor-code.md) | 🟡 Intermediate | 30–40 min | Refactoring | Extract method, switch expressions, code smells |
| 05 | [Document the Loyalty Rewards API](scenario-05-document-api.md) | 🟢 Beginner | 20–30 min | Documentation | JavaDoc, OpenAPI, Swagger UI |

---

## 🎯 Recommended Learning Paths

### 🚀 Complete Beginner (Start Here)
```
Scenario 01 → Scenario 05 → Scenario 03
```
Start by implementing a feature, document it, then test it.

### 🔍 Code Quality Focus
```
Scenario 01 → Scenario 02 → Scenario 04
```
Implement, review, then refactor.

### 🧪 Test-Driven Focus
```
Scenario 01 → Scenario 03 → Scenario 02
```
Implement, write tests, then practice reviewing.

### 🏆 Full Workshop (Complete All)
```
Scenario 01 → Scenario 03 → Scenario 02 → Scenario 04 → Scenario 05
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

| Agent | File | Used In |
|-------|------|---------|
| Code Implementation | [code-implementation-agent.md](../agents/code-implementation-agent.md) | Scenario 01 |
| PR Review | [pr-review-agent.md](../agents/pr-review-agent.md) | Scenario 02 |
| Testing | [testing-agent.md](../agents/testing-agent.md) | Scenario 03 |
| Refactoring | [refactoring-agent.md](../agents/refactoring-agent.md) | Scenario 04 |
| Documentation | [documentation-agent.md](../agents/documentation-agent.md) | Scenario 05 |

---

## 🏅 Difficulty Guide

| Badge | Meaning |
|-------|---------|
| 🟢 Beginner | Clear instructions, one focused task, minimal prerequisite knowledge |
| 🟡 Intermediate | Requires reading and understanding existing code, multi-step task |
| 🔴 Advanced | Open-ended, requires cross-cutting knowledge, design decisions required |
