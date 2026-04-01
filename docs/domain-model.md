# 🗂️ Domain Model

This document describes the business domain of the **Loyalty Rewards** application — the entities, relationships, and business rules that govern how the system works.

---

## 🏪 Business Context

The **Loyalty Rewards System** is a points-based loyalty programme for a retail business. Customers earn points when they make purchases and can redeem those points for promotional offers and discounts. The programme uses a tiered structure to reward high-spending customers with exclusive offers.

**Core value proposition:**
- Customers earn points at every purchase
- Points accumulate to unlock higher tiers (BRONZE → SILVER → GOLD)
- Higher-tier customers gain access to exclusive, higher-value offers
- Offers are time-limited and may require a minimum point balance

---

## 📐 Entity Descriptions

### 👤 Customer

Represents a person enrolled in the loyalty programme.

| Field | Type | Description |
|-------|------|-------------|
| `id` | Long | Auto-generated primary key |
| `name` | String | Customer's full name |
| `email` | String | Unique email address (used as identity) |
| `tier` | String | Loyalty tier: `BRONZE`, `SILVER`, or `GOLD` |
| `loyaltyPoints` | Integer | Total accumulated points (never negative) |
| `createdAt` | LocalDateTime | Enrolment timestamp (set automatically) |

**Defaults on creation:**
- `tier` defaults to `BRONZE`
- `loyaltyPoints` defaults to `0`

---

### 🎁 Offer

Represents a promotional offer available to eligible customers.

| Field | Type | Description |
|-------|------|-------------|
| `id` | Long | Auto-generated primary key |
| `title` | String | Short display name for the offer |
| `description` | String | Full description of the offer terms |
| `discountPercentage` | Double | Discount applied (e.g., `20.0` = 20% off) |
| `minPointsRequired` | Integer | Minimum loyalty points needed to qualify |
| `status` | OfferStatus | `ACTIVE`, `INACTIVE`, or `EXPIRED` |
| `validFrom` | LocalDate | First day the offer is valid (inclusive) |
| `validTo` | LocalDate | Last day the offer is valid (inclusive) |
| `targetTier` | String | Minimum tier required (`BRONZE`/`SILVER`/`GOLD`). Null means no tier restriction. |

---

### 💳 LoyaltyAccount

Represents the accounting record for a customer's points balance. Links the Customer entity to their historical point movements.

| Field | Type | Description |
|-------|------|-------------|
| `id` | Long | Auto-generated primary key |
| `customer` | Customer | The associated customer (one-to-one) |
| `totalPoints` | Integer | Running total of all earned points |
| `redeemedPoints` | Integer | Total points redeemed to date |

---

### 🔄 Transaction

Represents a single loyalty event — either a purchase (points earned) or a redemption (points spent).

| Field | Type | Description |
|-------|------|-------------|
| `id` | Long | Auto-generated primary key |
| `customer` | Customer | The customer who made the transaction |
| `amount` | Double | Monetary amount of the purchase (0 for redemptions) |
| `type` | TransactionType | `EARN`, `REDEEM`, or `ADJUSTMENT` |
| `pointsEarned` | Integer | Points awarded in this transaction |
| `pointsRedeemed` | Integer | Points spent in this transaction |
| `description` | String | Human-readable description |
| `transactionDate` | LocalDateTime | When the transaction occurred |

---

## 🏅 Tier System

Customers are automatically promoted (but not demoted) based on their cumulative `loyaltyPoints`.

| Tier | Points Range | Benefits |
|------|-------------|----------|
| 🥉 BRONZE | 0 – 999 pts | Access to standard offers |
| 🥈 SILVER | 1,000 – 4,999 pts | Access to standard + silver-tier offers |
| 🥇 GOLD | 5,000+ pts | Access to all offers, including exclusive gold-tier offers |

**Tier hierarchy for eligibility:** `BRONZE < SILVER < GOLD`

