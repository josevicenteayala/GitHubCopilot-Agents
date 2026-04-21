# 🚀 Appendix — Customising the Copilot Coding Agent

> **Audience:** students who have already completed
> [Scenario 0](../scenarios/scenario-00-create-an-agent.md) and are comfortable with
> prompt files and chat modes.

The [reference prompt files](../.github/prompts/) and [chat modes](../.github/chatmodes/) customise
**Copilot Chat** — the agent that runs in your editor. GitHub also ships a
**cloud-hosted Copilot coding agent** that picks up issues or PR comments, spins up a
container, and opens pull requests autonomously. Customising that agent requires
different files.

This appendix shows how to set up and customise the cloud agent for this repository.

---

## 🧰 The two files you will create

| File | Purpose |
|------|---------|
| `.github/copilot-setup-steps.yml` | Commands the cloud agent runs to prepare its container (install tools, warm caches, import data). |
| `.github/copilot-instructions.md` *(already in this repo)* | Project rules the cloud agent reads before every task — identical to what Copilot Chat uses. |

The cloud agent also inherits any **repository custom instructions** and any
**workflow-level environment** you configure through the GitHub UI.

---

## 1. Preinstall tools with `copilot-setup-steps.yml`

The cloud agent's container starts empty of project dependencies. Running `mvn test`
from a cold cache takes several minutes and counts against the agent's compute
budget. You can front-load that cost with a setup steps file.

Create `.github/copilot-setup-steps.yml`:

```yaml
name: Copilot agent setup

jobs:
  copilot-setup-steps:
    runs-on: ubuntu-latest
    steps:
      - name: Set up Java 17 (Temurin)
        uses: actions/setup-java@v4
        with:
          java-version: '17'
          distribution: 'temurin'

      - name: Cache Maven dependencies
        uses: actions/cache@v4
        with:
          path: ~/.m2/repository
          key: ${{ runner.os }}-maven-${{ hashFiles('**/pom.xml') }}
          restore-keys: |
            ${{ runner.os }}-maven-

      - name: Warm the dependency cache
        run: mvn -B -q -DskipTests dependency:go-offline

      - name: Pre-compile sources
        run: mvn -B -q -DskipTests compile test-compile
```

> ⚠️ The filename and the job name **must** be exactly
> `copilot-setup-steps.yml` and `copilot-setup-steps`. Anything else is ignored.

After a student commits this file, every cloud-agent session starts with Java 17
installed, Maven dependencies resolved, and both the production and test classpath
compiled — so the first `mvn test` inside a task is a near-instant incremental run.

## 2. Share project rules via `copilot-instructions.md`

No change needed — this repo already has [`.github/copilot-instructions.md`](../.github/copilot-instructions.md).
The cloud agent reads it before every task, so the same domain rules that guide your
editor-side agents also guide autonomous PRs.

## 3. Give the agent guard-rails

The cloud agent can commit and push — it is much more powerful than Chat. Add these
safeguards:

- **Require review on agent PRs.** In *Settings → Branches*, protect `main` and
  require at least one human reviewer for PRs authored by the agent.
- **Restrict the agent's tools.** In *Settings → Copilot → Coding agent*, enable
  only the MCP servers and GitHub tools you actually want it to use.
- **Mark secrets unavailable.** Don't expose workflow secrets to Copilot-authored
  runs; the agent does not need them to compile and test this project.

## 4. Exercise the cloud agent

1. Open any open issue in this repo (or file a new one).
2. Assign it to **Copilot**.
3. The agent picks the issue up, creates a branch, runs your setup steps,
   edits code, runs `mvn test`, and opens a PR when the tests pass.
4. Review the PR like any other contribution — the shared
   `copilot-instructions.md` means you can expect constructor injection, the correct
   domain exceptions, and AAA-style tests out of the box.

---

## 🔍 How it relates to the editor agents

| Capability | Editor (Chat / prompt files / chat modes) | Cloud coding agent |
|------------|-------------------------------------------|--------------------|
| Respects `copilot-instructions.md` | ✅ | ✅ |
| Respects `.github/prompts/*.prompt.md` | ✅ | ❌ (not invoked automatically) |
| Respects `.github/chatmodes/*.chatmode.md` | ✅ | ❌ |
| Uses `.github/copilot-setup-steps.yml` | ❌ | ✅ |
| Can commit and push | Only via your local Git | ✅ autonomously |

So your repo-wide instructions are **shared** across both surfaces, but prompt files
and chat modes are editor-only — which is why
[Scenario 0](../scenarios/scenario-00-create-an-agent.md) has you build one of each.

---

## 📚 Further reading

- GitHub Docs → *Customizing the development environment for Copilot coding agent*
- GitHub Docs → *About custom instructions for GitHub Copilot*
- GitHub Docs → *About Copilot chat modes*
