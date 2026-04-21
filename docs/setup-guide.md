# 🛠️ Setup Guide

This guide will get the **Loyalty Rewards** application running on your machine and configure GitHub Copilot for the hands-on exercises.

---

## 📋 Prerequisites

Before you begin, ensure the following are installed:

| Tool | Minimum Version | Download |
|------|----------------|----------|
| Java (JDK) | 17 | [Adoptium Temurin 17](https://adoptium.net/) |
| Apache Maven | 3.8+ | [maven.apache.org](https://maven.apache.org/download.cgi) |
| Git | 2.30+ | [git-scm.com](https://git-scm.com/) |
| IDE | Any | [VS Code](https://code.visualstudio.com/) (recommended) or [IntelliJ IDEA](https://www.jetbrains.com/idea/) |
| GitHub Copilot | Current | Requires active GitHub Copilot subscription |

### Verify Prerequisites

```bash
java -version
# Expected: openjdk version "17.x.x" or similar

mvn -version
# Expected: Apache Maven 3.8.x or higher

git --version
# Expected: git version 2.30.x or higher
```

---

## 🚀 Clone and Build

### 1. Clone the Repository

```bash
git clone https://github.com/<your-org>/GitHubCopilot-Agents.git
cd GitHubCopilot-Agents
```

### 2. Build the Project

```bash
mvn clean package -DskipTests
```

A successful build ends with:
```
[INFO] BUILD SUCCESS
[INFO] Total time: XX s
```

If the build fails, check that `JAVA_HOME` points to JDK 17:
```bash
echo $JAVA_HOME          # macOS/Linux
echo %JAVA_HOME%         # Windows
```

---

## ▶️ Running the Application

### Start the Spring Boot Application

```bash
mvn spring-boot:run
```

The application starts on port **8080**. Look for:
```
Started LoyaltyRewardsApplication in X.XXX seconds
```

### Verify the Application is Running

```bash
# List all customers (should return seed data)
curl http://localhost:8080/api/customers

# Expected response:
# [{"id":1,"name":"Alice Johnson","email":"alice@example.com","tier":"GOLD",...}, ...]
```

Or open your browser: [http://localhost:8080/api/customers](http://localhost:8080/api/customers)

### Stop the Application

Press `Ctrl + C` in the terminal running the app.

---

## 🗄️ Accessing the H2 Console

The application uses an in-memory H2 database, pre-loaded with sample data.

1. Start the application: `mvn spring-boot:run`
2. Open: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
3. Use these connection settings:

| Setting | Value |
|---------|-------|
| JDBC URL | `jdbc:h2:mem:loyaltydb` |
| Username | `sa` |
| Password | *(leave blank)* |

4. Click **Connect**
5. You can now run SQL queries, e.g.:
   ```sql
   SELECT * FROM CUSTOMERS;
   SELECT * FROM OFFERS;
   SELECT * FROM TRANSACTIONS;
   ```

> **Note:** Data resets every time the application restarts — H2 is in-memory only.

---

## 🧪 Running Tests

### Run All Tests

```bash
mvn test
```

### Run a Specific Test Class

```bash
mvn test -Dtest=CustomerServiceTest
mvn test -Dtest=EligibilityServiceTest
mvn test -Dtest=CustomerControllerTest
```

### Run Tests Matching a Pattern

```bash
mvn test -Dtest="*ServiceTest"
```

### View Test Results

Test results are saved to `target/surefire-reports/`. To view:
```bash
# macOS
open target/surefire-reports/

# Linux
xdg-open target/surefire-reports/

# Windows
explorer target\surefire-reports\
```

---

## 🤖 GitHub Copilot Setup

### VS Code

1. Install the **GitHub Copilot** extension:
   - Open Extensions: `Ctrl+Shift+X` (Windows/Linux) or `Cmd+Shift+X` (macOS)
   - Search: `GitHub Copilot`
   - Click **Install**

2. Install the **GitHub Copilot Chat** extension (same process)

3. Sign in to GitHub:
   - Click the GitHub icon in the bottom-left status bar
   - Select **Sign in with GitHub**
   - Authorise in your browser

4. Verify Copilot is active:
   - Open any `.java` file
   - Start typing a method — Copilot should suggest completions in grey text
   - Press `Tab` to accept a suggestion

### IntelliJ IDEA

1. Go to **Settings → Plugins**
2. Search for **GitHub Copilot**
3. Install and restart
4. Sign in via **Tools → GitHub Copilot → Login to GitHub**

### Using Copilot Chat

- **VS Code:** Click the chat icon in the sidebar, or press `Ctrl+Shift+I`
- **IntelliJ:** Click the Copilot icon or use the Chat panel

To use an agent, paste its system prompt at the start of a new chat session, then ask your question.

---

## 🔧 IDE-Specific Tips

### VS Code — Recommended Extensions

| Extension | Purpose |
|-----------|---------|
| Extension Pack for Java | Full Java support |
| Spring Boot Extension Pack | Spring Boot dev tools |
| GitHub Copilot | AI code completion |
| GitHub Copilot Chat | AI chat interface |

### IntelliJ IDEA

- Enable annotation processing: **Settings → Build → Compiler → Annotation Processors → Enable**
- This is required for Lombok to work correctly

---

## 🐛 Troubleshooting

### Problem: `mvn` command not found

**Solution:** Maven is not on your PATH. Add it:
```bash
# macOS/Linux (add to ~/.bashrc or ~/.zshrc)
export PATH=$PATH:/path/to/maven/bin

# Windows — add Maven bin folder to System Environment Variables → PATH
```

### Problem: `JAVA_HOME` points to wrong version

**Solution:**
```bash
# macOS (using sdkman)
sdk install java 17.0.11-tem
sdk use java 17.0.11-tem

# Or set manually:
export JAVA_HOME=/Library/Java/JavaVirtualMachines/temurin-17.jdk/Contents/Home
```

### Problem: Port 8080 already in use

**Solution:** Change the port or kill the occupying process:
```bash
# Change port
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8081"

# Or kill the process using port 8080 (macOS/Linux)
lsof -ti:8080 | xargs kill -9
```

### Problem: H2 console shows "Connection refused"

**Solution:** The H2 console is only available while the app is running. Ensure `mvn spring-boot:run` is active in another terminal.

### Problem: Tests fail with `UnsupportedOperationException`

**Solution:** `EligibilityService` has unimplemented TODO methods. Complete [Scenario 01](../scenarios/scenario-01-complete-feature.md) first, or temporarily skip the eligibility tests:
```bash
mvn test -Dtest="!EligibilityServiceTest"
```

### Problem: Copilot suggestions not appearing

**Solution:**
1. Check you are signed in: status bar shows your GitHub username
2. Check the file language mode is Java (bottom-right of VS Code)
3. Disable other AI completion extensions that may conflict
4. Restart VS Code / IntelliJ

---

## 📂 Project Structure Reference

```
GitHubCopilot-Agents/
├── src/
│   ├── main/java/com/loyalty/rewards/
│   │   ├── controller/          # REST controllers
│   │   ├── domain/              # JPA entities
│   │   ├── dto/                 # Data Transfer Objects
│   │   ├── exception/           # Custom exceptions + global handler
│   │   ├── repository/          # Spring Data JPA interfaces
│   │   └── service/             # Business logic
│   ├── main/resources/
│   │   ├── application.properties
│   │   └── data.sql             # Seed data loaded on startup
│   └── test/java/
│       └── com/loyalty/rewards/ # JUnit 5 + Mockito tests
├── agents/                      # AI agent definitions
├── scenarios/                   # Hands-on exercises
├── docs/                        # Project documentation
├── .github/workflows/           # CI/CD pipelines
└── pom.xml                      # Maven build configuration
```
