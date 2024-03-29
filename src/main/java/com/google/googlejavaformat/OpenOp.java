/*
 * Copyright 2015 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 *	 http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.googlejavaformat;

import com.google.common.base.MoreObjects;

/**
 * An {@code OpenOp} opens a level. It is an {@link Op} in the sequence of {@link Op}s generated by {@link OpsBuilder}.
 * When the sequence is turned into a {@link Doc} by {@link DocBuilder}, {@link Input.Tok}s delimited by {@code
 * OpenOp}-{@link CloseOp} pairs turn into nested {@link Doc.Level}s.
 */
public final class OpenOp implements Op {
	private final Indent plusIndent;

	private OpenOp(Indent plusIndent) {
		this.plusIndent = plusIndent;
	}

	/**
	 * Make an ordinary {@code OpenOp}.
	 *
	 * @param plusIndent the indent for breaks at this level
	 * @return the {@code OpenOp}
	 */
	public static Op make(Indent plusIndent) {
		return new OpenOp(plusIndent);
	}

	@Override
	public void add(DocBuilder builder) {
		builder.open(plusIndent);
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("plusIndent", plusIndent).toString();
	}
}
