# 🤖 AI Agents Index

This directory contains the **design specifications** for six GitHub Copilot agents
tailored to the loyalty rewards Spring Boot application. Each file documents a
specialist persona — its role, system prompt, conventions, and expected outputs —
and tells you how to turn that specification into a **real Copilot customisation**
you can invoke.

---

## 🧭 What Is an "Agent" in This Repo?

The word "agent" is overloaded in GitHub Copilot. This repo uses three concrete
surfaces, all of which live in `.github/`:

| Surface | File location | Activated by | Scope |
|---------|---------------|--------------|-------|
| **Repository instructions** | [`../copilot-instructions.md`](../copilot-instructions.md) | *Automatic* — every Copilot request in the repo | Everyone, everywhere |
| **Prompt file** | [`../prompts/*.prompt.md`](../prompts/) | Typing `/` in Copilot Chat and picking it | One invocation |
| **Custom chat mode** | [`../chatmodes/*.chatmode.md`](../chatmodes/) | The chat mode picker in VS Code | A whole conversation |

A fourth surface — the **Copilot coding agent** (cloud) — is documented in
[`coding-agent-setup.md`](coding-agent-setup.md).

Each file in *this* directory (`.github/agents/*.md`) is a **design doc** that maps
to one or more of those surfaces. It is *not* itself invoked by Copilot.

---

## 🏗️ How to Build & Use an Agent

### Build

1. Read the agent's design doc (e.g. [`testing-agent.md`](testing-agent.md)) to
   understand its role, rules, and expected outputs.
2. Follow the **"🏗️ Build this agent yourself"** section inside that file to
   create a prompt file, a chat mode, or repo instructions.
3. Reload VS Code so Copilot discovers your new file.

Reference implementations already ship under
[`../prompts/`](../prompts/) and [`../chatmodes/`](../chatmodes/) — you can
compare your version to them, or copy them verbatim as a starting point.

🎓 New to all of this? Start with
[**Scenario 0 — Author your first Copilot agent**](../../scenarios/scenario-00-create-an-agent.md).

### Use

- **Prompt file:** open Copilot Chat, type `/`, pick the prompt, add your task.
- **Chat mode:** use the mode picker at the bottom of the chat input to select
  the persona, then start a conversation.
- **Repository instructions:** nothing to do — they attach to every request
  automatically.

---

## 🗂️ Available Agents

| Agent | Design doc | Reference prompt | Role | Best for |
|-------|------------|------------------|------|----------|
| 💻 Code Implementation | [code-implementation-agent.md](code-implementation-agent.md) | [`../prompts/code-implementation.prompt.md`](../prompts/code-implementation.prompt.md) | Senior Java Developer | Completing TODO methods, implementing business logic |
| 🔍 PR Review | [pr-review-agent.md](pr-review-agent.md) | [`../prompts/pr-review.prompt.md`](../prompts/pr-review.prompt.md) | Senior Code Reviewer | Reviewing pull requests for bugs, quality, security |
| 📚 Documentation | [documentation-agent.md](documentation-agent.md) | [`../prompts/documentation.prompt.md`](../prompts/documentation.prompt.md) | Technical Writer | JavaDoc, OpenAPI annotations, README sections |
| 🧪 Testing | [testing-agent.md](testing-agent.md) | [`../prompts/testing.prompt.md`](../prompts/testing.prompt.md) | QA Engineer | JUnit 5 + Mockito unit tests, coverage improvement |
| 🏛️ Design & Architecture | [design-architecture-agent.md](design-architecture-agent.md) | [`../prompts/design-architecture.prompt.md`](../prompts/design-architecture.prompt.md) | Software Architect | Architecture reviews, pattern recommendations |
| ♻️ Refactoring | [refactoring-agent.md](refactoring-agent.md) | [`../prompts/refactoring.prompt.md`](../prompts/refactoring.prompt.md) | Code Quality Specialist | Eliminating code smells, applying design patterns |

The two reference chat modes are
[`senior-java-dev.chatmode.md`](../chatmodes/senior-java-dev.chatmode.md) (paired
with the Code Implementation and Refactoring agents) and
[`code-reviewer.chatmode.md`](../chatmodes/code-reviewer.chatmode.md) (paired with
the PR Review agent).

---

## 🚀 Quick Start

### Option A — Use the reference implementations (zero setup)

1. Pull this repo into VS Code with the Copilot extension installed.
2. Reload the window so Copilot picks up `.github/prompts/` and `.github/chatmodes/`.
3. In Copilot Chat, type `/` and pick any of the six reference prompts, or open
   the chat-mode picker and select **Senior Java Developer** or **Code Reviewer**.
4. Ask your task-specific question (e.g. *"Generate tests for `EligibilityService`"*).

### Option B — Build an agent from scratch (recommended for learning)

Follow [Scenario 0 — Author your first Copilot agent](../../scenarios/scenario-00-create-an-agent.md).
You will commit a `my-*.prompt.md` and a `my-*.chatmode.md` and verify each
appears in Copilot's UI.

---

## 🔗 Related Resources

- [Repository instructions](../copilot-instructions.md) — shared project rules
- [Prompt files](../prompts/) — reference prompt implementations
- [Chat modes](../chatmodes/) — reference chat-mode implementations
- [Coding agent setup](coding-agent-setup.md) — customising the cloud Copilot agent
- [Scenarios](../../scenarios/README.md) — hands-on exercises that use these agents
- [Setup Guide](../../docs/setup-guide.md) — running the application and configuring Copilot
- [Domain Model](../../docs/domain-model.md) — business context and entity descriptions
- [API Reference](../../docs/api-reference.md) — REST endpoint documentation
