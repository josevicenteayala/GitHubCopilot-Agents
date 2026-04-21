# `.github/prompts/` — Reference Prompt Files

This directory contains **working reference implementations** of the six agents
described in [`../../agents/`](../../agents/). Each file is a real GitHub Copilot
**prompt file** that you can invoke from Copilot Chat.

## How these files work

GitHub Copilot in VS Code discovers any file ending in `.prompt.md` under
`.github/prompts/` and makes it available in Copilot Chat:

- Type `/` in the chat input and the prompt's filename appears in the picker.
- Selecting it runs the prompt with the body of the file as the system instructions,
  honouring the `mode`, `description`, and `tools` fields from the YAML front-matter.

## Available prompts

| File | Agent | Mode |
|------|-------|------|
| [`code-implementation.prompt.md`](code-implementation.prompt.md) | Code Implementation | `agent` |
| [`pr-review.prompt.md`](pr-review.prompt.md) | PR Review | `ask` |
| [`testing.prompt.md`](testing.prompt.md) | Testing | `agent` |
| [`documentation.prompt.md`](documentation.prompt.md) | Documentation | `agent` |
| [`design-architecture.prompt.md`](design-architecture.prompt.md) | Design & Architecture | `ask` |
| [`refactoring.prompt.md`](refactoring.prompt.md) | Refactoring | `agent` |

## How to build your own

See [Scenario 0 — Author your first Copilot agent](../../scenarios/scenario-00-create-an-agent.md)
for a step-by-step walkthrough that produces a file equivalent to one of these.
Each agent file under [`../../agents/`](../../agents/) also contains a
**"🏗️ Build this agent yourself"** section linking back here.

## Front-matter reference

```yaml
---
mode: agent | ask | edit        # Copilot Chat mode the prompt opens in
description: <one-line summary> # Shown in the / picker
tools: ['codebase', 'search']    # Optional allow-list of tools the prompt may call
---
```

Keep prompt bodies short and rule-based. The shared project context
(tech stack, domain rules, conventions) lives in
[`../copilot-instructions.md`](../copilot-instructions.md) and is automatically
injected — prompts should focus on their specialty.
