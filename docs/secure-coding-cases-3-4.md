# Secure coding cases 3-4

These cases are the Member 3 remediations from commit `0e7bc638` (the
baseline is its parent, `21727452`). They are intentionally limited to the
two Java flows below and do not duplicate the UserService or Salaries cases.

## Case 3: WebWolf upload path traversal

- **CWE / OWASP:** CWE-22, Path Traversal
- **Source:** `src/main/java/org/owasp/webgoat/webwolf/FileServer.java`,
  `importFile`
- **Controlled input:** an authenticated upload named `../outside.txt`
- **Root cause:** the original filename was resolved below the per-user
  directory without verifying that normalization stayed in that directory.
- **Remediation:** normalize the resolved path and reject any upload whose
  parent is not the authenticated user's upload directory.
- **Regression:** `FileServerTest.shouldRejectUploadWithPathTraversal`
- **Expected fixed result:** the request redirects with `Nothing to upload`
  and no file is written outside the user's directory.

## Case 4: WebWolf JWT signature validation

- **CWE / OWASP:** CWE-347, Improper Verification of Cryptographic Signature
- **Source:** `src/main/java/org/owasp/webgoat/webwolf/jwt/JWTToken.java`,
  `decode` and `validateSignature`
- **Controlled input:** a structurally valid JWT with no configured key, or
  a JWT signed by a key absent from the supplied JWKS.
- **Root cause:** token structure was accepted independently of cryptographic
  verification.
- **Remediation:** require a non-empty shared secret or JWKS, validate the
  signature through jose4j, and return `false` when verification fails.
- **Regression:** `JWTTokenTest.decodeInvalidSignedToken`,
  `decodeTokenFailsWithMismatchingJwks`, and
  `decodeTokenFailsWhenJwksKeyIdIsMissing`
- **Expected fixed result:** malformed, unsigned, and mismatched-key tokens
  have `signatureValid == false`; valid shared-secret and JWKS tokens remain
  accepted.

Run the focused checks with:

```text
./mvnw --batch-mode --no-transfer-progress -Dtest=FileServerTest,JWTTokenTest test
```

The source and test paths above are the evidence references. Generated
responses and screenshots belong under ignored `evidence/private/`; do not
commit credentials, tokens, or other secret values.
