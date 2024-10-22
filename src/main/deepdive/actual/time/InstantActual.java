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
package deepdive.actual.time;


import java.time.Instant;
import deepdive.Context;
import deepdive.actual.lang.IntegerActual;
import deepdive.actual.lang.LongActual;


/**
 * An Actual implementation for {@link Instant} objects.
 */
public class InstantActual<BACK,IMPL extends InstantActual<BACK,IMPL>> extends TemporalActual<Instant,BACK,IMPL>
{
	/**
	 * Creates a new InstantActual.
	 * @param value the value
	 * @param back the owner
	 */
	public InstantActual(Instant value, BACK back)
	{
		super(value, back);
	}
	
	
	/**
	 * Compares the actual value to the other value and returns the result as IntegerActual.
	 * @param other another instant
	 * @return the new actual
	 */
	public IntegerActual<IMPL,?> compareTo(Instant other)
	{
		return new IntegerActual<>(value().compareTo(other), self()).as("compareTo");
	}
	
	
	/**
	 * Asserts that the epoch seconds of the Instant equals the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL epochSeconds(long expected)
    {
    	expectEqual(expected, value().getEpochSecond(), "epochSeconds");
        return self();
    }

	
    public LongActual<IMPL,?> epochSeconds()
    {
    	return new LongActual<>(value().getEpochSecond(), self()).as("epochSeconds");
    }

    
    public IMPL isAfter(Instant other)
	{
		expectTrue(value().isAfter(other), Context.call("isAfter", other));
		return self();
	}
	
	
	public IMPL isBefore(Instant other)
	{
		expectTrue(value().isBefore(other), Context.call("isBefore", other));
		return self();
	}

	
	/**
	 * Asserts that the nano part of the Instant equals the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL nano(int expected)
    {
    	expectEqual(expected, value().getNano(), "nano");
        return self();
    }


    public IntegerActual<IMPL,?> nano()
    {
    	return new IntegerActual<>(value().getNano(), self()).as("nano");
    }
}
