# IE3142 Team Member Assignments

This document outlines each team member's specific responsibilities, files to work on, and step-by-step commit procedures for the IE3142 DevSecOps assignment.

---

## Overview

The team has been split into **4 members**, each with a dedicated branch in the repository:

| Member | Branch Name | Responsibility | Key Files |
|--------|-------------|---|---|
| **Member 1** | `it24100452-member-1-docker-compose` | Docker and local deployment | Dockerfile, docker-compose.yml, .env, .env.example, docs |
| **Member 2** | `it24100452-member-2-threat-model` | Threat modeling & cases 1-2 | DefaultCredentialsTask.java, VerboseErrorTask.java, tests, threat model doc |
| **Member 3** | `it24100452-member-3-secure-cases` | Secure coding cases 3-4 | FileServer.java, JWTToken.java, tests, secret management |
| **Member 4** | `it24100452-member-4-devsecops` | CI/CD automation | .github/workflows/devsecops.yml, Dockerfile (pipeline changes), docs |

---

## Member 1: Docker Compose and Local Deployment

### What is your job?

Make the WebGoat application **easy, reproducible, and secure** to run on a local machine using Docker and Docker Compose.

### Student Explanation

Think of Docker as a **shipping container for software**. Instead of installing WebGoat and all its dependencies on your computer (which could break other things), Docker packages everything into a container. Docker Compose is a configuration file that:
- Starts the WebGoat container
- Maps ports safely (only localhost access)
- Sets up volumes for data persistence
- Adds health checks

Your job is to configure this safely.

### Files You Own

```
.dockerignore              # Ignore unnecessary files during Docker build
.env.example               # Example environment variables (with placeholders, NO secrets!)
.env                       # Actual environment variables (should NOT be committed)
Dockerfile                 # Instructions for building the Docker image
docker-compose.yml         # Configuration for running the container
docs/docker-compose.md     # Instructions for using Docker Compose
docs/architecture.md       # Architecture diagram and explanation
```

### Your Tasks

1. **Configure .dockerignore** to exclude large/unnecessary files:
   - Do NOT send compiled code twice
   - Do NOT include build artifacts
   - Do NOT include git metadata

2. **Create Dockerfile** that:
   - Starts from an official Java image
   - Installs only necessary tools
   - Runs the app as a non-root user (security)
   - Includes a health check

3. **Create docker-compose.yml** that:
   - Binds ports **ONLY to 127.0.0.1** (localhost) — not to 0.0.0.0
   - Sets environment variables from `.env`
   - Mounts a persistent volume for data
   - Includes security settings (read-only root, dropped capabilities)
   - Has health checks

4. **Create .env.example** with placeholder values:
   - Show what variables are needed
   - Use dummy values, NEVER real passwords or tokens
   - Add comments explaining each variable

