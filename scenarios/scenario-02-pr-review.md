# 🔍 Scenario 02 — Review a Pull Request with AI

**Difficulty:** 🟡 Intermediate  
**Estimated Time:** 25–35 minutes  
**Agent:** [PR Review Agent](../agents/pr-review-agent.md)

---

## 📖 Description

A team member has submitted a pull request adding a **transaction limit enforcement feature**. The PR adds a new endpoint that returns a transaction summary for a customer, and modifies `OfferService` to enforce a limit on how many times an offer can be redeemed.

Your task is to use the **PR Review Agent** to simulate a thorough code review and produce a structured report identifying bugs, quality issues, and missing tests.

---

## ✅ Prerequisites

- [ ] GitHub Copilot is installed and signed in
- [ ] You have read the [domain model](../docs/domain-model.md)
- [ ] You understand the project's existing patterns (constructor injection, ResponseEntity, DTOs)

---

## 🗂️ Code to Review

The following code has been added or modified in this PR. Review it carefully with the PR Review Agent.

### New endpoint in `TransactionController.java`

```java
// Added to TransactionController — GET /api/transactions/customer/{customerId}/summary
@GetMapping("/customer/{customerId}/summary")
public ResponseEntity<Map<String, Object>> getTransactionSummary(@PathVariable Long customerId) {
    List<Transaction> transactions = transactionService.getTransactionsForCustomer(customerId);
    Map<String, Object> summary = new HashMap<>();
    summary.put("count", transactions.size());
    int totalPoints = 0;
    for (Transaction t : transactions) {
        totalPoints = totalPoints + t.getPointsEarned();
    }
    summary.put("totalPointsEarned", totalPoints);
    summary.put("customerId", customerId);
    return ResponseEntity.ok(summary);
}
```

### Modified method in `OfferService.java`

```java
// Modified to enforce per-customer redemption limits
public void assignOfferToCustomer(Long offerId, Long customerId) {
    Offer offer = offerRepository.findById(offerId)
            .orElseThrow(() -> new OfferNotFoundException(offerId));

    Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new CustomerNotFoundException(customerId));

    // Check how many times this customer has already used this offer
    List<Transaction> transactions = transactionRepository.findByCustomerId(customerId);
    int redemptionCount = 0;
    for (Transaction t : transactions) {
        if (t.getDescription() != null && t.getDescription().contains("Offer: " + offer.getTitle())) {
            redemptionCount++;
        }
    }

    if (redemptionCount >= 3) {
        throw new IneligibleCustomerException("Offer redemption limit reached for customer " + customerId);
    }

    // Assign the offer
    customer.setLoyaltyPoints(customer.getLoyaltyPoints() - offer.getMinPointsRequired());
    customerRepository.save(customer);
}
```

### New test (attempted) in `OfferServiceTest.java`

```java
@Test
void testAssignOffer() {
    // just checking it doesn't throw
    offerService.assignOfferToCustomer(1L, 1L);
}
```

---

## 🪜 Step-by-Step Instructions

### Step 1 — Activate the PR Review Agent

Open GitHub Copilot Chat and paste this system prompt:

```
You are a senior Java code reviewer specialising in Spring Boot 3.x applications.
You are reviewing a pull request for a loyalty rewards system.

Tech stack: Java 17, Spring Boot 3.2.5, Spring Data JPA, H2, JUnit 5, Mockito.

Produce a structured review report with:
1. Summary — what the PR does
2. Critical Issues 🔴 — bugs or broken logic (must fix)
3. Warnings 🟡 — code smells, missing validation, performance
4. Suggestions 🟢 — style, better naming, refactoring ideas
5. Test Coverage Assessment
6. Overall Verdict — APPROVE / REQUEST_CHANGES

For each issue, include: file + description + concrete code fix.
```

### Step 2 — Submit the Code for Review

In Copilot Chat, paste the three code blocks above and ask:

```
Review the following PR code that adds a transaction summary endpoint and
offer redemption limit enforcement. Apply your full review criteria.

[paste all three code blocks]
```

### Step 3 — Analyse the Review Output

The agent should identify at least these issues. Check that your review report covers them:

| # | Severity | Issue |
|---|----------|-------|
| 1 | 🔴 Critical | `getTransactionSummary` doesn't verify the customer exists — returns 200 for non-existent customers |
| 2 | 🔴 Critical | `assignOfferToCustomer` uses string matching on `description` to track redemptions — fragile and wrong |
| 3 | 🔴 Critical | Points are deducted without checking if the customer has enough points |
| 4 | 🟡 Warning | For-loop in `getTransactionSummary` should use a stream: `.mapToInt(Transaction::getPointsEarned).sum()` |
| 5 | 🟡 Warning | `Map<String, Object>` return type — should be a typed DTO |
| 6 | 🟡 Warning | `assignOfferToCustomer` loads ALL transactions to count redemptions — very inefficient at scale |
| 7 | 🟢 Suggestion | The new endpoint lacks `@Operation` documentation |
| 8 | 🟢 Suggestion | The test `testAssignOffer()` is not a real test — it has no assertions |

### Step 4 — Write Up Your Review

Create a short (1-page) review summary that you would post as a PR comment. Include:
- Your overall verdict
- The top 3 issues that must be fixed before merging
- One concrete code fix for each critical issue

---

## 🏆 Acceptance Criteria

- [ ] Review report has all five required sections (Summary, Critical, Warnings, Suggestions, Tests, Verdict)
- [ ] At least 3 critical issues identified
- [ ] At least 2 warnings identified
- [ ] Test coverage assessment explains what is missing
- [ ] At least one concrete code fix is provided
- [ ] Overall verdict is **REQUEST_CHANGES**

---

## 💡 Tips

- Ask Copilot: *"What HTTP status code should be returned when a customer is not found?"*
- Ask Copilot: *"What is a better data structure to track offer redemptions per customer?"*
- Ask Copilot: *"Rewrite the getTransactionSummary method to fix all identified issues"*

---

## 🔗 What's Next?

After completing this review:
- Fix the critical bugs using the [Code Implementation Agent](../agents/code-implementation-agent.md)
- Add missing tests using the [Testing Agent](../agents/testing-agent.md)
- See [Scenario 04 — Refactor the Transaction Service](scenario-04-refactor-code.md) for further clean-up
