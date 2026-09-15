# IE3142 Team Branch and Commit Guide

This guide explains how the four team members should work in the new
`WebGoat-IE3142-DevSecOps` repository. It is written for students who may be
using Git and GitHub for the first time.

## 1. Repository and branch structure

The repository should contain:

- `main` - the integrated project and submission branch.
- `it24100452-member-1-docker-compose` - Member 1 work.
- `it24100452-member-2-threat-model` - Member 2 work.
- `it24100452-member-3-secure-cases` - Member 3 work.
- `it24100452-member-4-devsecops` - Member 4 work.

Each person must work only on their assigned branch. Do not commit directly
to `main`. Changes should be reviewed before they are merged.

## 2. First-time setup

Install Git, clone the repository, and enter the project directory:

```powershell
git clone https://github.com/IT24100452/WebGoat-IE3142-DevSecOps.git
cd WebGoat-IE3142-DevSecOps
git fetch --all --prune
```

Select your assigned branch:

```powershell
git switch it24100452-member-1-docker-compose
```

Replace the branch name with the branch assigned to you. Before starting
work, confirm that you are on the correct branch:

```powershell
git branch --show-current
git status
```

## 3. Daily work procedure

Before editing:

```powershell
git switch <your-branch>
git pull --ff-only origin <your-branch>
```

After editing, inspect and test your changes:

```powershell
git status
git diff --check
git diff
```

For Java changes, run the smallest relevant Maven test first. Before opening a
pull request, run the complete validation command:

```powershell
.\mvnw.cmd --batch-mode --no-transfer-progress test
```

Do not commit `.env` or real passwords, API keys, tokens, or private data.
Use `.env.example` with placeholder values.

## 4. Commit procedure

A commit should contain one logical change. Use a short conventional commit
message:

```text
<type>: <short description>
```

Recommended types:

- `fix` - correct a security or functional defect.
- `test` - add or improve automated tests.
- `docs` - update documentation.
- `ci` - change GitHub Actions or security automation.
- `build` - change Docker, Maven, or build configuration.

Example:

```powershell
git add Dockerfile docker-compose.yml docs/docker-compose.md
git commit -m "build: harden Docker Compose setup"
git push origin it24100452-member-1-docker-compose
```

Every commit must explain what changed, not who made it. The commit body is
optional, but useful for security work:

```powershell
git commit -m "fix: reject unsafe upload paths" `
  -m "Normalize the submitted path and reject destinations outside the user directory."
```

## 5. Member responsibilities and file lists

### Member 1 - Docker and local deployment

**Goal:** Make the application easy and safer to run locally with Docker.

**Files to own:**

- `.dockerignore`
- `.env`
- `.env.example`
- `Dockerfile`
- `docker-compose.yml`
- `docs/architecture.md`
- `docs/docker-compose.md`

**Student tasks:**

1. Build the application image without copying unnecessary files.
2. Keep secrets out of source control and document placeholder variables.
3. Bind local services safely and document the ports.
4. Confirm that the containers start, become healthy, and stop cleanly.
5. Update the architecture diagram and Docker runbook when configuration
   changes.

**Validation:**

```powershell
docker compose config --quiet
docker compose build
docker compose up -d
docker compose ps
docker compose down
```

### Member 2 - Threat model and secure coding cases 1 and 2

**Goal:** Document threats and remove insecure default credentials and verbose
error disclosure.

**Files to own:**

- `docs/security-threat-model.md`
- `src/main/java/org/owasp/webgoat/lessons/securitymisconfiguration/DefaultCredentialsTask.java`
- `src/main/java/org/owasp/webgoat/lessons/securitymisconfiguration/VerboseErrorTask.java`
- `src/test/java/org/owasp/webgoat/lessons/securitymisconfiguration/DefaultCredentialsTaskTest.java`
- `src/test/java/org/owasp/webgoat/lessons/securitymisconfiguration/VerboseErrorTaskTest.java`

**Student tasks:**

1. Describe assets, threats, trust boundaries, risks, and controls using
   STRIDE.
2. Ensure built-in `admin/admin` credentials are not accepted as a successful
   authentication result.
3. Ensure error responses do not expose stack traces, passwords, environment
   variables, or tokens.
4. Add tests for blank, invalid, and attack-style inputs.
5. Run the focused tests and record the result in the report.

**Validation:**

```powershell
.\mvnw.cmd --batch-mode --no-transfer-progress `
  -Dtest="DefaultCredentialsTaskTest,VerboseErrorTaskTest" test
```

### Member 3 - Secure coding cases 3 and 4 and secrets

**Goal:** Prevent file-upload path traversal and strengthen JWT/JWKS
validation.

**Files to own:**

- `src/main/java/org/owasp/webgoat/webwolf/FileServer.java`
- `src/main/java/org/owasp/webgoat/webwolf/jwt/JWTToken.java`
- `src/test/java/org/owasp/webgoat/webwolf/FileServerTest.java`
- `src/test/java/org/owasp/webgoat/webwolf/jwt/JWTTokenTest.java`
- `.gitignore`
- `.gitleaks.toml`
- `docs/secure-coding-cases-3-4.md`
- `docs/secret-management.md`

**Student tasks:**

1. Normalize uploaded paths and reject files that leave the user directory.
2. Require a JWT key ID when validating against JWKS.
3. Accept only the JWKS key that matches the token key ID.
4. Add regression tests for traversal, missing key IDs, and mismatched keys.
5. Keep real secrets out of commits and document safe local configuration.

**Validation:**

```powershell
.\mvnw.cmd --batch-mode --no-transfer-progress `
  -Dtest="FileServerTest,JWTTokenTest" test
```

### Member 4 - DevSecOps pipeline

**Goal:** Automate build, test, static analysis, dependency checks, secret
scanning, and container scanning.

**Files to own:**

- `.github/workflows/devsecops.yml`
- `Dockerfile` (only when the pipeline requires a related image change)
- `docs/devsecops-pipeline.md`

**Student tasks:**

1. Run Maven tests in GitHub Actions.
2. Run Semgrep for source-code security checks.
3. Run OWASP Dependency-Check with the agreed CVSS threshold.
4. Run Gitleaks without hiding newly introduced secrets.
5. Build the image and run Trivy, blocking fixed critical vulnerabilities.
6. Keep permissions minimal and upload reports as workflow artifacts.

**Validation:**

```powershell
.\mvnw.cmd --batch-mode --no-transfer-progress validate
```

Then inspect every job in the GitHub Actions run. A successful final policy
job is required before merging.

## 6. Pull request procedure

When the work is ready:

1. Push the branch.
2. Open a pull request from the member branch to `main`.
3. Explain the problem, the files changed, and the tests run.
4. Ask one colleague to review the pull request.
5. Fix review comments in new commits.
6. Merge only after required checks pass.

Pull request description template:

```text
## What changed

## Files changed

## Security reason

## Tests and validation

## Reviewer
```

## 7. Final integration checklist

Before submission, the team should confirm:

- All four member branches are present on GitHub.
- No real secret is tracked.
- `main` contains the reviewed changes.
- Focused tests and the full Maven test suite pass.
- Docker Compose configuration is valid.
- Every required GitHub Actions security job passes.
- The technical report names the real files, commits, test results, and
  evidence links.
- The report is exported to PDF if the course requires PDF submission.
