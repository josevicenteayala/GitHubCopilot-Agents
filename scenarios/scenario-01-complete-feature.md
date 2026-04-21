# 🎯 Scenario 01 — Complete the Eligibility Feature

**Difficulty:** 🟢 Beginner  
**Estimated Time:** 20–30 minutes  
**Agent:** [Code Implementation Agent](../agents/code-implementation-agent.md)

---

## 📖 Description

The `EligibilityService` is the heart of the loyalty rewards system — it decides whether a customer qualifies for a promotional offer. Two of its methods are currently stubs that throw `UnsupportedOperationException`:

1. `isEligibleForOffer(Customer customer, Offer offer)` — the main eligibility check
2. `checkTransactionLimit(Long customerId, int maxTransactions)` — guards against over-use

Your task is to use the **Code Implementation Agent** to implement both methods correctly, using the helper methods already present in the class.

---

## ✅ Prerequisites

- [ ] Application builds successfully: `mvn compile`
- [ ] You can open files in VS Code or IntelliJ
- [ ] GitHub Copilot is installed and signed in
- [ ] You have read the [domain model](../docs/domain-model.md)

---

## 🗂️ Relevant Files

| File | Purpose |
|------|---------|
| `src/main/java/com/loyalty/rewards/service/EligibilityService.java` | The class to implement |
| `src/main/java/com/loyalty/rewards/domain/Customer.java` | Customer entity (tier, loyaltyPoints) |
| `src/main/java/com/loyalty/rewards/domain/Offer.java` | Offer entity (status, dates, minPoints, targetTier) |
| `src/main/java/com/loyalty/rewards/domain/OfferStatus.java` | Enum: ACTIVE, INACTIVE, EXPIRED |
| `src/main/java/com/loyalty/rewards/repository/TransactionRepository.java` | Has `findByCustomerId()` |

---

## 📋 Business Rules to Implement

A customer is eligible for an offer **only if ALL** of the following are true:

| Rule | Description |
|------|-------------|
| **Points check** | Customer's `loyaltyPoints` ≥ offer's `minPointsRequired` |
| **Tier check** | Customer's tier is equal to or above the offer's `targetTier` (BRONZE < SILVER < GOLD). If `targetTier` is null, any tier qualifies. |
| **Offer active** | Offer status is `ACTIVE` AND today's date is within `validFrom`..`validTo` (inclusive) |
| **Transaction limit** | Customer has fewer than 50 total transactions on record |

---

## 🪜 Step-by-Step Instructions

### Step 1 — Open the File

Open `EligibilityService.java` in your editor. Notice:
- Lines 29–31: `isEligibleForOffer()` throws `UnsupportedOperationException`
- Lines 70–72: `checkTransactionLimit()` throws `UnsupportedOperationException`
- Lines 34–67: Helper methods already implemented — use them!

### Step 2 — Activate the Code Implementation Agent

Open GitHub Copilot Chat and paste this system prompt:

```
You are a senior Java developer working on a Spring Boot 3.x loyalty rewards application.
The tech stack is: Java 17, Spring Boot 3.2.5, Spring Data JPA, H2, JUnit 5, Mockito.

Domain rules:
- Customers have a tier: BRONZE, SILVER, GOLD (BRONZE < SILVER < GOLD)
- A customer is eligible for an offer only if ALL of these are true:
  1. loyaltyPoints >= offer.minPointsRequired
  2. customer tier >= offer.targetTier (null targetTier means any tier qualifies)
  3. offer.status == ACTIVE and today is within offer.validFrom..offer.validTo
  4. customer has fewer than 50 total transactions
- Use constructor injection, never @Autowired field injection
- Do not add unnecessary comments
```

### Step 3 — Prompt for Implementation

With the agent activated, type in Copilot Chat:

```
Implement the two TODO methods in EligibilityService:

1. isEligibleForOffer(Customer customer, Offer offer):
   Return true only when ALL four conditions are met.
   Use these existing helper methods:
   - hasMinimumPoints(customer, offer.getMinPointsRequired())
   - isTierEligible(customer, offer.getTargetTier())
   - isOfferActive(offer)
   - checkTransactionLimit(customer.getId(), 50)

2. checkTransactionLimit(Long customerId, int maxTransactions):
   Use transactionRepository.findByCustomerId(customerId) to get the list.
   Return true if the list size is strictly less than maxTransactions.

Do not modify any other methods.
```

### Step 4 — Apply the Changes

Review the generated code carefully:
- Does `isEligibleForOffer` use all four helper methods with `&&`?
- Does `checkTransactionLimit` call `transactionRepository.findByCustomerId()`?
- Does it return `true` when size < maxTransactions?

Apply the changes to `EligibilityService.java`.

### Step 5 — Verify

```bash
# Confirm the code compiles
mvn compile

# Run all tests
mvn test

# Run just the eligibility tests (if they exist)
mvn test -Dtest=EligibilityServiceTest
```

---

## 🏆 Acceptance Criteria

- [ ] `isEligibleForOffer` returns `true` when a GOLD customer with 5000 points applies for an active SILVER offer
- [ ] `isEligibleForOffer` returns `false` when the customer has insufficient points
- [ ] `isEligibleForOffer` returns `false` when the offer has expired
- [ ] `isEligibleForOffer` returns `false` when the customer has 50+ transactions
- [ ] `checkTransactionLimit` returns `true` when the customer has 0 transactions
- [ ] `checkTransactionLimit` returns `false` when the customer has exactly 50 transactions
- [ ] `mvn test` passes with no failures
- [ ] No `UnsupportedOperationException` is thrown

---

## 💡 Expected Final Implementation

<details>
<summary>Click to reveal the reference implementation</summary>

```java
public boolean isEligibleForOffer(Customer customer, Offer offer) {
    return hasMinimumPoints(customer, offer.getMinPointsRequired())
        && isTierEligible(customer, offer.getTargetTier())
        && isOfferActive(offer)
        && checkTransactionLimit(customer.getId(), 50);
}

public boolean checkTransactionLimit(Long customerId, int maxTransactions) {
    return transactionRepository.findByCustomerId(customerId).size() < maxTransactions;
}
```

</details>

---

## 🔗 What's Next?

Once you've completed this scenario, proceed to:
- [Scenario 03 — Generate Tests for EligibilityService](scenario-03-generate-tests.md) — write tests that verify your implementation
- [Scenario 05 — Document the API](scenario-05-document-api.md) — add JavaDoc to what you just implemented
