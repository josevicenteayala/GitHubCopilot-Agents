# ♻️ Scenario 04 — Refactor the Transaction Service

**Difficulty:** 🟡 Intermediate  
**Estimated Time:** 30–40 minutes  
**Agent:** [Refactoring Agent](../agents/refactoring-agent.md)

---

## 📖 Description

`TransactionService` is functional but contains several **code smells** that make it harder to maintain and extend. In this scenario, you'll use the **Refactoring Agent** to identify and fix these issues — without changing the service's external behaviour.

The golden rule of refactoring: **make tests pass before you start, and make sure they still pass when you're done.**

---

## ✅ Prerequisites

- [ ] `mvn test` passes before you begin
- [ ] GitHub Copilot is installed and signed in
- [ ] You have read `src/main/java/com/loyalty/rewards/service/TransactionService.java`

---

## 🗂️ Relevant Files

| File | Purpose |
|------|---------|
| `src/main/java/com/loyalty/rewards/service/TransactionService.java` | Target for refactoring |
| `src/main/java/com/loyalty/rewards/domain/Transaction.java` | Domain entity |
| `src/main/java/com/loyalty/rewards/domain/TransactionType.java` | Enum: EARN, REDEEM, ADJUSTMENT |

---

## 🐛 Code Smells to Find and Fix

Study the current `TransactionService.java` and identify these specific problems:

### Smell 1 — Duplicated Zero-Setting Logic
```java
// In createTransaction():
if (type == TransactionType.EARN) {
    transaction.setPointsEarned(points);
    transaction.setPointsRedeemed(0);       // ← repeated
    ...
} else if (type == TransactionType.REDEEM) {
    transaction.setPointsEarned(0);         // ← repeated
    transaction.setPointsRedeemed(points);
} else {
    transaction.setPointsEarned(0);         // ← repeated again
    transaction.setPointsRedeemed(0);
}
```
**Problem:** The pattern of setting both `pointsEarned` and `pointsRedeemed` is duplicated. Logic for points handling is scattered.

---

### Smell 2 — Verbose Object Construction
```java
Transaction transaction = new Transaction();
transaction.setCustomer(customer);
transaction.setAmount(amount);
transaction.setType(type);
transaction.setDescription(description);
transaction.setTransactionDate(LocalDateTime.now());
// ... then more setters depending on type
```
**Problem:** Object construction is tangled with business logic in `createTransaction()`. The method does two things: builds the transaction AND applies business rules.

---

### Smell 3 — Duplicated Transaction Construction in `redeemPoints()`
```java
// redeemPoints() builds a Transaction in almost the same way as createTransaction()
Transaction transaction = new Transaction();
transaction.setCustomer(customer);
transaction.setAmount(0.0);
transaction.setPointsEarned(0);
transaction.setPointsRedeemed(points);
transaction.setType(TransactionType.REDEEM);
transaction.setDescription("Points redemption: " + points + " points");
transaction.setTransactionDate(LocalDateTime.now());
```
**Problem:** Transaction construction code appears in both `createTransaction()` and `redeemPoints()`. This is **duplicated code** (Fowler: "Duplicated Code" smell).

---

### Smell 4 — Imperative Loop Instead of Stream
The points calculation `(int) (amount * POINTS_PER_DOLLAR)` is already extracted to a method — good! But if any caller has inline arithmetic, it should use this method instead.

---

### Smell 5 — Long Method
`createTransaction()` has responsibilities for: fetching the customer, calculating points, building the transaction, applying business rules by type, and persisting. Consider extracting helper methods.

---

## 🪜 Step-by-Step Instructions

### Step 1 — Baseline: Run Tests

```bash
mvn test
```

Note the number of passing tests. This number must stay the same after refactoring.

### Step 2 — Activate the Refactoring Agent

Open GitHub Copilot Chat and paste this system prompt:

