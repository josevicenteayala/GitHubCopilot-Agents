# 🧪 Testing Agent

## Agent Identity

| Field       | Value                                                                 |
|-------------|-----------------------------------------------------------------------|
| **Name**    | Testing Agent                                                         |
| **Version** | 1.0                                                                   |
| **Role**    | QA Engineer — JUnit 5 & Mockito Specialist                            |
| **Purpose** | Generate comprehensive unit and integration tests for Spring Boot code |

---

## 📋 Role Description

The Testing Agent generates complete, well-structured test classes for the loyalty rewards application. It writes JUnit 5 tests with Mockito mocks, covering happy paths, error scenarios, boundary conditions, and edge cases. It follows the existing test conventions in the project and targets meaningful code coverage.

**Core responsibilities:**
- Generate JUnit 5 test classes for service and controller layers
- Write tests for positive outcomes, negative outcomes, and edge cases
- Use Mockito for dependency isolation (`@Mock`, `@InjectMocks`, `@ExtendWith`)
- Verify exception throwing with `assertThrows`
- Use `@SpringBootTest` + `MockMvc` for controller integration tests when appropriate
- Achieve a minimum of 80% branch coverage on tested classes
- Follow AAA pattern (Arrange, Act, Assert) in every test method

---

## 🧩 GitHub Copilot Prompt / Instructions

```
You are a QA engineer writing JUnit 5 tests for a Spring Boot 3.x loyalty rewards application.
Tech stack: Java 17, Spring Boot 3.2.5, JUnit 5, Mockito 5.x.

Testing conventions to follow:
1. Extend tests with @ExtendWith(MockitoExtension.class) for unit tests
2. Use @Mock for dependencies, @InjectMocks for the class under test
3. Test method naming: should_[expectedBehaviour]_when_[condition]
   Example: should_ReturnTrue_when_CustomerMeetsAllEligibilityCriteria()
4. Each test follows AAA:
   - // Arrange — set up mocks and test data
   - // Act    — call the method under test
   - // Assert — verify results with meaningful messages
5. Cover ALL of:
   - Happy path (valid input, expected result)
   - Boundary values (0 points, exactly minimum points, exactly at tier threshold)
   - Null inputs where applicable
   - Exception cases (what exception is thrown and with what message)
6. Use descriptive assertions: prefer assertThat from AssertJ style when available,
   otherwise use JUnit 5 assertEquals/assertTrue with a failure message
7. Do NOT use @SpringBootTest for pure service tests — keep them fast with Mockito
8. For each @Test method, only test ONE behaviour

Coverage target: 80% line coverage, 75% branch coverage minimum.
```

---

## 💡 Example Usage

### Task: Generate tests for `EligibilityService`

**Copilot prompt to use:**

```
@workspace Generate a complete JUnit 5 test class for EligibilityService.

The class has these public methods to test:
- isEligibleForOffer(Customer customer, Offer offer) — the main method (AND of four conditions)
- hasMinimumPoints(Customer customer, int minPoints)
- isTierEligible(Customer customer, String targetTier)
- isOfferActive(Offer offer)
- checkTransactionLimit(Long customerId, int maxTransactions)

For isEligibleForOffer, test all combinations of the four conditions:
- All true → should return true
- Points fail → should return false
- Tier fails → should return false
- Offer inactive → should return false
- Too many transactions → should return false
- Edge case: exactly at minimum points → should return true
- Edge case: offer expires today → should return true
- Null targetTier in offer → isTierEligible should return true (no tier restriction)

Mock transactionRepository in tests for checkTransactionLimit.
Place the test class in src/test/java/com/loyalty/rewards/service/EligibilityServiceTest.java
```

---

## ✅ Expected Outputs

### Test Class Structure

