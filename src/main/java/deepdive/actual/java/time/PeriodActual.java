/*
 * Copyright (c) 2021 jdlib, https://github.com/jdlib
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package deepdive.actual.java.time;


import java.time.Period;
import deepdive.actual.Actual;


/**
 * An Actual implementation for {@link Period}.
 */
public class PeriodActual<BACK,IMPL extends PeriodActual<BACK,IMPL>> extends Actual<Period,BACK,IMPL>
{
	/**
	 * Creates a new PeriodActual.
	 * @param value the value
	 * @param back the owner
	 */
	public PeriodActual(Period value, BACK back)
	{
		super(value, back);
	}
	
	
	/**
	 * Asserts that the days equal the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL days(int expected)
	{
		expectEqual(expected, value().getDays(), "days");
		return self();
	}
	
	
	/**
	 * Asserts that the period is negative.
	 * @return this
	 */
	public IMPL isNegative()
	{
		expectTrue(value().isNegative(), "isNegative");
		return self();
	}
	
	
	/**
	 * Asserts that the period is zero.
	 * @return this
	 */
	public IMPL isZero()
	{
		expectTrue(value().isZero(), "isZero");
		return self();
	}
	
	
	/**
	 * Asserts that the months equal the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL months(int expected)
	{
		expectEqual(expected, value().getMonths(), "months");
		return self();
	}
	
	
	/**
	 * Asserts that the years equal the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL totalMonths(long expected)
	{
		expectEqual(expected, value().toTotalMonths(), "totalMonths");
		return self();
	}
	
	
	/**
	 * Asserts that the years equals the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL years(int expected)
	{
		expectEqual(expected, value().getYears(), "years");
		return self();
	}
}
