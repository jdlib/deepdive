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


import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import deepdive.Context;
import deepdive.actual.Actual;
import deepdive.actual.java.lang.IntegerActual;
import deepdive.actual.java.lang.LongActual;
import deepdive.impl.ActualChange;


/**
 * An Actual implementation for {@link Temporal} objects.
 * @param <T> the temporal type
 * @param <BACK> the type of the owner of the TemporalActual
 * @param <IMPL> the type of the concrete TemporalActual implementation 
 */
public class TemporalActual<T extends Temporal,BACK,IMPL extends TemporalActual<T,BACK,IMPL>> extends Actual<T,BACK,IMPL>
{
	/**
	 * Creates a new TemporalActual.
	 * @param value the value
	 * @param back the owner
	 */
	public TemporalActual(T value, BACK back)
	{
		super(value, back);
	}
	
	
	/**
	 * Asserts that the value of the TemporalField equals the expected value.
	 * @param field a field
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL get(TemporalField field, int expected)
	{
		expectEqual(expected, value().get(field), field.toString());
		return self();
	}	
	
	
	/**
	 * Returns a IntegerActual for the value of the TemporalField.
	 * @param field a field
	 * @return the new actual
	 */
	public IntegerActual<IMPL,?> get(TemporalField field)
	{
		return new IntegerActual<>(value().get(field), self()).as(field.toString());
	}	
	
	
	/**
	 * Asserts that the value of the TemporalField equals the expected value.
	 * @param field a field
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL getLong(TemporalField field, long expected)
	{
		expectEqual(expected, value().getLong(field), field.toString());
		return self();
	}	


	/**
	 * Returns a LongActual for the value of the TemporalField.
	 * @param field a field
	 * @return the new actual
	 */
	public LongActual<IMPL,?> getLong(TemporalField field)
	{
		return new LongActual<>(value().getLong(field), self()).as(field.toString());
	}	
	
	
	/**
	 * Asserts that the TemporalField is supported.
	 * @param field the TemporalField
	 * @return this
	 */
	public IMPL isSupported(TemporalField field)
	{
		expectTrue(value().isSupported(field), Context.call("isSupported", field));
		return self();
	}
	
	
	/**
	 * Adds the amount to the Temporal.
	 * @param amountToAdd the amount
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	@ActualChange
	public IMPL plus(TemporalAmount amountToAdd) 
	{
		setValue((T)value().plus(amountToAdd));
		return self();
	}

	
	/**
	 * Adds the amount to the Temporal.
	 * @param amountToAdd the amount
	 * @param unit the unit
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	@ActualChange
	public IMPL plus(long amountToAdd, TemporalUnit unit)
	{
		setValue((T)value().plus(amountToAdd, unit));
		return self();
	}
	
	
	/**
	 * Adjusts the value with the given adjuster.
	 * @param adjuster the adjuster
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	@ActualChange
	public IMPL with(TemporalAdjuster adjuster)
	{
		setValue((T)value().with(adjuster));
		return self();
	}
	
	
	/**
	 * Set the field value with the given new value.
	 * @param field a field
	 * @param newValue the new values
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	@ActualChange
	public IMPL with(TemporalField field, int newValue)
	{
		setValue((T)value().with(field, newValue));
		return self();
	}
}
