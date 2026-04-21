# 🎯 Scenario 00 — Author Your First Copilot Agent

**Difficulty:** 🟢 Beginner (start here)
**Estimated Time:** 25–35 minutes
**What you build:** a real, invokable GitHub Copilot **prompt file** and
**custom chat mode** committed to this repository.

---

## 📖 Description

Before completing any of the other scenarios, you will **author** the Copilot agents
that the other scenarios depend on. By the end you will have:

1. A prompt file you can invoke from the Copilot Chat `/` picker.
2. A custom chat mode you can select from the chat mode picker.
3. A repository instructions file that applies to every Copilot request in this repo.

This turns the files under [`agents/`](../agents/) from
*reading material* into **actual Copilot customisations** running against the
loyalty rewards code.

> 💡 **Tip:** This repo already ships a set of reference implementations under
> [`.github/prompts/`](../.github/prompts/) and [`.github/chatmodes/`](../.github/chatmodes/).
> Use them as a cheat sheet if you get stuck — but try to build yours from scratch first.

---

## ✅ Prerequisites

- [ ] GitHub Copilot Chat is installed in **VS Code** (1.90+) and you are signed in.
- [ ] The repo builds: `mvn compile`.
- [ ] You have read the project rules in [`.github/copilot-instructions.md`](../.github/copilot-instructions.md).
- [ ] You have skimmed [`agents/README.md`](../agents/README.md)
      so you know which six agents exist.

---

## 🧱 Background — the three Copilot surfaces

GitHub Copilot supports three kinds of customisation that live in the repo:

| Surface | File location | Activated by | Scope |
|---------|---------------|--------------|-------|
| **Repository instructions** | `.github/copilot-instructions.md` | *Automatic* — attached to every Copilot request in the repo | Everyone, everywhere |
| **Prompt file** | `.github/prompts/<name>.prompt.md` | Typing `/` in Copilot Chat and picking it | One invocation |
| **Custom chat mode** | `.github/chatmodes/<name>.chatmode.md` | Selecting it from the chat-mode picker | A whole conversation |

A fourth surface — the **Copilot coding agent** (cloud) — is covered in the
optional appendix [`agents/coding-agent-setup.md`](../agents/coding-agent-setup.md).

In this scenario you will create one of each of the first three.

---

## 🗂️ Your deliverables

By the end you will have committed:

```
.github/
├── copilot-instructions.md          ← already in the repo; you will extend it
├── prompts/
│   └── my-testing.prompt.md         ← NEW, built by you
└── chatmodes/
    └── my-senior-java-dev.chatmode.md  ← NEW, built by you
```

> ⚠️ Use the `my-` prefix on your filenames so they don't clash with the reference
> files already in those directories.

---

## 🧭 Step-by-Step

### Step 1 — Read the shared project context

Open [`.github/copilot-instructions.md`](../.github/copilot-instructions.md) and read
the **Tech Stack**, **Domain Rules**, and **Coding Conventions** sections.

Everything in that file is automatically injected into every Copilot request. When
you write your prompt file in Step 2, you can **assume the reader already knows it**
and focus on the *specialty* of your agent.

### Step 2 — Build a prompt file (Testing Agent)

1. Open the design doc at [`agents/testing-agent.md`](../agents/testing-agent.md)
   and read the **"🧩 GitHub Copilot Prompt / Instructions"** section — this is the
   raw content you are about to turn into a real Copilot prompt file.

2. Create `.github/prompts/my-testing.prompt.md` with:

   ```yaml
   ---
   mode: agent
   description: Generate JUnit 5 + Mockito unit tests for Spring Boot services.
   tools: ['codebase', 'editFiles', 'findTestFiles', 'problems', 'search', 'usages']
   ---
   ```

   followed by a short Markdown body (10–30 lines) that tells Copilot how to behave.
   Cover at minimum:

   - The persona ("You are a QA engineer writing JUnit 5 tests…").
   - The test-naming convention (`should_[behaviour]_when_[condition]`).
   - The Arrange / Act / Assert structure rule.
   - The coverage checklist (happy path, boundary, null, every exception branch).
   - A clear **output format** (full test class in one Java code block,
     plus a coverage table).

3. Do **not** repeat information already in `copilot-instructions.md` (Java version,
   Spring Boot version, constructor injection, etc.) — that context comes for free.

4. **Compare** your file to the reference at
   [`.github/prompts/testing.prompt.md`](../.github/prompts/testing.prompt.md). They
   should be structurally similar.