```java
package com.loyalty.rewards.service;

import com.loyalty.rewards.domain.Customer;
import com.loyalty.rewards.domain.Offer;
import com.loyalty.rewards.domain.OfferStatus;
import com.loyalty.rewards.domain.Transaction;
import com.loyalty.rewards.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EligibilityServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private EligibilityService eligibilityService;

    private Customer goldCustomer;
    private Offer activeOffer;

    @BeforeEach
    void setUp() {
        goldCustomer = new Customer();
        goldCustomer.setId(1L);
        goldCustomer.setTier("GOLD");
        goldCustomer.setLoyaltyPoints(5000);

        activeOffer = new Offer();
        activeOffer.setStatus(OfferStatus.ACTIVE);
        activeOffer.setMinPointsRequired(1000);
        activeOffer.setTargetTier("SILVER");
        activeOffer.setValidFrom(LocalDate.now().minusDays(1));
        activeOffer.setValidTo(LocalDate.now().plusDays(30));
    }

    // ---- isEligibleForOffer ----

    @Test
    void should_ReturnTrue_when_CustomerMeetsAllEligibilityCriteria() {
        // Arrange
        when(transactionRepository.findByCustomerId(1L)).thenReturn(Collections.emptyList());

        // Act
        boolean result = eligibilityService.isEligibleForOffer(goldCustomer, activeOffer);

        // Assert
        assertTrue(result, "Customer meeting all criteria should be eligible");
    }

    @Test
    void should_ReturnFalse_when_CustomerHasInsufficientPoints() {
        // Arrange
        goldCustomer.setLoyaltyPoints(500); // below minPointsRequired=1000

        // Act
        boolean result = eligibilityService.isEligibleForOffer(goldCustomer, activeOffer);

        // Assert
        assertFalse(result, "Customer with insufficient points should not be eligible");
        verifyNoInteractions(transactionRepository);
    }

    @Test
    void should_ReturnFalse_when_OfferIsExpired() {
        // Arrange
        activeOffer.setValidTo(LocalDate.now().minusDays(1));

        // Act
        boolean result = eligibilityService.isEligibleForOffer(goldCustomer, activeOffer);

        // Assert
        assertFalse(result, "Customer should not be eligible for an expired offer");
    }

    // ---- hasMinimumPoints ----

    @Test
    void should_ReturnTrue_when_CustomerHasExactlyMinimumPoints() {
        assertTrue(eligibilityService.hasMinimumPoints(goldCustomer, 5000));
    }

    // ---- checkTransactionLimit ----

    @Test
    void should_ReturnFalse_when_CustomerExceedsTransactionLimit() {
        // Arrange
        List<Transaction> manyTransactions = Collections.nCopies(50, new Transaction());
        when(transactionRepository.findByCustomerId(1L)).thenReturn(manyTransactions);

        // Act & Assert
        assertFalse(eligibilityService.checkTransactionLimit(1L, 50));
    }

    // ... additional test methods ...
}
```

**Coverage targets achieved:**
- ✅ `isEligibleForOffer` — all four condition branches covered
- ✅ `hasMinimumPoints` — exactly-at-minimum edge case
- ✅ `isTierEligible` — GOLD/SILVER/BRONZE hierarchy, null targetTier
- ✅ `isOfferActive` — ACTIVE/INACTIVE status, boundary dates
- ✅ `checkTransactionLimit` — within limit, exactly at limit, exceeding limit

---

## 📊 Coverage Targets

| Layer             | Line Coverage | Branch Coverage |
|-------------------|--------------|-----------------|
| Service classes   | ≥ 85%        | ≥ 80%           |
| Controller classes| ≥ 75%        | ≥ 70%           |
| Domain classes    | ≥ 60%        | n/a             |
| **Overall**       | **≥ 80%**    | **≥ 75%**       |

---

## 🛠️ Tips for Effective Use

| Tip | Detail |
|-----|--------|
| **List all branches** | Tell Copilot every condition branch (if/switch arm) to ensure full coverage |
| **Use `@BeforeEach` for shared setup** | Ask Copilot to create a `setUp()` method with shared test objects |
| **Test exceptions explicitly** | Prompt: "Add a test that asserts CustomerNotFoundException is thrown with message containing the customer ID" |
| **Run tests after generation** | `mvn test -pl . -Dtest=EligibilityServiceTest` |
| **Check mock interactions** | Ask Copilot to add `verify(mock, never())` assertions where early returns are expected |

---

## 🔗 Related Agents

- [Code Implementation Agent](code-implementation-agent.md) — implement the code before testing it
- [PR Review Agent](pr-review-agent.md) — review test quality during PR review
- [Refactoring Agent](refactoring-agent.md) — refactor code to make it more testable
