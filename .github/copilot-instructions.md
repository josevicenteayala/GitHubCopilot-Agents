# Copilot Repository Instructions — Loyalty Rewards

These instructions are automatically included in every GitHub Copilot request made in this
repository (Chat, inline suggestions, and the Copilot coding agent). They provide the
project-wide context that every agent in [`agents/`](../agents/) relies on.

> 📚 **For students:** This file is a worked example of a repository-level Copilot
> instructions file. It is loaded by Copilot whenever you work in this repo. When you
> create your own agents (see [`.github/prompts/`](prompts/) and [`.github/chatmodes/`](chatmodes/)),
> they can assume everything in this file as given and focus on their specialty.

---

## Project Overview

The **Loyalty Rewards** system is a Spring Boot application that manages customers in a
points-based loyalty programme. Customers earn points from transactions, progress through
tiers, and redeem points against offers.

## Tech Stack

- **Language:** Java 17
- **Framework:** Spring Boot 3.2.5
- **Persistence:** Spring Data JPA + H2 (in-memory, `jdbc:h2:mem:loyaltydb`)
- **Build:** Maven (`mvn clean package`, `mvn test`, `mvn spring-boot:run`)
- **Testing:** JUnit 5, Mockito, MockMvc
- **Lombok** is used for boilerplate reduction where annotated.

## Domain Rules

Business rules that must be respected in every implementation, test, or review:

- Customer tiers: `BRONZE`, `SILVER`, `GOLD` (hierarchy `BRONZE < SILVER < GOLD`).
- Tier thresholds by `loyaltyPoints`:
  - `BRONZE` → 0–999
  - `SILVER` → 1,000–4,999
  - `GOLD`   → 5,000+
- Points are earned at **10 points per $1** spent.
- An `Offer` is **valid** only when `status == ACTIVE` **and** today is within
  `validFrom..validTo` (inclusive).
- A customer is **eligible** for an offer only when they meet the offer's
  `minPointsRequired` **and** `targetTier`.

## Coding Conventions

Apply these rules whenever generating, reviewing, or refactoring code in this repo:

1. **Constructor injection only.** Do not use field `@Autowired`. Declare dependencies
   as `private final` and receive them through the constructor (Lombok
   `@RequiredArgsConstructor` is acceptable).
2. **Domain exceptions.** Throw the existing types — do not invent new ones unnecessarily:
   - `CustomerNotFoundException`
   - `OfferNotFoundException`
   - `IneligibleCustomerException`
3. **Transaction boundaries.** `@Transactional` belongs on the service layer. Mark
   read-only service methods with `@Transactional(readOnly = true)`. Controllers must
   never carry `@Transactional`.
4. **Controllers stay thin.** No business logic in controllers — delegate to services.
5. **Prefer Java 17 idioms.** Switch expressions, records, streams, `var` where the type
   is obvious, text blocks for multi-line strings.
6. **No unused fields.** Do not add private fields unless they are actually used.
7. **JavaDoc on public API.** Every public class and public method gets a JavaDoc block.
   Trivial getters/setters do not need JavaDoc. Document the *why* and the business
   rule, not the *how*.
8. **No gratuitous comments.** Only add inline comments where they explain non-obvious
   intent.

## Testing Conventions

1. Use `@ExtendWith(MockitoExtension.class)` for unit tests, `@Mock` for dependencies,
   `@InjectMocks` for the class under test.
2. Do not use `@SpringBootTest` for pure service tests — keep them fast with Mockito.
3. Test method naming: `should_[expectedBehaviour]_when_[condition]`.
4. Follow the **Arrange / Act / Assert** pattern, one behaviour per test.
5. Cover: happy path, boundary values (`0`, exactly minimum, exactly at tier threshold),
   null inputs where applicable, and every thrown exception.
6. Controller tests use `@WebMvcTest` + `MockMvc`.
7. Coverage targets: **80% line**, **75% branch** on tested classes.

## Package / Layer Layout

```
com.loyalty.rewards
 ├── controller   // REST controllers — input validation + delegation only
 ├── service      // Business logic, @Transactional boundaries
 ├── repository   // Spring Data JPA repositories
 ├── domain       // JPA entities + enums (Customer, Offer, Transaction, …)
 ├── dto          // Request/response DTOs (records preferred)
 └── exception    // Custom exceptions + GlobalExceptionHandler
```

## Build & Verification Commands

- Compile: `mvn compile`
- Run tests: `mvn test`
- Package: `mvn clean package`
- Run app: `mvn spring-boot:run` (serves on `http://localhost:8080`)

Always confirm `mvn test` passes before completing any code change.
