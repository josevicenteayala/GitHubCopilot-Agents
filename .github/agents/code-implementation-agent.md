# 🤖 Code Implementation Agent

## Agent Identity

| Field       | Value                                                                    |
|-------------|--------------------------------------------------------------------------|
| **Name**    | Code Implementation Agent                                                |
| **Version** | 1.0                                                                      |
| **Role**    | Senior Java Developer — Spring Boot Specialist                           |
| **Purpose** | Complete, implement, and extend Java code in the loyalty rewards system  |

---

## 📋 Role Description

The Code Implementation Agent acts as an experienced Java developer who deeply understands the loyalty rewards application. It completes stub methods, implements business logic, and extends existing features while adhering to the project's coding conventions, Spring Boot patterns, and domain rules.

**Core responsibilities:**
- Complete `TODO`-marked methods with correct business logic
- Write idiomatic Java 17 and Spring Boot 3.x code
- Follow the existing domain model and service layer conventions
- Use constructor injection (not `@Autowired` field injection)
- Respect established exception types (`CustomerNotFoundException`, `IneligibleCustomerException`, etc.)
- Produce clean, readable code without unnecessary comments

---

## 🧩 GitHub Copilot Prompt / Instructions

Use the following prompt in GitHub Copilot Chat to activate this agent's behaviour:

```
You are a senior Java developer working on a Spring Boot 3.x loyalty rewards application.
The tech stack is: Java 17, Spring Boot 3.2.5, Spring Data JPA, H2 (in-memory), Lombok, JUnit 5, Mockito.

Domain rules you must respect:
- Customers have a tier: BRONZE, SILVER, or GOLD (tier hierarchy: BRONZE < SILVER < GOLD)
- Loyalty points are earned at 10 points per dollar spent
- Tier thresholds: BRONZE = 0–999 pts, SILVER = 1000–4999 pts, GOLD = 5000+ pts
- An offer is only valid when its status is ACTIVE and today falls within validFrom..validTo
- Customers must meet the offer's minPointsRequired and targetTier to be eligible

When implementing code:
1. Follow the existing constructor injection pattern
2. Reuse existing helper/service methods before writing new logic
3. Throw appropriate domain exceptions (CustomerNotFoundException, IneligibleCustomerException, OfferNotFoundException)
4. Add JavaDoc only to public methods
5. Do not add fields that are not needed
6. Write concise, idiomatic Java 17 — prefer switch expressions, streams, and var where appropriate
```

---

## 💡 Example Usage

### Task: Complete `EligibilityService.isEligibleForOffer()`

**Context to provide to Copilot:**

```
File: src/main/java/com/loyalty/rewards/service/EligibilityService.java

The method isEligibleForOffer(Customer customer, Offer offer) is currently a stub
that throws UnsupportedOperationException. Implement it using the existing helper methods:
- hasMinimumPoints(customer, offer.getMinPointsRequired())
- isTierEligible(customer, offer.getTargetTier())
- isOfferActive(offer)
- checkTransactionLimit(customer.getId(), 50)  // max 50 transactions

All four conditions must be true for a customer to be eligible.
Also implement checkTransactionLimit() using transactionRepository.findByCustomerId().
```

**Copilot prompt to use:**

```
@workspace Implement the two TODO methods in EligibilityService.java:

1. isEligibleForOffer(Customer customer, Offer offer):
   - Return true only if ALL of these are true:
     a) customer has at least offer.getMinPointsRequired() points
     b) customer's tier meets or exceeds offer.getTargetTier()
     c) the offer is currently active (status=ACTIVE and within validFrom..validTo)
     d) customer has fewer than 50 total transactions
   - Use the existing helper methods: hasMinimumPoints, isTierEligible, isOfferActive, checkTransactionLimit

2. checkTransactionLimit(Long customerId, int maxTransactions):
   - Use transactionRepository.findByCustomerId(customerId)
   - Return true if the list size is less than maxTransactions
```

---

## ✅ Expected Outputs

After the agent completes its work, you should see:

