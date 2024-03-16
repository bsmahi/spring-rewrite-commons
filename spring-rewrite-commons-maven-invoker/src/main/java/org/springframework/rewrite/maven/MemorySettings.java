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
package org.springframework.rewrite.maven;

/**
 * @author Fabian Krüger
 */
public class MemorySettings {

	private final String min;

	private final String max;

	public MemorySettings(String min, String max) {
		this.min = min;
		this.max = max;
	}

	public static MemorySettings of(String min, String max) {
		return new MemorySettings(min, max);
	}

	public String getMin() {
		return min;
	}

	public String getMax() {
		return max;
	}

}
