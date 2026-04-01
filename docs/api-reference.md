# 📡 API Reference

The Loyalty Rewards API is a RESTful JSON API built with Spring Boot. All endpoints return and accept `application/json`.

---

## 🌐 Base URL

```
http://localhost:8080
```

---

## 📋 Endpoints Overview

| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/customers` | List all customers |
| GET | `/api/customers/{id}` | Get a customer by ID |
| POST | `/api/customers` | Create a new customer |
| GET | `/api/customers/{id}/offers` | Get eligible offers for a customer |
| GET | `/api/offers` | List all offers |
| GET | `/api/offers/{id}` | Get an offer by ID |
| POST | `/api/offers` | Create a new offer |
| POST | `/api/offers/{offerId}/assign/{customerId}` | Assign an offer to a customer |
| GET | `/api/transactions/customer/{customerId}` | Get transactions for a customer |
| POST | `/api/transactions` | Create a new transaction |

---

## 👤 Customer Endpoints

### `GET /api/customers`

Returns all customers enrolled in the loyalty programme.

**Response: 200 OK**
```json
[
  {
    "id": 1,
    "name": "Alice Johnson",
    "email": "alice@example.com",
    "tier": "GOLD",
    "loyaltyPoints": 7500,
    "createdAt": "2024-01-15T09:00:00"
  },
  {
    "id": 2,
    "name": "Bob Smith",
    "email": "bob@example.com",
    "tier": "SILVER",
    "loyaltyPoints": 2500,
    "createdAt": "2024-02-20T14:30:00"
  }
]
```

**cURL Example:**
```bash
curl -s http://localhost:8080/api/customers | jq .
```

---

### `GET /api/customers/{id}`

Returns a single customer by their unique ID.

**Path Parameters:**

| Parameter | Type | Description |
|-----------|------|-------------|
| `id` | Long | Unique customer identifier |

**Response: 200 OK**
```json
{
  "id": 1,
  "name": "Alice Johnson",
  "email": "alice@example.com",
  "tier": "GOLD",
  "loyaltyPoints": 7500,
  "createdAt": "2024-01-15T09:00:00"
}
```

**Response: 404 Not Found**
```json
{
  "error": "Customer not found with id: 99"
}
```

**cURL Example:**
```bash
curl -s http://localhost:8080/api/customers/1
```

---

### `POST /api/customers`

Enrols a new customer in the loyalty programme. New customers start at BRONZE tier with 0 points.

**Request Body:**
```json
{
  "name": "Jane Doe",
  "email": "jane.doe@example.com"
}
```

| Field | Required | Description |
|-------|----------|-------------|
| `name` | ✅ Yes | Customer's full name |
| `email` | ✅ Yes | Must be unique across all customers |
| `tier` | ❌ No | Defaults to `BRONZE` if omitted |
| `loyaltyPoints` | ❌ No | Defaults to `0` if omitted |

**Response: 201 Created**
```json
{
  "id": 6,
  "name": "Jane Doe",
  "email": "jane.doe@example.com",
  "tier": "BRONZE",
  "loyaltyPoints": 0,
  "createdAt": "2024-06-01T10:15:00"
}
```

**Response: 400 Bad Request** (duplicate email or missing required fields)
```json
{
  "error": "Email already in use: jane.doe@example.com"
}
```

**cURL Example:**
```bash
curl -s -X POST http://localhost:8080/api/customers \
  -H "Content-Type: application/json" \
  -d '{"name": "Jane Doe", "email": "jane.doe@example.com"}'
```

---

### `GET /api/customers/{id}/offers`

Returns all offers for which the specified customer is currently eligible.

**Eligibility criteria (all must be true):**
- Customer's `loyaltyPoints` ≥ offer's `minPointsRequired`
- Customer's tier equals or exceeds offer's `targetTier`
- Offer `status` is `ACTIVE` and today's date is within `validFrom`..`validTo`
- Customer has fewer than 50 total transactions

**Response: 200 OK**
```json
[
  {
    "id": 2,
    "title": "Gold Member Exclusive 30% Off",
    "description": "Exclusive discount for our top-tier members",
    "discountPercentage": 30.0,
    "minPointsRequired": 5000,
    "status": "ACTIVE",
    "validFrom": "2024-01-01",
    "validTo": "2024-12-31",
    "targetTier": "GOLD"
  }
]
```

**Response: 200 OK** (no eligible offers — returns empty array, not 404)
```json
[]
```

**Response: 404 Not Found** (customer does not exist)
```json
{
  "error": "Customer not found with id: 99"
}
```

**cURL Example:**
```bash
curl -s http://localhost:8080/api/customers/1/offers
```

---

## 🎁 Offer Endpoints

### `GET /api/offers`

Returns all offers in the system regardless of status.

**Response: 200 OK**
```json
[
  {
    "id": 1,
    "title": "Welcome Bonus 10% Off",
    "description": "Introductory offer for new members",
    "discountPercentage": 10.0,
    "minPointsRequired": 0,
    "status": "ACTIVE",
    "validFrom": "2024-01-01",
    "validTo": "2024-12-31",
    "targetTier": null
  }
]
```

**cURL Example:**
```bash
curl -s http://localhost:8080/api/offers
```

---

### `GET /api/offers/{id}`

Returns a single offer by ID.

**Response: 200 OK** — offer JSON (see above)  
**Response: 404 Not Found** — `{"error": "Offer not found with id: 99"}`

**cURL Example:**
```bash
curl -s http://localhost:8080/api/offers/1
```

---

### `POST /api/offers`

Creates a new promotional offer.

**Request Body:**
```json
{
  "title": "Summer Sale 25% Off",
  "description": "Exclusive summer discount for silver and above",
  "discountPercentage": 25.0,
  "minPointsRequired": 1000,
  "status": "ACTIVE",
  "validFrom": "2024-06-01",
  "validTo": "2024-08-31",
  "targetTier": "SILVER"
}
```

| Field | Required | Description |
|-------|----------|-------------|
| `title` | ✅ Yes | Short display name |
| `discountPercentage` | ✅ Yes | Percentage discount (0.0 – 100.0) |
| `minPointsRequired` | ✅ Yes | Minimum points to qualify (0 = no minimum) |
| `status` | ✅ Yes | `ACTIVE`, `INACTIVE`, or `EXPIRED` |
| `validFrom` | ✅ Yes | Start date (ISO format: `YYYY-MM-DD`) |
| `validTo` | ✅ Yes | End date (ISO format: `YYYY-MM-DD`) |
| `targetTier` | ❌ No | `BRONZE`, `SILVER`, `GOLD`, or null (any tier) |

**Response: 201 Created** — full offer JSON including generated `id`

**cURL Example:**
```bash
curl -s -X POST http://localhost:8080/api/offers \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Summer Sale 25% Off",
    "description": "For silver members",
    "discountPercentage": 25.0,
    "minPointsRequired": 1000,
    "status": "ACTIVE",
    "validFrom": "2024-06-01",
    "validTo": "2024-08-31",
    "targetTier": "SILVER"
  }'
