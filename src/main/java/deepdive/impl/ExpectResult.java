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
package deepdive.impl;

public enum ExpectResult
{
	SUCCESS__NOT_WAS_OFF	(true,  Not.OFF),
	SUCCESS__NOT_WAS_ON		(true,  Not.ON),
	FAILURE__NOT_WAS_OFF	(false, Not.OFF),
	FAILURE__NOT_WAS_ON		(false, Not.ON);
	
	
	/**
	 * Applies the Not to the ok flag and returns the corresponding result-
	 * @param not null is treadet like Not.OFF
	 * @param ok a flag indicating assertion success or failure without considering the not 
	 * @return the final ok
	 */
	public static ExpectResult eval(Not not, boolean ok)
	{
		boolean on = false;
		if (not != null)
		{
			ok = not.apply(ok);
			on = not.isOn();
		}
		return ok ?
			on ? SUCCESS__NOT_WAS_ON : SUCCESS__NOT_WAS_OFF :
			on ? FAILURE__NOT_WAS_ON : FAILURE__NOT_WAS_OFF; 
	}
	
	
	ExpectResult(boolean ok, Not not)
	{
		this.ok = ok;
		this.not = not;
	}
	
	
	public final boolean ok;
	public final Not not;
}