```
You are a senior Java developer performing safe, behaviour-preserving refactoring on a
Spring Boot 3.x loyalty rewards application.

Tech stack: Java 17, Spring Boot 3.2.5.

Refactoring rules:
1. NEVER change observable behaviour — all existing tests must still pass
2. Name each refactoring applied (use Fowler's catalogue)
3. Prefer Java 17 features: switch expressions, var
4. Split methods longer than 20 lines
5. Extract duplicated object construction into a private builder method
6. Show Before → After snippets for each change
7. After explaining all changes, show the complete refactored file
```

### Step 3 — Request Refactoring

In Copilot Chat, type:

```
Refactor TransactionService.java to fix these code smells:

1. Duplicated Zero-Setting: The setPointsEarned(0) and setPointsRedeemed(0) calls are
   repeated in each branch. Replace the if/else-if/else with a switch expression and
   eliminate the duplication by default-initialising both fields to 0 before the switch.

2. Duplicated Transaction Construction: Both createTransaction() and redeemPoints()
   build a Transaction object with similar setter calls. Extract a private method:
   buildTransaction(Customer customer, Double amount, TransactionType type, String description)
   that returns a Transaction with customer, amount, type, description, and transactionDate set.
   Both methods should call this helper.

3. Long Method: createTransaction() should call buildTransaction() for construction,
   then use a switch expression for the points logic. The result should be ≤ 15 lines.

After explaining each refactoring, show the complete refactored TransactionService.java file.
```

### Step 4 — Review the Refactored Code

Verify the refactored code meets these criteria:
- [ ] A `private buildTransaction(...)` method exists
- [ ] Both `createTransaction()` and `redeemPoints()` call `buildTransaction()`
- [ ] A switch expression (or cleaner if structure) handles the type-based points logic
- [ ] `POINTS_PER_DOLLAR` constant is used, not the raw number `10`
- [ ] `createTransaction()` is ≤ 20 lines
- [ ] No `TODO` or placeholder code exists

### Step 5 — Apply and Verify

```bash
# Confirm compilation
mvn compile

# Confirm all tests still pass
mvn test
```

If tests fail, the refactoring changed behaviour — ask Copilot to explain the difference and fix it.

---

## 🏆 Acceptance Criteria

- [ ] All tests pass before and after refactoring (`mvn test`)
- [ ] A private `buildTransaction()` helper method is extracted
- [ ] Transaction construction is not duplicated between `createTransaction()` and `redeemPoints()`
- [ ] Switch expression (or equivalent) replaces the if/else-if/else block
- [ ] `createTransaction()` is ≤ 20 lines
- [ ] `POINTS_PER_DOLLAR` constant is used (not magic number `10`)
- [ ] Each refactoring applied is named (e.g., "Extract Method", "Replace Conditional with Switch")

---

## 💡 Reference: Refactored Method Shape

<details>
<summary>Click to reveal the target structure</summary>

```java
public Transaction createTransaction(Long customerId, Double amount,
                                     TransactionType type, String description) {
    Customer customer = customerService.getCustomerById(customerId);
    int points = calculatePointsForAmount(amount);
    Transaction transaction = buildTransaction(customer, amount, type, description);

    switch (type) {
        case EARN -> {
            transaction.setPointsEarned(points);
            transaction.setPointsRedeemed(0);
            customerService.updateCustomerPoints(customerId, points);
        }
        case REDEEM -> {
            transaction.setPointsEarned(0);
            transaction.setPointsRedeemed(points);
        }
        default -> {
            transaction.setPointsEarned(0);
            transaction.setPointsRedeemed(0);
        }
    }

    customerService.updateCustomerTier(customerId);
    return transactionRepository.save(transaction);
}

private Transaction buildTransaction(Customer customer, Double amount,
                                     TransactionType type, String description) {
    Transaction t = new Transaction();
    t.setCustomer(customer);
    t.setAmount(amount);
    t.setType(type);
    t.setDescription(description);
    t.setTransactionDate(LocalDateTime.now());
    return t;
}
```

</details>

---

## 🔗 What's Next?

- [Scenario 05 — Document the API](scenario-05-document-api.md) — add documentation after cleaning up the code
- Go back to [Scenario 02](scenario-02-pr-review.md) and apply the same refactoring lens to the PR code reviewed there
