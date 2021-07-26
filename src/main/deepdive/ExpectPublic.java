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
package deepdive;


import deepdive.impl.Not;


/**
 * ExpectPublic is an implementation of ExpectInterface.
 * It stores a reference to an owner object - the "back" object - (which can be null)  
 * and a context string (which can be null) which is added to an error message
 * if an assertion tested on this object fails.
 */
public class ExpectPublic extends Checkpoint implements ExpectInterface
{
	public ExpectPublic(Object back, CharSequence context)
	{
		this(back, context, null);
	}
	
	
	protected ExpectPublic(Object back, CharSequence context, Not.Holder notHolder)
	{
		back_ 		= back;
		context_ 	= context;
		notHolder_	= notHolder != null ? notHolder : new Not.Holder(); 
	}

	
	@Override public Object backOrNull()
	{
		return back_;
	}


	@Override public ExpectInterface getExpect(CharSequence context)
	{
		return context != null ? new ExpectPublic(this, context) : this;
	}

	
	@Override public CharSequence getContext()
	{
		return context_;
	}
	
	
	@Override public ExpectInterface not()
	{
		notHolder_.toggle();
		return this;
	}
	
	
	@Override public Not getNotAndClear()
	{
		return notHolder_.getAndClear();
	}
	
	
	@Override public String toString()
	{
		return context_ != null ? context_.toString() : super.toString();
	}
	
	
	private final CharSequence context_;
	private final Object back_;
	private final Not.Holder notHolder_;
}