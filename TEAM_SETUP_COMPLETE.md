# Team Setup Complete ✅

All four team members' branches and documentation have been prepared for the IE3142 DevSecOps assignment.

---

## Repository Structure

### Primary Repository (Original Work)
- **URL:** https://github.com/IT24100452/WebGoat
- **Contains:** All commits and branches from the entire team
- **Branches:** `main`, `develop`, four member branches

### Assignment Repository (For Distribution)
- **URL:** https://github.com/IT24100452/WebGoat-IE3142-DevSecOps
- **Contains:** Integrated code, student guides, and member branches
- **Default branch:** `main` (contains integrated work)

---

## What Each Member Gets

### Branch Assignments

Each team member has a dedicated branch with their specific work:

| Member | Branch | Files | Focus Area |
|--------|--------|-------|---|
| **1** | `it24100452-member-1-docker-compose` | Dockerfile, docker-compose.yml, .env*, docs | Container & deployment |
| **2** | `it24100452-member-2-threat-model` | DefaultCredentialsTask.java, VerboseErrorTask.java, tests, threat model doc | Threat modeling & cases 1-2 |
| **3** | `it24100452-member-3-secure-cases` | FileServer.java, JWTToken.java, tests, secrets management | Path traversal & JWT validation |
| **4** | `it24100452-member-4-devsecops` | .github/workflows/devsecops.yml, pipeline doc | CI/CD security automation |

---

## Documentation Provided

### 1. **docs/team-branch-and-commit-guide.md**
   - First-time Git setup (clone, switch branches)
   - Daily workflow (pull, edit, commit, push)
   - Conventional commit message format
   - Pull request procedure
   - Testing commands
   - **For:** All team members

### 2. **docs/member-assignments.md** ⭐ **START HERE**
   - Detailed assignment for each member
   - Student-friendly explanations of concepts:
     - Docker & Docker Compose (Member 1)
     - STRIDE threat modeling (Member 2)
     - Path traversal & JWT validation (Member 3)
     - GitHub Actions & CI/CD (Member 4)
   - Before/after code examples
   - Step-by-step tasks
   - Validation checklists
   - Commit procedures with exact commands
   - **For:** Each specific team member

### 3. **Code Comments**
   - Every key file now has clear, student-friendly comments
   - Explains WHAT the code does and WHY it's secure/insecure
   - Examples of vulnerabilities and fixes
   - **Files with comments:**
     - `docker-compose.yml` (security settings explained)
     - `Dockerfile` (build process & user management)
     - `.env.example` (configuration documented)
     - `DefaultCredentialsTask.java` (default credential vulnerability)
     - `VerboseErrorTask.java` (error disclosure vulnerability)
     - `FileServer.java` (path traversal prevention)
     - `JWTToken.java` (JWKS validation)
     - `.github/workflows/devsecops.yml` (each pipeline job explained)

---

## How to Distribute to Your Team

### Step 1: Share the Repository URL
Give each member the assignment repository link:
```
https://github.com/IT24100452/WebGoat-IE3142-DevSecOps
```

### Step 2: Each Member Clones and Checks Out Their Branch

**Member 1:**
```powershell
git clone https://github.com/IT24100452/WebGoat-IE3142-DevSecOps.git
cd WebGoat-IE3142-DevSecOps
git switch it24100452-member-1-docker-compose
```

**Member 2:**
```powershell
git clone https://github.com/IT24100452/WebGoat-IE3142-DevSecOps.git
cd WebGoat-IE3142-DevSecOps
git switch it24100452-member-2-threat-model
```

**Member 3:**
```powershell
git clone https://github.com/IT24100452/WebGoat-IE3142-DevSecOps.git
cd WebGoat-IE3142-DevSecOps
git switch it24100452-member-3-secure-cases
```

**Member 4:**
```powershell
git clone https://github.com/IT24100452/WebGoat-IE3142-DevSecOps.git
cd WebGoat-IE3142-DevSecOps
git switch it24100452-member-4-devsecops
```

### Step 3: Each Member Reads Their Assignment

```powershell
# In your branch, read:
1. docs/member-assignments.md      (Your specific tasks)
2. docs/team-branch-and-commit-guide.md  (Git procedures)
3. Read code comments in your files (Learn security concepts)
```

### Step 4: Complete Tasks and Commit

Follow the exact commit procedures in `docs/member-assignments.md` for your member number.

---

## Getting Help

### If Member 1 (Docker) Needs Help
- Read: `docs/member-assignments.md` → Member 1 section
- Read comments in: `docker-compose.yml`, `Dockerfile`, `.env.example`
- Validate with: `docker compose config --quiet`

