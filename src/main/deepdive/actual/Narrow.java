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
package deepdive.actual;


import java.util.function.BiFunction;
import deepdive.impl.Check;


/**
 * Narow allows to Narrow from an Actual to a more 
 * specific one.
 * @see Actual#narrow(Narrow)
 */
public class Narrow<N,BACK,R>
{
	public Narrow(Class<N> type, BiFunction<? super N,? super BACK,R> fn)
	{
		this.type 	= Check.notNull(type, "type");
		this.fn 	= Check.notNull(fn, "fn");
	}
	
	
	public final Class<N> type;
	public final BiFunction<? super N,? super BACK,R> fn;
}