### Step 3 — Build a custom chat mode (Senior Java Developer)

1. Create `.github/chatmodes/my-senior-java-dev.chatmode.md` with this front-matter:

   ```yaml
   ---
   description: Senior Java developer persona for the loyalty rewards codebase.
   tools: ['codebase', 'editFiles', 'findTestFiles', 'problems', 'search', 'usages']
   ---
   ```

2. Write a persona body (15–30 lines) describing:

   - **Who the persona is** (role, experience).
   - **How they work** (read before writing, reuse helpers, small steps).
   - **What they never do** (field injection, business logic in controllers,
     invented exception types).
   - **How they handle uncertainty** (ask the user to clarify instead of guessing).

3. **Compare** your file to
   [`.github/chatmodes/senior-java-dev.chatmode.md`](../.github/chatmodes/senior-java-dev.chatmode.md).

### Step 4 — Extend the repository instructions (optional)

Open `.github/copilot-instructions.md` and add one project rule of your own under
**Coding Conventions** — for example, *"All DTOs must be Java records unless a
mutable builder is genuinely needed."* Keep it short and testable.

### Step 5 — Verify everything works

1. **Reload VS Code** (`Ctrl+Shift+P` → *Developer: Reload Window*) so Copilot
   rediscovers your new files.
2. Open Copilot Chat.
3. Type `/` in the chat input. **Expected:** `my-testing` appears in the picker with
   the description you wrote.
4. Open the chat mode picker (bottom of the chat panel, next to Ask/Edit/Agent).
   **Expected:** `my-senior-java-dev` appears.
5. Select your `my-senior-java-dev` chat mode and ask:
   > *"Implement the TODO methods in `EligibilityService.java`."*
   **Expected:** the reply respects constructor injection, reuses helpers, and
   throws the existing domain exceptions — exactly as your persona specifies.
6. Start a new chat, choose `/my-testing`, and ask:
   > *"Generate tests for `EligibilityService`."*
   **Expected:** a JUnit 5 class with `should_X_when_Y` names, AAA comments, and a
   coverage table — because your prompt demanded that format.

---

## 🎯 Expected Outcome

- [ ] `.github/prompts/my-testing.prompt.md` exists and appears in the Copilot `/` picker.
- [ ] `.github/chatmodes/my-senior-java-dev.chatmode.md` exists and appears in the chat mode picker.
- [ ] Invoking each one produces output that matches the format you specified.
- [ ] The files are **committed** to the repo so the next student who opens it gets
      the same agents.

---

## ✅ Acceptance Criteria

- [ ] The front-matter of your prompt file includes `mode`, `description`, and `tools`.
- [ ] The front-matter of your chat mode file includes `description` and `tools`.
- [ ] Your prompt body does **not** repeat the tech stack / domain rules already in
      `.github/copilot-instructions.md`.
- [ ] The Copilot output you get in Step 5 visibly follows your custom rules
      (e.g. AAA comments in the tests, severity tags in a review).
- [ ] Running `mvn test` still passes — this scenario is documentation-only for
      application code.

---

## 🧠 Reflection Questions

1. Which of your rules would be better placed in `copilot-instructions.md`
   (so *every* agent respects them) vs. inside a single prompt file?
2. What happens if two prompt files disagree with each other? (Try it — it's
   instructive.)
3. How would this change if you were running the **Copilot coding agent** in the
   cloud instead of Copilot Chat locally?
   See [`agents/coding-agent-setup.md`](../agents/coding-agent-setup.md).

---

## 🔗 Next Steps

Now that your agents exist, every subsequent scenario can say
**"activate the Testing Agent you built in Scenario 0"** instead of
**"paste the prompt block from `testing-agent.md` into chat"**.

Recommended order:

1. → [Scenario 01 — Complete the Eligibility Feature](scenario-01-complete-feature.md) (Code Implementation Agent)
2. → [Scenario 03 — Generate Tests for EligibilityService](scenario-03-generate-tests.md) (Testing Agent)
3. → [Scenario 02 — Review a Pull Request with AI](scenario-02-pr-review.md) (PR Review Agent)
4. → [Scenario 04 — Refactor the Transaction Service](scenario-04-refactor-code.md) (Refactoring Agent)
5. → [Scenario 05 — Document the Loyalty Rewards API](scenario-05-document-api.md) (Documentation Agent)
