/*
 * Copyright 2016 Google Inc.
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

package hello.main;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import java.util.Iterator;

/** A parser for {@link WrapperOptions}. */
final class WrapperOptionsParser {

	private static final Splitter COMMA_SPLITTER = Splitter.on(',');
	private static final Splitter COLON_SPLITTER = Splitter.on(':');
	private static final Splitter ARG_SPLITTER =
			Splitter.on(CharMatcher.breakingWhitespace()).omitEmptyStrings().trimResults();

	/** Parses {@link WrapperOptions}. */
	static WrapperOptions parse(Iterable<String> options) {
		WrapperOptions.Builder optionsBuilder = WrapperOptions.builder();
		Iterator<String> it = options.iterator();
		while (it.hasNext()) {
			String option = it.next();
			String flag;
			String value;
			int idx = option.indexOf('=');
			if (idx >= 0) {
				flag = option.substring(0, idx);
				value = option.substring(idx + 1, option.length());
			} else {
				flag = option;
				value = null;
			}
			switch (flag) {
				case "-d":
					optionsBuilder.dir(value);
					break;
				default:
					throw new IllegalArgumentException("unexpected flag: " + flag);
			}
		}
		return optionsBuilder.build();
	}
}
