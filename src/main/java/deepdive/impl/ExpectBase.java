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


import java.util.function.Consumer;
import deepdive.Context;
import deepdive.ExpectInterface;
import deepdive.ExpectProtected;
import deepdive.actual.Actual;


/**
 * ExpectBase represents an object which issues expectations (aka assertions).<p>
 * It can describe itself by providing a {@link #getContext() context} description.<br>
 * An ExpectBase may hold a reference to the object which created it (the "owner").
 * A call to {@link #backOrNull()} returns that object or null if there is no such object.
 * Such stacking of ExpectBase objects allows to build a rich context which
 * is included in an error message if an expectation fails.   
 * <p>
 * This class is put into the impl package since it is not directly interesting
 * to pure users of the library even if it is part of the "public" API since 
 * {@link ExpectInterface}, {@link ExpectProtected} and therefore {@link Actual} inherit from ExpectBase.  
 */
public interface ExpectBase
{
	/**
	 * Returns the object which created this ExpectBase or null if there is no such object.
	 * The default implementation returns null.
	 * @return the owner object
	 */
	public default Object backOrNull()
	{
		return null;
	}
	
	
	/**
	 * Returns a CharSequence which describes the context in which this ExpectBase issues assertions.
	 * The context is modeled as CharSequence and not as string in order to allow context
	 * objects which are lazily turned into Strings (see {@link Context}).
	 * The default implementation returns null.
	 * @return the context
	 */
	public default CharSequence getContext()
	{
		return null;
	}
	
	
	/**
	 * Recursively adds all contexts from an ExpectBase and its ancestors
	 * to the list
	 * @param object ignore if not an ExpectBase
	 * @param list receives the contexts 
	 */
	public static void addContexts(Object object, Consumer<CharSequence> list)
	{
		if (object instanceof ExpectBase)
		{
			ExpectBase base = (ExpectBase)object;
			addContexts(base.backOrNull(), list);
			CharSequence context = base.getContext();
			if (context != null)
				list.accept(context);
		}
	}
}