```

---

### `POST /api/offers/{offerId}/assign/{customerId}`

Assigns an offer to a specific customer (records the redemption).

**Path Parameters:**

| Parameter | Type | Description |
|-----------|------|-------------|
| `offerId` | Long | The offer to assign |
| `customerId` | Long | The customer to assign it to |

**Response: 200 OK**
```json
{
  "message": "Offer successfully assigned to customer"
}
```

**Response: 404 Not Found** — offer or customer does not exist  
**Response: 400 Bad Request** — customer is not eligible for the offer

**cURL Example:**
```bash
curl -s -X POST http://localhost:8080/api/offers/1/assign/2
```

---

## 🔄 Transaction Endpoints

### `GET /api/transactions/customer/{customerId}`

Returns the full transaction history for a customer.

**Path Parameters:**

| Parameter | Type | Description |
|-----------|------|-------------|
| `customerId` | Long | The customer whose history to retrieve |

**Response: 200 OK**
```json
[
  {
    "id": 1,
    "customerId": 1,
    "amount": 150.00,
    "type": "EARN",
    "pointsEarned": 1500,
    "pointsRedeemed": 0,
    "description": "Purchase at Main Street Store",
    "transactionDate": "2024-03-15T14:22:00"
  },
  {
    "id": 5,
    "customerId": 1,
    "amount": 0.00,
    "type": "REDEEM",
    "pointsEarned": 0,
    "pointsRedeemed": 500,
    "description": "Points redemption: 500 points",
    "transactionDate": "2024-04-01T10:00:00"
  }
]
```

**cURL Example:**
```bash
curl -s http://localhost:8080/api/transactions/customer/1
```

---

### `POST /api/transactions`

Records a new transaction for a customer. For `EARN` type, points are automatically calculated and added to the customer's balance.

**Request Body:**
```json
{
  "customerId": 1,
  "amount": 75.50,
  "type": "EARN",
  "description": "Online purchase - order #12345"
}
```

| Field | Required | Description |
|-------|----------|-------------|
| `customerId` | ✅ Yes | ID of the customer |
| `amount` | ✅ Yes | Purchase amount in dollars |
| `type` | ✅ Yes | `EARN`, `REDEEM`, or `ADJUSTMENT` |
| `description` | ❌ No | Human-readable description |

**Points calculation for EARN:** `pointsEarned = (int)(amount × 10)`  
For `$75.50` → `755 points`

**Response: 201 Created**
```json
{
  "id": 9,
  "customerId": 1,
  "amount": 75.50,
  "type": "EARN",
  "pointsEarned": 755,
  "pointsRedeemed": 0,
  "description": "Online purchase - order #12345",
  "transactionDate": "2024-06-01T11:30:00"
}
```

**Response: 404 Not Found** — customer does not exist  
**Response: 400 Bad Request** — insufficient points for REDEEM type

**cURL Example:**
```bash
curl -s -X POST http://localhost:8080/api/transactions \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": 1,
    "amount": 75.50,
    "type": "EARN",
    "description": "Online purchase - order #12345"
  }'
```

---

## ⚠️ Error Responses

All errors follow this structure:

```json
{
  "error": "Human-readable error message"
}
```

| HTTP Status | Cause |
|-------------|-------|
| `400 Bad Request` | Invalid input, missing required field, business rule violation |
| `404 Not Found` | Customer or Offer with specified ID does not exist |
| `500 Internal Server Error` | Unexpected server-side error |

---

## 🔄 Transaction Types

| Type | Description | Points Effect |
|------|-------------|--------------|
| `EARN` | Customer made a purchase | +`(amount × 10)` points |
| `REDEEM` | Customer redeemed points | Points are spent |
| `ADJUSTMENT` | Manual correction | No points movement |