5. **Document in docs/**:
   - How to start/stop containers
   - What ports are used
   - How to check application health
   - Troubleshooting steps

### Validation Checklist

```bash
# Build the image
docker compose build

# Start the container
docker compose up -d

# Check it's healthy
docker compose ps

# Test the endpoint
curl http://127.0.0.1:8080/WebGoat/actuator/health

# Stop the container
docker compose down

# Validate YAML syntax
docker compose config --quiet
```

### Commit Procedure

Make a commit when you finish each task:

```powershell
# Add your changes
git add docker-compose.yml Dockerfile .dockerignore .env.example

# Commit with a clear message
git commit -m "build: create Docker Compose setup for local development"

# Push to your branch
git push origin it24100452-member-1-docker-compose
```

### Code Comments

Your code has detailed comments explaining:
- Why we bind to 127.0.0.1 only
- What each Docker security setting does
- How health checks work
- Environment variable purposes

Read the comments carefully to understand each line!

---

## Member 2: Threat Model and Secure Coding Cases 1-2

### What is your job?

1. Document **security threats** using STRIDE methodology
2. Fix two **insecure coding patterns** in WebGoat:
   - Default credentials (admin/admin should be rejected)
   - Verbose error disclosure (stack traces leak secrets)

### Student Explanation

**STRIDE** is a security threat modeling method:
- **S**poofing: Fake identity
- **T**ampering: Unauthorized changes
- **R**epudiation: Denying actions
- **I**nformation Disclosure: Secrets leaked
- **D**enial of Service: System unavailable
- **E**levation of Privilege: Getting higher access

You'll use this to describe what could go wrong in WebGoat.

### Files You Own

```
docs/security-threat-model.md      # STRIDE threat analysis
src/main/java/org/owasp/webgoat/lessons/securitymisconfiguration/DefaultCredentialsTask.java
src/main/java/org/owasp/webgoat/lessons/securitymisconfiguration/VerboseErrorTask.java
src/test/java/...DefaultCredentialsTaskTest.java
src/test/java/...VerboseErrorTaskTest.java
```

### Your Tasks

#### Task 1: Document Threats (STRIDE)

Create `docs/security-threat-model.md` with:

```markdown
## STRIDE Threat Model for WebGoat

### Spoofing
- Threat: Default credentials (admin/admin) allow anyone to log in
- Impact: Unauthorized access to all lessons
- Control: Use strong, unique credentials; force password change on first login

### Information Disclosure
- Threat: Stack traces show environment variables, database passwords, API tokens
- Impact: Attacker learns system architecture and credentials
- Control: Return generic error messages; log details server-side only

### [Continue for other threats...]
```

#### Task 2: Fix Default Credentials (Case 1)

**Current Problem:** The code accepts `admin/admin` and returns success.

**What the code looks like now:**
```java
if (DEFAULT_USERNAME.equals(username.trim()) && DEFAULT_PASSWORD.equals(password)) {
    return success(this)...  // ❌ WRONG: Accepts default credentials!
}
```

**What you need to do:**
1. Change it to **reject** admin/admin
2. Return `failed()` instead of `success()`
3. Add test to verify rejection

**After your fix:**
```java
if (DEFAULT_USERNAME.equals(username.trim()) && DEFAULT_PASSWORD.equals(password)) {
    return failed(this)...  // ✅ CORRECT: Rejects default credentials
}
```

#### Task 3: Fix Verbose Errors (Case 2)

**Current Problem:** The code returns stack traces with environment variables and tokens.

**What the code looks like now:**
```java
String stackTrace = "...ENVIRONMENT=staging...DB_PASSWORD=staging_password123...SYSTEM_API_TOKEN=TOKEN-42...";
return ResponseEntity.ok(stackTrace);  // ❌ WRONG: Exposes secrets!
```

**What you need to do:**
1. Return a **generic error message** instead
2. Log details to the server (not shown to users)
3. Add test to verify generic response

**After your fix:**
```java
String errorMessage = "Internal server error. Please try again later.";
return ResponseEntity.status(500).body(errorMessage);  // ✅ CORRECT: Generic message
logger.error("...", stackTrace);  // ✅ Log safely server-side
```

#### Task 4: Add Regression Tests

In both test classes, add tests that verify:
- Blank inputs are rejected
- Default credentials are rejected
- Invalid inputs are rejected
- Generic error responses don't leak secrets

### Validation Checklist

```bash
# Run only your tests
./mvnw.cmd -Dtest="DefaultCredentialsTaskTest,VerboseErrorTaskTest" test

# All tests should pass
# Total: 6+ tests, 0 failures
```

### Commit Procedure

Make a commit for each logical change:

```powershell
# Commit 1: Add threat model
git add docs/security-threat-model.md
git commit -m "docs: add STRIDE threat model analysis"
git push origin it24100452-member-2-threat-model

# Commit 2: Fix case 1
git add src/main/java/org/owasp/webgoat/lessons/securitymisconfiguration/DefaultCredentialsTask.java
git add src/test/java/org/owasp/webgoat/lessons/securitymisconfiguration/DefaultCredentialsTaskTest.java
git commit -m "fix: reject default admin/admin credentials"
git push origin it24100452-member-2-threat-model

# Commit 3: Fix case 2
git add src/main/java/org/owasp/webgoat/lessons/securitymisconfiguration/VerboseErrorTask.java
git add src/test/java/org/owasp/webgoat/lessons/securitymisconfiguration/VerboseErrorTaskTest.java
git commit -m "fix: return generic error instead of leaking stack traces"
git push origin it24100452-member-2-threat-model

# Commit 4: Add comprehensive tests
git add src/test/java/...DefaultCredentialsTaskTest.java
git add src/test/java/...VerboseErrorTaskTest.java
git commit -m "test: add regression coverage for security cases 1-2"
git push origin it24100452-member-2-threat-model
```

### Code Comments

Your code has detailed comments explaining:
- What the STRIDE threat categories are
- Why default credentials are dangerous
- How stack traces leak information
- What the security controls do

Read the comments — they teach you security concepts!

---

## Member 3: Secure Coding Cases 3-4 and Secrets Management

### What is your job?

1. Prevent **file upload path traversal** attacks
2. Enforce **JWT signature validation** with JWKS
3. Protect **secrets** from being accidentally committed

### Student Explanation

**Path Traversal Attack:** Imagine an upload endpoint. If you upload a file named `../../etc/passwd`, the application might save it outside the intended directory and overwrite system files!

**JWT JWKS Validation:** A JWT is a signed token. JWKS (JSON Web Key Set) is a collection of public keys. You must:
- Check that the JWT has a key ID
- Find the matching key in JWKS
- Reject if no match is found

### Files You Own

```
src/main/java/org/owasp/webgoat/webwolf/FileServer.java         # Upload handling
src/main/java/org/owasp/webgoat/webwolf/jwt/JWTToken.java       # JWT validation
src/test/java/org/owasp/webgoat/webwolf/FileServerTest.java
src/test/java/org/owasp/webgoat/webwolf/jwt/JWTTokenTest.java
.gitignore                                                        # Prevent secrets from being committed
.gitleaks.toml                                                    # Gitleaks configuration
docs/secure-coding-cases-3-4.md                                  # Documentation
docs/secret-management.md                                        # Secret handling guide
```

### Your Tasks

#### Task 1: Fix Path Traversal (Case 3)

**The Vulnerability:**
```java
var destinationFile = destinationDir.toPath().resolve(originalFilename).normalize();
// ❌ WRONG: No check that the resolved path is still inside destinationDir!
Files.copy(is, destinationFile);
```

**The Fix:**
```java
var destinationFile = destinationDir.toPath().resolve(originalFilename).normalize();
// ✅ CORRECT: Verify the file stays in the user's directory
if (!destinationFile.getParent().equals(destinationDir.toPath().toAbsolutePath()) &&
    !destinationFile.getParent().equals(destinationDir.toPath())) {
    throw new SecurityException("Path traversal detected!");
}
Files.copy(is, destinationFile);
```

**What does this do?**
- `resolve()` combines path + filename
- `normalize()` removes `..` and `.` components
- Check that the parent directory is still within the user's upload directory

#### Task 2: Fix JWT JWKS Validation (Case 4)

**The Vulnerability:**
```java
// ❌ WRONG: No check for key ID, will accept any key
for (JsonWebKey jwk : jsonWebKeySet.getJsonWebKeys()) {
    return jwk.getKey();  // Uses FIRST key, ignoring the token's key ID
}
```

**The Fix:**
```java
String keyId = jws.getKeyIdHeaderValue();
if (!hasText(keyId)) {
    throw new UnresolvableKeyException("JWT does not specify a key id");  // ✅ Require key ID
}
for (JsonWebKey jwk : jsonWebKeySet.getJsonWebKeys()) {
    if (keyId.equals(jwk.getKeyId())) {  // ✅ Only accept matching keys
        return jwk.getKey();
    }
}
throw new UnresolvableKeyException("No matching key in JWKS");  // ✅ Reject mismatch
```

**What does this do?**
- Requires a key ID in the JWT header
- Finds the key with matching ID in JWKS
- Rejects tokens with missing or mismatched key IDs

#### Task 3: Protect Secrets

1. **Create/update .gitignore:**
   ```
   .env
   *.env.local
   config.properties
   secrets/
   ```

2. **Create .gitleaks.toml** to scan for secrets:
   ```toml
   title = "IE3142 Gitleaks configuration"
   ```

3. **Create docs/secret-management.md:**
   - Explain what `.env.example` is for
   - Show how to create `.env` locally
   - List secret variable names without values
   - Explain why secrets must never be committed

#### Task 4: Add Tests

Add tests that verify:
- Path traversal attempts are blocked
- JWT with missing key ID is rejected
- JWT with wrong key ID is rejected
- JWT with matching key ID is accepted

### Validation Checklist

```bash
# Run your tests
./mvnw.cmd -Dtest="FileServerTest,JWTTokenTest" test

# Verify .gitleaks.toml syntax
cat .gitleaks.toml

# Check .gitignore is correct
git check-ignore .env
git check-ignore secrets/
```

### Commit Procedure

```powershell
# Commit 1: Fix path traversal
git add src/main/java/org/owasp/webgoat/webwolf/FileServer.java
git add src/test/java/org/owasp/webgoat/webwolf/FileServerTest.java
git commit -m "fix: prevent file upload path traversal attacks"
git push origin it24100452-member-3-secure-cases

# Commit 2: Fix JWT validation
git add src/main/java/org/owasp/webgoat/webwolf/jwt/JWTToken.java
git add src/test/java/org/owasp/webgoat/webwolf/jwt/JWTTokenTest.java
git commit -m "fix: enforce JWKS key-id validation in JWT verification"
git push origin it24100452-member-3-secure-cases

# Commit 3: Secrets management
git add .gitignore .gitleaks.toml docs/secret-management.md docs/secure-coding-cases-3-4.md
git commit -m "security: document secret handling and Gitleaks configuration"
git push origin it24100452-member-3-secure-cases
```

### Code Comments

Your code has detailed comments explaining:
- How path traversal attacks work
- What normalize() does
- Why key ID validation matters
- How JWKS resolution works

---

## Member 4: DevSecOps Pipeline and Automation

### What is your job?

Create a **GitHub Actions workflow** that automatically:
1. Builds and tests the application
2. Scans code for security issues (SAST)
3. Scans for accidentally committed secrets
4. Checks dependencies for known vulnerabilities
5. Builds and scans the Docker image
6. Requires all checks to pass before merge

### Student Explanation

Think of this as an **automated security guard** that runs every time you push code. It:
- Compiles your code
- Looks for bugs with Semgrep
- Searches for passwords/keys with Gitleaks
- Checks if libraries have security patches with Dependency-Check
- Scans the Docker image for OS vulnerabilities with Trivy
- Blocks merging if anything fails

### Files You Own

```
.github/workflows/devsecops.yml    # GitHub Actions workflow
Dockerfile                          # Any pipeline-related updates
docs/devsecops-pipeline.md          # Documentation of what each job does
```

### Your Tasks

#### Task 1: GitHub Actions Workflow Structure

Create `.github/workflows/devsecops.yml` with these jobs:

1. **build-test** (Member 1 prerequisite)
   - Check out code
   - Set up Java 25
   - Run: `./mvnw test`
   - Upload test reports

2. **sast** (Static Application Security Testing)
   - Run Semgrep with `.semgrep.yml` config
   - Upload JSON report

3. **secrets** (Secret scanning)
   - Run Gitleaks with `.gitleaks.toml` config
   - Fail if ANY secret is found

4. **dependency** (Dependency scanning)
   - Run OWASP Dependency-Check
   - Fail if vulnerability CVSS >= 7

5. **image** (Container scanning)
   - Build Docker image
   - Run Trivy on image
   - Fail if CRITICAL vulnerabilities with fixes exist
   - Depends on build-test job

6. **policy** (Final gate)
   - Require all jobs to succeed
   - Blocks merge if any job failed

#### Task 2: Security Policies

In the workflow, set policies like:

```yaml
# Permissions: minimal (least privilege)
permissions:
  contents: read
  security-events: write
  actions: read

# Fail build if test fails
exit-code: "1"

# Fail on vulnerable dependencies
-DfailBuildOnCVSS=7

# Block image with unfixed CRITICAL vulnerabilities
severity: CRITICAL
```

#### Task 3: Document the Pipeline

Create `docs/devsecops-pipeline.md` explaining:
- What each job does
- Why each tool is important
- When the workflow runs (triggers)
- How to read the results
- What to do if a job fails

### Validation Checklist

```bash
# Validate YAML syntax
yq eval . .github/workflows/devsecops.yml

# Check for syntax errors
# (GitHub will show errors in the Actions tab)

# Push a change and watch the workflow run
git push origin it24100452-member-4-devsecops

# Check the Actions tab on GitHub
# All jobs should pass (or fail for expected reasons)
```

### Commit Procedure

```powershell
# Commit 1: Create workflow
git add .github/workflows/devsecops.yml
git commit -m "ci: add DevSecOps security pipeline"
git push origin it24100452-member-4-devsecops

# Commit 2: Document pipeline
git add docs/devsecops-pipeline.md
git commit -m "docs: document GitHub Actions security pipeline"
git push origin it24100452-member-4-devsecops

# Commit 3: Any Dockerfile updates for pipeline
git add Dockerfile
git commit -m "build: update Dockerfile for pipeline compatibility"
git push origin it24100452-member-4-devsecops
```

### Code Comments

Your code has detailed comments explaining:
- What each job does
- Why permissions are minimal
- What exit codes mean
- How security thresholds work

---

## How to Merge Your Work

When your branch is ready:

1. **Push your branch:**
   ```powershell
   git push origin <your-branch>
   ```

2. **Open a pull request on GitHub:**
   - From your branch → to `main`
   - Explain what you changed
   - Ask 1-2 colleagues to review

3. **Fix any review comments:**
   ```powershell
   # Make changes
   git add <files>
   git commit -m "review: fix issue mentioned in PR feedback"
   git push origin <your-branch>
   ```

4. **Merge when all checks pass:**
   - GitHub will show green checkmarks
   - Squash or rebase as you prefer
   - Delete the feature branch after merging

---

## Quick Reference: Git Commands

```powershell
# Switch to your branch
git switch it24100452-member-X-XXXX

# Pull latest changes
git pull --ff-only origin it24100452-member-X-XXXX

# Check status
git status

# Stage changes
git add <files>

# Commit with message
git commit -m "type: short description" -m "Optional detailed explanation"

# Push to GitHub
git push origin it24100452-member-X-XXXX

# View your commits
git log --oneline -10
```

---

## Support and Questions

- **For code questions:** Look at the comments in each file
- **For Git questions:** Ask your team lead or check `docs/team-branch-and-commit-guide.md`
- **For security concepts:** Search OWASP WebSite or consult the comments in your code
- **For workflow failures:** Check the "Actions" tab on GitHub for detailed error messages

---

Good luck, and happy coding! 🚀
