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
package deepdive.actual.java.lang.reflect;


import java.lang.reflect.Modifier;
import deepdive.actual.java.lang.IntegerActual;


/**
 * An Actual implementation for {@link Modifier} values.
 */
public class ModifierActual<BACK,IMPL extends ModifierActual<BACK,IMPL>> extends IntegerActual<BACK,IMPL>
{
	/**
	 * Creates a new ModifierActual.
	 * @param value the actual value
	 * @param back the owner object
	 */
	public ModifierActual(int value, BACK back)
	{
		super(value, back);
	}
	
	
	/**
	 * Asserts that the abstract flag is set.
	 * @return this
	 */
	public IMPL isAbstract()
	{
		expectTrue(Modifier.isAbstract(intValue()), "isAbstract");
		return self();
	}
	
	
	/**
	 * Asserts that the final flag is set.
	 * @return this
	 */
	public IMPL isFinal()
	{
		expectTrue(Modifier.isFinal(intValue()), "isFinal");
		return self();
	}
	
	
	/**
	 * Asserts that the interface flag is set.
	 * @return this
	 */
	public IMPL isInterface()
	{
		expectTrue(Modifier.isInterface(intValue()), "isInterface");
		return self();
	}
	
	
	/**
	 * Asserts that the native flag is set.
	 * @return this
	 */
	public IMPL isNative()
	{
		expectTrue(Modifier.isNative(intValue()), "isNative");
		return self();
	}
	
	
	/**
	 * Asserts that the private flag is set.
	 * @return this
	 */
	public IMPL isPrivate()
	{
		expectTrue(Modifier.isPrivate(intValue()), "isPrivate");
		return self();
	}
	
	
	/**
	 * Asserts that the protected flag is set.
	 * @return this
	 */
	public IMPL isProtected()
	{
		expectTrue(Modifier.isProtected(intValue()), "isProtected");
		return self();
	}
	
	
	/**
	 * Asserts that the public flag is set.
	 * @return this
	 */
	public IMPL isPublic()
	{
		expectTrue(Modifier.isPublic(intValue()), "isPublic");
		return self();
	}
	
	
	/**
	 * Asserts that the static flag is set.
	 * @return this
	 */
	public IMPL isStatic()
	{
		expectTrue(Modifier.isStatic(intValue()), "isStatic");
		return self();
	}
	
	
	/**
	 * Asserts that the strict flag is set.
	 * @return this
	 */
	public IMPL isStrict()
	{
		expectTrue(Modifier.isStrict(intValue()), "isStrict");
		return self();
	}
	
	
	/**
	 * Asserts that the synchronized flag is set.
	 * @return this
	 */
	public IMPL isSynchronized()
	{
		expectTrue(Modifier.isSynchronized(intValue()), "isSynchronized");
		return self();
	}
	
	
	/**
	 * Asserts that the transient flag is set.
	 * @return this
	 */
	public IMPL isTransient()
	{
		expectTrue(Modifier.isTransient(intValue()), "isTransient");
		return self();
	}
	
	
	/**
	 * Asserts that the volate flag is set.
	 * @return this
	 */
	public IMPL isVolatile()
	{
		expectTrue(Modifier.isVolatile(intValue()), "isVolatile");
		return self();
	}
}
