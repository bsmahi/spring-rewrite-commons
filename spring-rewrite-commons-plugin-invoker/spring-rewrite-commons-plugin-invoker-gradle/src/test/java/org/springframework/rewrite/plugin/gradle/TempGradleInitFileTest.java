/*
 * Copyright 2021 - 2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.rewrite.plugin.gradle;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Fabian Krüger
 */
class TempGradleInitFileTest {

	@Test
	@DisplayName("should create and delete gradle init file")
	void shouldCreateAndDeleteGradleInitFile(@TempDir Path temoDir) {
		List<String> depemdemcies = List.of("dep1", "dep2");
		String pluginVersion = "x.y.z";

		assertThat(temoDir.resolve("recipes.init.gradle")).doesNotExist();

		try (TempGradleInitFile initFile = new TempGradleInitFile(temoDir, depemdemcies, pluginVersion)) {

			assertThat(temoDir.resolve("recipes.init.gradle")).exists();

			assertThat(initFile.getContent()).isEqualTo("""
					initscript {
					    repositories {
					        maven { url "https://plugins.gradle.org/m2" }
					    }
					    dependencies {
					        classpath("org.openrewrite:plugin:x.y.z")
					    }
					}

					rootProject {
					    plugins.apply(org.openrewrite.gradle.RewritePlugin)
					    dependencies {
					        rewrite("dep1")
					        rewrite("dep2")
					    }

					    afterEvaluate {
					        if (repositories.isEmpty()) {
					            repositories {
					                mavenCentral()
					            }
					        }
					    }
					}
					""");
		}

		assertThat(temoDir.resolve("recipes.init.gradle")).doesNotExist();
	}

	@Test
	@DisplayName("should create and delete gradle init file on exception")
	void shouldCreateAndDeleteGradleInitFileOnException(@TempDir Path temoDir) {
		List<String> depemdemcies = List.of("dep1", "dep2");
		String pluginVersion = "x.y.z";

		assertThat(temoDir.resolve("recipes.init.gradle")).doesNotExist();
		try {

			try (TempGradleInitFile initFile = new TempGradleInitFile(temoDir, depemdemcies, pluginVersion)) {

				assertThat(temoDir.resolve("recipes.init.gradle")).exists();

				assertThat(initFile.getContent()).isEqualTo("""
						initscript {
						    repositories {
						        maven { url "https://plugins.gradle.org/m2" }
						    }
						    dependencies {
						        classpath("org.openrewrite:plugin:x.y.z")
						    }
						}

						rootProject {
						    plugins.apply(org.openrewrite.gradle.RewritePlugin)
						    dependencies {
						        rewrite("dep1")
						        rewrite("dep2")
						    }

						    afterEvaluate {
						        if (repositories.isEmpty()) {
						            repositories {
						                mavenCentral()
						            }
						        }
						    }
						}
						""");
				throw new RuntimeException();
			}
		}
		catch (Exception e) {
			assertThat(temoDir.resolve("recipes.init.gradle")).doesNotExist();
		}
	}

}