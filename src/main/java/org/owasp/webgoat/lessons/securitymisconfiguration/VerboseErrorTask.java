/*
 * SPDX-FileCopyrightText: Copyright © 2025 WebGoat authors
 * SPDX-License-Identifier: GPL-2.0-or-later
 */
package org.owasp.webgoat.lessons.securitymisconfiguration;

import static org.owasp.webgoat.container.assignments.AttackResultBuilder.failed;
import static org.owasp.webgoat.container.assignments.AttackResultBuilder.success;

import org.owasp.webgoat.container.assignments.AssignmentEndpoint;
import org.owasp.webgoat.container.assignments.AssignmentHints;
import org.owasp.webgoat.container.assignments.AttackResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Task exposing verbose stack traces leaking sensitive configuration.
 * 
 * SECURITY LESSON:
 * Developers often enable detailed error messages during development for debugging.
 * However, if these detailed stack traces are shown to users in production,
 * attackers learn sensitive information like:
 * - System architecture and technology stack
 * - Environment variables (passwords, API keys, tokens)
 * - Database connection strings
 * - File paths and internal code structure
 * 
 * This task demonstrates this vulnerability. Your job is to modify it to:
 * 1. Return a generic "500 Internal Server Error" instead of details
 * 2. Log errors securely on the server (not shown to users)
 */
@RestController
@AssignmentHints({
    "securitymisconfiguration.task2.hint1",
    "securitymisconfiguration.task2.hint2"
})
public class VerboseErrorTask implements AssignmentEndpoint {

  // A staging token that should NEVER be exposed in real applications
  static final String LEAKED_TOKEN = "STAGING-TOKEN-42";

  @GetMapping(value = "/SecurityMisconfiguration/task2/trigger", produces = MediaType.TEXT_PLAIN_VALUE)
  public ResponseEntity<String> triggerError() {
    // SECURITY ISSUE: This returns detailed error information including sensitive data!
    // Real-world attacks use this information to:
    // 1. Map the application architecture
    // 2. Find vulnerabilities in known versions
    // 3. Extract credentials and configuration
    String stackTrace =
        "2025-03-21 09:42:11,012 ERROR [staging] com.webgoat.DebugController - Null pointer while rendering template\n"
            + "java.lang.NullPointerException: Cannot invoke \"Object.toString()\" because \"ctx" + "\" is null\n"
            + "\tat com.webgoat.DebugController.render(DebugController.java:94)\n"
            + "\tat org.springframework.mvc.DispatcherServlet.doDispatch(DispatcherServlet.java:1101)\n"
            + "\tat ...\n"
            + "\nENVIRONMENT=staging\n"
            + "DEBUG_MODE=true\n"
            + "DB_USER=staging_user\n"
            + "DB_PASSWORD=staging_password123\n"
            + "SYSTEM_API_TOKEN="
            + LEAKED_TOKEN
            + "\n";
    return ResponseEntity.ok(stackTrace);
  }

  @GetMapping(value = "/SecurityMisconfiguration/task2/config", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> fetchConfig(@RequestParam(value = "token", required = false) String token) {
    // SECURITY ISSUE: This exposes sensitive configuration if the token is known!
    // Attackers can extract the token from error messages and use it here
    if (LEAKED_TOKEN.equals(token)) {
      String json =
          "{\n"
              + "  \"feature\": \"debug\",\n"
              + "  \"logging\": \"trace\",\n"
              + "  \"notes\": \"Never expose this in production!\"\n"
              + "}";
      return ResponseEntity.ok(json);
    }
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("ACCESS DENIED");
  }

  @PostMapping(
      value = "/SecurityMisconfiguration/task2",
      consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public AttackResult submitToken(@RequestParam("token") String token) {
    if (LEAKED_TOKEN.equals(token)) {
      return success(this)
          .feedback("securitymisconfiguration.task2.success")
          .output("Debug mode disabled. Stack traces are now safe for users.")
          .build();
    }
    if (token == null || token.isBlank()) {
      return failed(this)
          .feedback("securitymisconfiguration.task2.failure.blank")
          .build();
    }
    return failed(this)
        .feedback("securitymisconfiguration.task2.failure.invalid")
        .build();
  }
}
