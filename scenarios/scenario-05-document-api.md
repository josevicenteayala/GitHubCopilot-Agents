# 📚 Scenario 05 — Document the Loyalty Rewards API

**Difficulty:** 🟢 Beginner  
**Estimated Time:** 20–30 minutes  
**Agent:** [Documentation Agent](../agents/documentation-agent.md)

---

## 📖 Description

The REST controllers in the loyalty rewards application currently have no documentation. This makes the API hard to explore, understand, and consume — especially for new team members or front-end developers.

In this scenario, you'll use the **Documentation Agent** to generate:
1. **JavaDoc** on the controller class and each endpoint method
2. **OpenAPI annotations** (`@Tag`, `@Operation`, `@ApiResponse`, `@Parameter`) for interactive API docs
3. A **README-style API summary** you can paste into the project docs

You'll work primarily with `CustomerController.java`, then optionally extend to `OfferController` and `TransactionController`.

---

## ✅ Prerequisites

- [ ] GitHub Copilot is installed and signed in
- [ ] Application runs: `mvn spring-boot:run` (needed to verify docs at `/swagger-ui.html`)
- [ ] You have read `CustomerController.java`

---

## 🗂️ Relevant Files

| File | Purpose |
|------|---------|
| `src/main/java/com/loyalty/rewards/controller/CustomerController.java` | Primary target |
| `src/main/java/com/loyalty/rewards/controller/OfferController.java` | Secondary target |
| `src/main/java/com/loyalty/rewards/controller/TransactionController.java` | Secondary target |
| `src/main/java/com/loyalty/rewards/dto/CustomerDTO.java` | DTO to annotate with `@Schema` |
| `pom.xml` | Will need SpringDoc dependency if not present |

---

## 🪜 Step-by-Step Instructions

### Step 1 — Check if SpringDoc is Available

Look at `pom.xml` for a SpringDoc dependency. If it's not present, add it:

```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.5.0</version>
</dependency>
```

Run `mvn compile` to verify the dependency resolves.

### Step 2 — Activate the Documentation Agent

Open GitHub Copilot Chat and paste this system prompt:

```
You are a technical writer specialising in Java Spring Boot applications.
You write clear, accurate, and professional documentation.

Tech stack: Java 17, Spring Boot 3.2.5, SpringDoc OpenAPI 2.x.

Documentation standards:
1. JavaDoc: every public class and public method gets a JavaDoc block
   - Class: one-sentence purpose summary
   - Method: what it does, @param for each, @return, @throws
   - Do NOT document trivial getters/setters
2. OpenAPI annotations (springdoc):
   - @Tag(name=..., description=...) on controller class
   - @Operation(summary=..., description=...) on each endpoint
   - @ApiResponse for each possible HTTP status code
   - @Parameter(description=...) for path variables and query params
   - @Schema(description=...) on DTO fields
3. Focus on the WHY and business rules, not just WHAT
4. Include example curl commands in Markdown documentation
5. HTTP status codes for this project:
   - GET → 200 OK, 404 Not Found
   - POST → 201 Created, 400 Bad Request
   - General errors → 500 Internal Server Error
```

### Step 3 — Document `CustomerController`

In Copilot Chat, type:

```
Add complete documentation to CustomerController.java.

The controller has these endpoints:
- GET /api/customers → returns all customers
- GET /api/customers/{id} → returns one customer by ID, 404 if not found
- POST /api/customers → creates a new customer, new customers start at BRONZE tier with 0 points
- GET /api/customers/{id}/offers → returns all eligible offers for the customer based on their
  tier and loyalty points; 404 if customer not found

Add:
1. Class-level @Tag and JavaDoc
2. @Operation with summary and description on each method
3. @ApiResponse for each possible response (200, 201, 400, 404)
4. @Parameter on {id} path variables
5. Method-level JavaDoc with @param, @return, @throws

Business context:
- Customers are loyalty program members with a tier (BRONZE/SILVER/GOLD) and points balance
- Offers are returned based on tier eligibility, minimum points, and offer active status
- CustomerNotFoundException results in a 404 response (handled by GlobalExceptionHandler)

Show me the complete annotated CustomerController.java file.
```

### Step 4 — Document `CustomerDTO`

Request `@Schema` annotations on the DTO:

```
Add @Schema annotations to CustomerDTO.java fields:
- id: "Unique customer identifier, auto-generated"
- name: "Customer's full name"
- email: "Customer's unique email address used for login"
- tier: "Loyalty tier: BRONZE (0-999 pts), SILVER (1000-4999 pts), GOLD (5000+ pts)"
- loyaltyPoints: "Accumulated loyalty points. 10 points earned per dollar spent"
- createdAt: "Timestamp when the customer enrolled in the loyalty program"
```

### Step 5 — Generate API Reference Markdown

Ask Copilot to generate a markdown summary:

```
Generate a Markdown API reference section for the Customers endpoints.
Format it as a table of endpoints, followed by request/response JSON examples
and curl commands for each endpoint.
Include the business rules for how offers are filtered.
```

### Step 6 — Verify with Swagger UI

Start the application and navigate to the API documentation:

```bash
mvn spring-boot:run
```

Open in your browser: http://localhost:8080/swagger-ui.html

Verify:
- [ ] "Customers" tag appears in the UI
- [ ] All 4 endpoints are visible with their summaries
- [ ] Response schemas are correct
- [ ] Try the "GET /api/customers" endpoint using the "Try it out" button

---

## 🏆 Acceptance Criteria

- [ ] `CustomerController` has a class-level `@Tag` annotation with name and description
- [ ] Every endpoint method has `@Operation(summary=..., description=...)`
- [ ] Every endpoint has `@ApiResponse` for each HTTP status it can return
- [ ] Path variable `{id}` has `@Parameter(description=...)` in at least 2 methods
- [ ] Class-level JavaDoc explains the controller's purpose
- [ ] Each public method has JavaDoc with `@param`, `@return`, and `@throws` as appropriate
- [ ] Application starts with `mvn spring-boot:run` and Swagger UI is accessible
- [ ] Swagger UI shows the Customers section with all 4 endpoints

---

## 💡 Tips

- Ask Copilot: *"What is the difference between @ApiResponse and @ApiResponses?"*
- Ask Copilot: *"Show me an example of @Schema with allowableValues for the tier field"*
- To see your annotations immediately: `mvn spring-boot:run` and refresh the Swagger UI
- For the `OfferController`, use the same prompt but adapt the endpoint descriptions

---

## 🔧 Optional Extension

Once `CustomerController` is documented, extend to the other controllers:

**OfferController:**
- `GET /api/offers` — list all offers in the system
- `GET /api/offers/{id}` — get offer by ID
- `POST /api/offers` — create a new promotional offer
- `POST /api/offers/{offerId}/assign/{customerId}` — assign an offer to a customer

**TransactionController:**
- `GET /api/transactions/customer/{customerId}` — transaction history for a customer
- `POST /api/transactions` — record a new transaction (EARN or REDEEM)

---

## 🔗 What's Next?

- Review the documentation output using the [PR Review Agent](../agents/pr-review-agent.md)
- Read the complete [API Reference](../docs/api-reference.md) for all endpoints
- Return to [Scenario 01](scenario-01-complete-feature.md) and document the `EligibilityService` methods you implemented