A GOLD customer qualifies for SILVER and BRONZE targeted offers. A BRONZE customer does NOT qualify for SILVER or GOLD targeted offers.

**Tier recalculation** happens automatically after every EARN transaction via `CustomerService.updateCustomerTier()`.

---

## 💰 Points System

| Rule | Detail |
|------|--------|
| **Earn rate** | 10 points per $1.00 spent |
| **Example** | $25.50 purchase → 255 points |
| **Calculation** | `points = (int)(amount * 10)` |
| **Redemption** | Points redeemed directly via `TransactionService.redeemPoints()` |
| **Minimum balance** | A customer cannot redeem more points than they hold |

---

## 📋 Business Rules Summary

| Rule | Implementation |
|------|----------------|
| New customers start at BRONZE with 0 points | `Customer.onCreate()` @PrePersist |
| Tier promotes automatically after earning | `CustomerService.updateCustomerTier()` |
| Offer eligibility requires: points, tier, active status, and transaction count < 50 | `EligibilityService.isEligibleForOffer()` |
| An offer is active only when status=ACTIVE AND today is within validFrom..validTo | `EligibilityService.isOfferActive()` |
| Customers cannot redeem more points than they have | `TransactionService.redeemPoints()` validation |
| Transaction limit per customer: max 50 total transactions for offer eligibility | `EligibilityService.checkTransactionLimit()` |

---

## 🗺️ Entity Relationship Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                    LOYALTY REWARDS DOMAIN                    │
└─────────────────────────────────────────────────────────────┘

    ┌──────────────┐         ┌──────────────────┐
    │   Customer   │         │   LoyaltyAccount │
    │──────────────│  1   1  │──────────────────│
    │ id           │─────────│ id               │
    │ name         │         │ customer_id (FK) │
    │ email        │         │ totalPoints      │
    │ tier         │         │ redeemedPoints   │
    │ loyaltyPoints│         └──────────────────┘
    │ createdAt    │
    └──────┬───────┘
           │ 1
           │
           │ many
           ▼
    ┌──────────────┐
    │ Transaction  │
    │──────────────│
    │ id           │
    │ customer_id  │  (FK → Customer)
    │ amount       │
    │ type         │  EARN | REDEEM | ADJUSTMENT
    │ pointsEarned │
    │ pointsRedeem │
    │ description  │
    │ transactionDt│
    └──────────────┘


    ┌──────────────┐
    │    Offer     │
    │──────────────│
    │ id           │
    │ title        │
    │ description  │
    │ discountPct  │
    │ minPointsReq │
    │ status       │  ACTIVE | INACTIVE | EXPIRED
    │ validFrom    │
    │ validTo      │
    │ targetTier   │  null | BRONZE | SILVER | GOLD
    └──────────────┘

    ╔═══════════════════════════════════════════╗
    ║          ELIGIBILITY CHECK FLOW           ║
    ╠═══════════════════════════════════════════╣
    ║  Customer ──► EligibilityService ◄── Offer║
    ║                     │                     ║
    ║             4 conditions (AND):            ║
    ║             1. points >= minPointsRequired ║
    ║             2. tier >= targetTier          ║
    ║             3. offer status=ACTIVE         ║
    ║                  & within date range       ║
    ║             4. transaction count < 50      ║
    ╚═══════════════════════════════════════════╝
```

---

## 🌱 Sample Data

The application loads sample data from `src/main/resources/data.sql` on startup:

| Entity | Count | Description |
|--------|-------|-------------|
| Customers | 5 | Alice (GOLD), Bob (SILVER), Carol (BRONZE), David (SILVER), Eve (GOLD) |
| Offers | 4 | Mix of BRONZE, SILVER, GOLD targeted offers with varying point requirements |
| Transactions | 8 | Historical EARN transactions for the sample customers |

The H2 console at [http://localhost:8080/h2-console](http://localhost:8080/h2-console) shows the live data.
