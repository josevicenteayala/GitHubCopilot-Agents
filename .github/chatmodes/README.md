# `.github/chatmodes/` — Reference Chat Modes

This directory contains **GitHub Copilot custom chat modes** for the loyalty rewards
project. A chat mode is a reusable persona + tool-set that you can activate in the
Copilot Chat mode picker (next to **Ask** / **Edit** / **Agent**).

## How these files work

VS Code's Copilot Chat discovers any `*.chatmode.md` file under `.github/chatmodes/`
and makes it available in the **mode picker** at the bottom of the chat input.
Selecting a custom mode loads:

- the persona in the body of the file as the system prompt,
- the allow-listed `tools` from the YAML front-matter,
- (optionally) a specific `model` preference.

## Available chat modes

| File | Persona | Best for |
|------|---------|----------|
| [`senior-java-dev.chatmode.md`](senior-java-dev.chatmode.md) | Senior Java developer | Multi-turn implementation, refactoring, and debugging sessions |
| [`code-reviewer.chatmode.md`](code-reviewer.chatmode.md) | Principal-level reviewer | Structured PR reviews with severity-tagged feedback |

## How to build your own

Follow [Scenario 0 — Author your first Copilot agent](../../scenarios/scenario-00-create-an-agent.md).
Each agent file under [`../../agents/`](../../agents/) includes a
**"🏗️ Build this agent yourself"** section with per-agent chat-mode instructions.

## Front-matter reference

```yaml
---
description: <one-line summary shown in the mode picker>
tools: ['codebase', 'search']   # Optional tool allow-list
model: GPT-5                    # Optional model preference (omit to use the user's default)
---
```

Keep the body focused on *how the persona behaves* — the shared project context
lives in [`../copilot-instructions.md`](../copilot-instructions.md) and is
automatically included.
