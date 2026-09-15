# IE3142 Quick Reference Card for Team Members

Print this card or keep it handy while working on the assignment.

---

## Your Assignment Repository

**Repository URL:**
```
https://github.com/IT24100452/WebGoat-IE3142-DevSecOps
```

**Clone command:**
```powershell
git clone https://github.com/IT24100452/WebGoat-IE3142-DevSecOps.git
cd WebGoat-IE3142-DevSecOps
```

---

## Member Assignments at a Glance

### Member 1: Docker Compose
**Branch:** `it24100452-member-1-docker-compose`  
**Key files:** `docker-compose.yml`, `Dockerfile`, `.env.example`  
**Task:** Make WebGoat run safely in Docker  
**Validate:** `docker compose config --quiet`

### Member 2: Threat Model & Cases 1-2
**Branch:** `it24100452-member-2-threat-model`  
**Key files:** `DefaultCredentialsTask.java`, `VerboseErrorTask.java`  
**Task:** Stop rejecting default credentials and verbose errors  
**Validate:** `./mvnw.cmd -Dtest="DefaultCredentialsTaskTest,VerboseErrorTaskTest" test`

### Member 3: Secure Coding 3-4
**Branch:** `it24100452-member-3-secure-cases`  
**Key files:** `FileServer.java`, `JWTToken.java`  
**Task:** Prevent path traversal and enforce JWKS validation  
**Validate:** `./mvnw.cmd -Dtest="FileServerTest,JWTTokenTest" test`

### Member 4: DevSecOps Pipeline
**Branch:** `it24100452-member-4-devsecops`  
**Key files:** `.github/workflows/devsecops.yml`  
**Task:** Create automated security pipeline  
**Validate:** `yq eval . .github/workflows/devsecops.yml`

---

## Essential Git Commands

### First Time Setup
```powershell
git clone https://github.com/IT24100452/WebGoat-IE3142-DevSecOps.git
cd WebGoat-IE3142-DevSecOps
git switch it24100452-member-X-XXXX  # Replace with your branch
```

### Daily Workflow
```powershell
# Start work
git pull --ff-only origin it24100452-member-X-XXXX
git status

# Make changes in your editor...

# Commit changes
git add <files>
git commit -m "type: short description"  # e.g., "fix: prevent path traversal"
git push origin it24100452-member-X-XXXX

# View your work
git log --oneline -5
```

### Types for Commit Messages
```
fix:     Fix a security issue or bug
test:    Add or improve tests
docs:    Update documentation
ci:      Change CI/CD pipeline
build:   Docker/Maven/build config
```

---

## Documentation Reading Order

1. **TEAM_SETUP_COMPLETE.md** ← You are here
2. **docs/member-assignments.md** ← Your member section (IMPORTANT!)
3. **docs/team-branch-and-commit-guide.md** ← Git procedures
4. **Code comments in your files** ← Learn security concepts

---

## Validation Checklist Before Commit

### All Members
- [ ] Changes match your member assignment
- [ ] No `.env` file committed (only `.env.example`)
- [ ] No passwords, tokens, or API keys in code
- [ ] Code compiles: `./mvnw.cmd clean compile`

### Member 1 (Docker)
- [ ] Docker image builds: `docker compose build`
- [ ] Containers start: `docker compose up -d`
- [ ] Health check passes: `docker compose ps` shows "healthy"
- [ ] YAML is valid: `docker compose config --quiet`

### Members 2-3 (Java Code)
- [ ] Member 2: `./mvnw.cmd -Dtest="DefaultCredentialsTaskTest,VerboseErrorTaskTest" test`
- [ ] Member 3: `./mvnw.cmd -Dtest="FileServerTest,JWTTokenTest" test`
- [ ] All tests pass (0 failures)

### Member 4 (Pipeline)
- [ ] Workflow YAML is valid: `yq eval . .github/workflows/devsecops.yml`
- [ ] Push to branch and check GitHub Actions for status

---

## Key Concepts (Read Code Comments!)

### Member 1: Docker
- **127.0.0.1** = localhost only (secure)
- **0.0.0.0** = accessible from any machine (not secure here)
- health check = auto-verify container is running