### If Member 2 (Threat Model) Needs Help
- Read: `docs/member-assignments.md` → Member 2 section
- Read comments in: `DefaultCredentialsTask.java`, `VerboseErrorTask.java`
- Validate with: `./mvnw.cmd -Dtest="DefaultCredentialsTaskTest,VerboseErrorTaskTest" test`

### If Member 3 (Secure Coding) Needs Help
- Read: `docs/member-assignments.md` → Member 3 section
- Read comments in: `FileServer.java`, `JWTToken.java`
- Validate with: `./mvnw.cmd -Dtest="FileServerTest,JWTTokenTest" test`

### If Member 4 (DevSecOps) Needs Help
- Read: `docs/member-assignments.md` → Member 4 section
- Read comments in: `.github/workflows/devsecops.yml`, `docs/devsecops-pipeline.md`
- Validate with: `yq eval . .github/workflows/devsecops.yml`

---

## Quick Start Checklist

- [ ] All four members have cloned the repository
- [ ] Each member is on their assigned branch
- [ ] Each member has read `docs/member-assignments.md` (their section)
- [ ] Each member has reviewed the code comments in their files
- [ ] Each member understands their tasks and validation procedures
- [ ] Team has decided on a merge strategy (squash? rebase?)
- [ ] Team has assigned reviewers for pull requests

---

## Timeline Suggestion

1. **Days 1-2:** Setup & reading
   - Clone repo
   - Switch to branch
   - Read assignment document
   - Understand security concepts from code comments

2. **Days 3-7:** Implementation
   - Complete tasks
   - Write tests
   - Validate locally

3. **Days 8-9:** Code review
   - Open pull requests
   - Review each other's work
   - Fix comments

4. **Day 10:** Final submission
   - Merge all branches to `main`
   - Export technical report as PDF
   - Submit to course platform

---

## Files Summary

### In the Repository Root (`main` branch)

```
/
├── docs/
│   ├── team-branch-and-commit-guide.md     ← Git procedures
│   ├── member-assignments.md               ← ⭐ START HERE
│   ├── docker-compose.md                   ← Docker runbook
│   ├── architecture.md                     ← System diagram
│   ├── security-threat-model.md            ← STRIDE analysis
│   ├── secure-coding-cases-3-4.md          ← Cases 3-4 doc
│   ├── secret-management.md                ← Secrets guide
│   └── devsecops-pipeline.md               ← Pipeline jobs explained
├── src/
│   ├── main/java/org/owasp/webgoat/
│   │   ├── lessons/securitymisconfiguration/
│   │   │   ├── DefaultCredentialsTask.java         ← Case 1 (commented)
│   │   │   ├── VerboseErrorTask.java              ← Case 2 (commented)
│   │   ├── webwolf/
│   │   │   ├── FileServer.java                    ← Case 3 (commented)
│   │   │   └── jwt/JWTToken.java                  ← Case 4 (commented)
│   └── test/java/...tests...
├── .github/workflows/
│   └── devsecops.yml                       ← CI/CD pipeline (commented)
├── docker-compose.yml                      ← Docker Compose (commented)
├── Dockerfile                              ← Docker image (commented)
├── .env.example                            ← Config template (commented)
├── .gitignore                              ← Git ignore rules
├── .gitleaks.toml                          ← Secret scanning config
├── .semgrep.yml                            ← SAST rules
└── README.md                               ← Repository overview
```

---

## After Submission

Once the course has accepted your submission:

1. **Archive the branches:**
   - Consider if you want to keep the member branches
   - Could delete them after merging, or keep for history

2. **Tag the submission:**
   ```powershell
   git tag -a submission-v1 -m "IE3142 final submission"
   git push origin submission-v1
   ```

3. **Update the README:**
   - Add links to documentation
   - Add team member names
   - Link to the technical report

---

## Success Criteria

Your team will have successfully completed this assignment when:

- ✅ All four member branches are merged to `main`
- ✅ All tests pass locally and in GitHub Actions
- ✅ Docker Compose starts the application and health checks pass
- ✅ Security scans run (Semgrep, Gitleaks, Dependency-Check, Trivy)
- ✅ Each member has made meaningful commits with clear messages
- ✅ Code has been reviewed by at least one other member
- ✅ Technical report is updated with real file paths and evidence
- ✅ README explains the assignment and team structure
- ✅ No real secrets are committed (`.env`, passwords, tokens)
- ✅ All four security fixes are working as intended

---

## Questions?

Refer to:
1. `docs/member-assignments.md` for your specific member tasks
2. `docs/team-branch-and-commit-guide.md` for Git procedures
3. Code comments in your files for security concepts
4. GitHub Actions results for pipeline failures
5. Test output for validation failures

Good luck with your assignment! 🚀

---

**Setup completed:** 2026-09-15  
**Repository:** https://github.com/IT24100452/WebGoat-IE3142-DevSecOps  
**Prepared by:** Copilot
