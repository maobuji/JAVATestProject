/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.lang3;

import java.util.Random;

/**
 * <p>
 * Utility library that supplements the standard {@link Random} class.
 * </p>
 * 
 * @since 3.3
 */
public class RandomUtils {

	/**
	 * Random object used by random method. This has to be not local to the
	 * random method so as to not return the same value in the same millisecond.
	 */
	private static final Random RANDOM = new Random();

	public static long nextLong(final long startInclusive, final long endExclusive) {
		if (startInclusive > endExclusive) {
			return startInclusive;
		}

		if (startInclusive == endExclusive) {
			return startInclusive;
		}

		return (long) nextDouble(startInclusive, endExclusive);
	}

	public static long nextLong() {
		return nextLong(0, Long.MAX_VALUE);
	}

	public static double nextDouble(final double startInclusive, final double endInclusive) {
		if (startInclusive == endInclusive) {
			return startInclusive;
		}

		return startInclusive + ((endInclusive - startInclusive) * RANDOM.nextDouble());
	}

}
