/*
 * SPDX-FileCopyrightText: Copyright © 2025 WebGoat authors
 * SPDX-License-Identifier: GPL-2.0-or-later
 */
package org.owasp.webgoat.lessons.securitymisconfiguration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.owasp.webgoat.container.assignments.AttackResult;
import org.springframework.http.ResponseEntity;

class VerboseErrorTaskTest {

  private VerboseErrorTask task;

  @BeforeEach
  void setUp() {
    task = new VerboseErrorTask();
  }

  @Test
  void triggerShouldNotExposeDiagnosticsOrSecrets() {
    ResponseEntity<String> response = task.triggerError();

    assertThat(response.getStatusCode()).isEqualTo(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
    assertThat(response.getBody()).doesNotContain("SYSTEM_API_TOKEN", "DB_PASSWORD", "java.lang");
  }

  @Test
  void shouldFailWhenTokenMissing() {
    AttackResult result = task.submitToken("");

    assertThat(result.assignmentSolved()).isFalse();
    assertThat(result.getFeedback()).isEqualTo("securitymisconfiguration.task2.failure.blank");
  }

  @Test
  void shouldFailWithIncorrectToken() {
    AttackResult result = task.submitToken("WRONG");

    assertThat(result.assignmentSolved()).isFalse();
    assertThat(result.getFeedback()).isEqualTo("securitymisconfiguration.task2.failure.invalid");
  }


  @Test
  void configEndpointShouldRequireToken() {
    var response = task.fetchConfig(null);
    assertThat(response.getStatusCode()).isEqualTo(org.springframework.http.HttpStatus.FORBIDDEN);
  }

  @Test
  void configEndpointShouldRemainUnavailableEvenWithFormerToken() {
    var response = task.fetchConfig("STAGING-TOKEN-42");
    assertThat(response.getStatusCode()).isEqualTo(org.springframework.http.HttpStatus.FORBIDDEN);
  }

  @Test
  void formerTokenShouldNotSolveAssignment() {
    AttackResult result = task.submitToken("STAGING-TOKEN-42");

    assertThat(result.assignmentSolved()).isFalse();
    assertThat(result.getFeedback()).isEqualTo("securitymisconfiguration.task2.failure.invalid");
  }
}
