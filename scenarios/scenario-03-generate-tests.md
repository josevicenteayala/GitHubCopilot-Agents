# 🧪 Scenario 03 — Generate Tests for EligibilityService

**Difficulty:** 🟡 Intermediate  
**Estimated Time:** 30–40 minutes  
**Agent:** [Testing Agent](../agents/testing-agent.md)  
**Prerequisite:** Complete [Scenario 01](scenario-01-complete-feature.md) first

---

## 📖 Description

You've implemented `EligibilityService.isEligibleForOffer()` in Scenario 01. Now it's time to prove it works correctly by generating a comprehensive test suite.

A good test suite doesn't just test the happy path — it tests every boundary condition, every branch, and every failure mode. In this scenario, you'll use the **Testing Agent** to generate a complete `EligibilityServiceTest` class that covers all of these cases.

---

## ✅ Prerequisites

- [ ] `EligibilityService.isEligibleForOffer()` is implemented (Scenario 01 complete)
- [ ] `EligibilityService.checkTransactionLimit()` is implemented
- [ ] `mvn compile` passes with no errors
- [ ] GitHub Copilot is installed and signed in

---

## 🗂️ Relevant Files

| File | Purpose |
|------|---------|
| `src/main/java/com/loyalty/rewards/service/EligibilityService.java` | Class under test |
| `src/main/java/com/loyalty/rewards/domain/Customer.java` | Test data builder target |
| `src/main/java/com/loyalty/rewards/domain/Offer.java` | Test data builder target |
| `src/main/java/com/loyalty/rewards/domain/OfferStatus.java` | Enum values: ACTIVE, INACTIVE, EXPIRED |
| `src/main/java/com/loyalty/rewards/repository/TransactionRepository.java` | Mock target |
| `src/test/java/com/loyalty/rewards/service/CustomerServiceTest.java` | Reference for test style |

---

## 📊 Test Coverage Requirements

| Method | Minimum Test Cases |
|--------|--------------------|
| `isEligibleForOffer` | ≥ 6 tests (all-pass, each condition failing, edge cases) |
| `hasMinimumPoints` | ≥ 3 tests (below, exact, above) |
| `isTierEligible` | ≥ 5 tests (BRONZE/SILVER/GOLD combos, null targetTier) |
| `isOfferActive` | ≥ 4 tests (ACTIVE/INACTIVE, boundary dates) |
| `checkTransactionLimit` | ≥ 3 tests (under, exact, over limit) |
| **Total** | **≥ 21 tests** |

**Coverage targets:**
- Line coverage: ≥ 85%
- Branch coverage: ≥ 80%

---

## 🪜 Step-by-Step Instructions

### Step 1 — Review the Existing Test Style

Open `src/test/java/com/loyalty/rewards/service/CustomerServiceTest.java` to understand the project's test conventions:
- `@ExtendWith(MockitoExtension.class)`
- `@Mock` and `@InjectMocks`
- Method naming: `should_[expected]_when_[condition]`
- AAA pattern (Arrange, Act, Assert)

### Step 2 — Activate the Testing Agent

Open GitHub Copilot Chat and paste this system prompt:

```
You are a QA engineer writing JUnit 5 tests for a Spring Boot 3.x loyalty rewards application.
Tech stack: Java 17, Spring Boot 3.2.5, JUnit 5, Mockito 5.x.

Conventions:
1. @ExtendWith(MockitoExtension.class) — not @SpringBootTest
2. @Mock for dependencies, @InjectMocks for class under test
3. Method names: should_[expectedBehaviour]_when_[condition]
4. AAA pattern with // Arrange, // Act, // Assert comments
5. One behaviour per @Test method
6. Use assertFalse/assertTrue with a descriptive failure message
7. Use @BeforeEach for shared test data setup
8. For early-return cases, verify mocks are not called using verifyNoInteractions()

Coverage target: 85% line, 80% branch.
```

### Step 3 — Request Test Generation

In Copilot Chat, type:

```
Generate a complete JUnit 5 test class for EligibilityService.
Place it at: src/test/java/com/loyalty/rewards/service/EligibilityServiceTest.java

The class under test has these public methods:
- isEligibleForOffer(Customer customer, Offer offer): returns true only if all four conditions pass:
    1. hasMinimumPoints: customer.loyaltyPoints >= offer.minPointsRequired
    2. isTierEligible: customer tier >= offer targetTier (BRONZE < SILVER < GOLD), null = any tier ok
    3. isOfferActive: offer.status == ACTIVE and today within validFrom..validTo inclusive
    4. checkTransactionLimit: customer's transaction count < 50
- hasMinimumPoints(Customer customer, int minPoints)
- isTierEligible(Customer customer, String targetTier)
- isOfferActive(Offer offer)
- checkTransactionLimit(Long customerId, int maxTransactions)

Mock: TransactionRepository (has findByCustomerId returning List<Transaction>)

Required test scenarios for isEligibleForOffer:
1. All four conditions true → should return true
2. Points below minimum → should return false (verify transactionRepository NOT called)
3. Tier too low (BRONZE customer, GOLD offer) → should return false
4. Offer status is INACTIVE → should return false
5. Offer expired (validTo = yesterday) → should return false
6. Too many transactions (50 transactions) → should return false
7. Exactly at point minimum (5000 points, offer needs 5000) → should return true
8. Offer valid date is today (validTo = today) → should return true

Required tests for isTierEligible:
- GOLD customer, BRONZE target → true
- SILVER customer, GOLD target → false
- BRONZE customer, null target → true
- GOLD customer, GOLD target → true
- unknown tier → false

Required tests for isOfferActive:
- ACTIVE offer within date range → true
- INACTIVE offer → false
- ACTIVE offer where validFrom is future → false
- ACTIVE offer where validTo is today → true

Required tests for checkTransactionLimit:
- 0 transactions, limit 50 → true
- 49 transactions, limit 50 → true
- 50 transactions, limit 50 → false
```

### Step 4 — Review the Generated Test Class

Check the generated test class for:
- [ ] Correct package: `com.loyalty.rewards.service`
- [ ] `@ExtendWith(MockitoExtension.class)` present
- [ ] `@Mock TransactionRepository transactionRepository`
- [ ] `@InjectMocks EligibilityService eligibilityService`
- [ ] `@BeforeEach setUp()` initialises shared `Customer` and `Offer` objects
- [ ] All methods use the naming convention
- [ ] Each test has Arrange/Act/Assert structure
- [ ] Early-return tests use `verifyNoInteractions(transactionRepository)`

### Step 5 — Run the Tests

```bash
# Run just the eligibility tests
mvn test -Dtest=EligibilityServiceTest

# Run all tests to ensure nothing is broken
mvn test
```

### Step 6 — Check Coverage (Optional)

If Jacoco is configured:
```bash
mvn test jacoco:report
open target/site/jacoco/index.html
```

---

## 🏆 Acceptance Criteria

- [ ] Test class is in the correct package and file location
- [ ] At least 21 `@Test` methods are generated
- [ ] All tests in `EligibilityServiceTest` pass (`mvn test` shows 0 failures)
- [ ] All pre-existing tests still pass
- [ ] At least one test uses `verifyNoInteractions()` to confirm short-circuit evaluation
- [ ] At least one boundary test (exactly at minimum points, exactly at date boundary)
- [ ] The test class compiles and can be run independently

---

## 💡 Tips

- If Copilot generates too many lines in one go, ask for one method's tests at a time
- Ask Copilot: *"Add a test for the case where validFrom and validTo are the same day as today"*
- If a generated test imports the wrong classes, ask: *"Fix the import statements"*
- Run the tests after each set of additions: `mvn test -Dtest=EligibilityServiceTest`

---

## 🔗 What's Next?

- [Scenario 04 — Refactor the Transaction Service](scenario-04-refactor-code.md)
- [Scenario 05 — Document the API](scenario-05-document-api.md)