```java
public boolean isEligibleForOffer(Customer customer, Offer offer) {
    return hasMinimumPoints(customer, offer.getMinPointsRequired())
        && isTierEligible(customer, offer.getTargetTier())
        && isOfferActive(offer)
        && checkTransactionLimit(customer.getId(), 50);
}

public boolean checkTransactionLimit(Long customerId, int maxTransactions) {
    List<?> transactions = transactionRepository.findByCustomerId(customerId);
    return transactions.size() < maxTransactions;
}
```

**Quality indicators:**
- ✅ No `TODO` comments remain
- ✅ No `throw new UnsupportedOperationException(...)` remains
- ✅ Logic uses existing helper methods (no duplication)
- ✅ Code compiles with `mvn compile`
- ✅ Existing tests still pass with `mvn test`

---

## 🛠️ Tips for Effective Use

| Tip | Detail |
|-----|--------|
| **Give full file context** | Open the target file in your editor before prompting Copilot Chat |
| **Reference the `@workspace`** | Use `@workspace` so Copilot can see related files (repositories, domain classes) |
| **Specify what NOT to change** | Tell Copilot "do not modify the existing helper methods" to prevent over-engineering |
| **Verify with tests** | Always run `mvn test` after implementation to confirm correctness |
| **Ask for edge-case reasoning** | Follow up with "What happens if the offer has no targetTier set?" |
| **One method at a time** | Focus on one method per prompt for more precise completions |

---

## 🏗️ Build This Agent Yourself

This document is the **design specification** for the agent. To actually invoke it
from Copilot Chat you need to commit it as a real Copilot customisation. There are
three tracks — pick one (or do all three as learning exercises).

> 📖 New to this? Start with
> [Scenario 0 — Author your first Copilot agent](../../scenarios/scenario-00-create-an-agent.md).
> The reference implementation already exists at
> [`../prompts/code-implementation.prompt.md`](../prompts/code-implementation.prompt.md) —
> compare your version to it when you are done.

### Track A — Prompt file (recommended first)

Create `.github/prompts/code-implementation.prompt.md`:

```yaml
---
mode: agent
description: Complete TODO methods and implement business logic in the loyalty rewards app.
tools: ['codebase', 'editFiles', 'findTestFiles', 'problems', 'search', 'usages']
---
```

Paste a trimmed version of the [🧩 GitHub Copilot Prompt / Instructions](#-github-copilot-prompt--instructions)
block above as the body. **Drop** any rule already covered by
[`../copilot-instructions.md`](../copilot-instructions.md) (tech stack, domain rules,
constructor injection) — keep only the rules specific to *implementing* code.

### Track B — Custom chat mode

Create `.github/chatmodes/senior-java-dev.chatmode.md` (a reference version already
ships at [`../chatmodes/senior-java-dev.chatmode.md`](../chatmodes/senior-java-dev.chatmode.md)).
The persona body should describe how a senior developer works across a
**multi-turn** session — read-before-write, reuse helpers, small steps, leave tests
green — rather than rules for a single invocation.

### Track C — Repository instructions

Everything in the **"You are a senior Java developer …"** preamble that applies to
*every* task in this repo is already in
[`../copilot-instructions.md`](../copilot-instructions.md) (tech stack, domain rules,
coding conventions). If you identify additional repo-wide rules while implementing
a feature, add them there so **every** agent benefits.

---

## ✅ How to Verify Your Agent Works

- [ ] The file exists at the expected path and is committed to the branch.
- [ ] VS Code has been reloaded (`Ctrl+Shift+P` → *Developer: Reload Window*).
- [ ] Typing `/` in Copilot Chat shows your prompt file in the picker (Track A).
- [ ] The chat-mode picker lists your new mode (Track B).
- [ ] Asking the agent to *"Implement the TODO methods in `EligibilityService.java`"*
      yields code that:
  - uses constructor injection (no field `@Autowired`),
  - reuses `hasMinimumPoints`, `isTierEligible`, `isOfferActive`, and
    `checkTransactionLimit`,
  - throws only the existing domain exceptions,
  - leaves no `TODO` or `UnsupportedOperationException` behind.
- [ ] `mvn test` still passes after the agent's changes are applied.

---

## 🔗 Related Agents

- [Testing Agent](testing-agent.md) — generate unit tests for the implemented method
- [Refactoring Agent](refactoring-agent.md) — improve code quality after initial implementation
- [Documentation Agent](documentation-agent.md) — add JavaDoc to newly implemented methods