### Member 2: Threat Model
- **STRIDE** = threat modeling method (Spoofing, Tampering, Repudiation, Information Disclosure, Denial of Service, Elevation)
- **Default credentials** = attack vector (must be rejected)
- **Stack traces** = leak sensitive information

### Member 3: Secure Coding
- **Path traversal** = `../../` in filename to escape directory
- **normalize()** = remove `.., .` from paths
- **JWKS** = JSON Web Key Set (public key collection)
- **Key ID** = must match between token and JWKS

### Member 4: DevSecOps
- **SAST** = Static Application Security Testing (analyze code)
- **Gitleaks** = scan for secrets in git history
- **Trivy** = container image vulnerability scanner
- **Exit code 1** = job fails (good for security gates)

---

## Emergency Quick Fixes

### "I made a mistake in my commit"
```powershell
# If not pushed yet:
git reset --soft HEAD~1        # Undo commit, keep changes
git add <correct-files>
git commit -m "fix: corrected message"

# If already pushed:
git revert HEAD                # Create new commit that undoes previous
git push origin it24100452-member-X-XXXX
```

### "I'm on the wrong branch"
```powershell
git switch it24100452-member-X-XXXX  # Switch to correct branch
```

### "My code won't compile"
```powershell
./mvnw.cmd clean compile
# Check error message for file and line number
```

### "Docker won't start"
```powershell
docker compose down          # Stop all containers
docker compose rm webgoat    # Remove stopped container
docker compose up -d         # Start fresh
docker compose ps            # Check status
docker compose logs webgoat  # See error messages
```

---

## Important Reminders

✅ **DO:**
- Read your member assignment in `docs/member-assignments.md`
- Read code comments (they explain security)
- Test before pushing
- Ask teammates for help
- Commit frequently with clear messages
- Review others' pull requests

❌ **DON'T:**
- Commit real passwords or tokens
- Edit files from other members' assignments
- Skip validation tests
- Push directly to `main` (use pull requests)
- Ignore code comments
- Wait until the last day to start

---

## Where to Get Help

| Problem | Where to Look |
|---------|---|
| "What are my tasks?" | `docs/member-assignments.md` (your member section) |
| "How do I use Git?" | `docs/team-branch-and-commit-guide.md` |
| "How do I understand the code?" | Read code comments in your files |
| "My test is failing" | Read test error message + code comments |
| "Docker won't start" | `docker compose logs webgoat` or `docs/docker-compose.md` |
| "Pipeline is failing" | Check GitHub Actions "Details" tab for error |
| "Security concept unclear" | Search OWASP website + read code comments |

---

## Submit Your Work

1. **Commit and push** to your member branch
2. **Open pull request** on GitHub (from your branch → `main`)
3. **Request review** from 1-2 teammates
4. **Fix review comments** with new commits
5. **Merge** when all checks pass

---

## Your Member Branch Quick Commands

### Member 1
```powershell
git switch it24100452-member-1-docker-compose
```

### Member 2
```powershell
git switch it24100452-member-2-threat-model
```

### Member 3
```powershell
git switch it24100452-member-3-secure-cases
```

### Member 4
```powershell
git switch it24100452-member-4-devsecops
```

---

## Timeline

| When | What |
|------|------|
| Day 1 | Clone repo, switch to branch, read assignment |
| Days 2-7 | Complete tasks, write code, test |
| Days 8-9 | Open PR, review teammates, fix feedback |
| Day 10 | Merge, export report, submit |

---

## Final Checklist Before Submission

- [ ] All team members are on their assigned branch
- [ ] All code changes are complete and tested
- [ ] All commits are pushed to origin
- [ ] All pull requests are merged to `main`
- [ ] No real secrets in the repository
- [ ] Tests pass locally and in GitHub Actions
- [ ] Docker Compose starts successfully
- [ ] Technical report is updated with real evidence
- [ ] README explains the project and team
- [ ] All four members have contributed commits

---

**Good luck! You've got this! 🚀**

For detailed help: Read `docs/member-assignments.md` for your member number.
