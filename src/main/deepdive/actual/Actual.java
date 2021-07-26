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


import java.util.Arrays;
import java.util.Objects;
import java.util.function.BiFunction;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import deepdive.ExpectInterface;
import deepdive.ExpectProtected;
import deepdive.ExpectPublic;
import deepdive.ExpectThat;
import deepdive.actual.lang.ClassActual;
import deepdive.actual.lang.StringActual;
import deepdive.actual.lang.ThrowableActual;
import deepdive.function.CheckedBiConsumer;
import deepdive.function.CheckedConsumer;
import deepdive.function.CheckedFunction;
import deepdive.impl.ActualChange;
import deepdive.impl.ExpectResult;
import deepdive.impl.Not;
import deepdive.impl.StmtTemplate;
import deepdive.impl.Value;
import deepdive.tool.ActualGenerator;
import deepdive.impl.NotMustBeOff;
import deepdive.impl.StmtTemplate.Input;


/**
 * Actual is a base class for classes which implement fluent assertions
 * on actual values, i.e. values under test.<p>
 * To test a specific type, an Actual implementations for that type
 * will a) store the value and b) offer methods to make assertions about that value:
 * <pre><code>import deepdive.actual.lang.StringActual;
 * 
 * String s = ...
 * StringActual&lt;?,?&gt; a = new StringActual(s, null);
 * a.startsWith("a").endsWith("bc").length(3);
 * </code></pre>
 * The fluent API allows you to chain multiple assertions.
 * <hr>
 * DeepDive comes with Actual implementations for a variety of JDK core classes. {@link ExpectThat}
 * allows for easy instantation of these implementations:
 * <pre><code>
 * import static deepdive.ExpectThat.*;
 * 
 * String s = ...
 * expectThat(s).startsWith("a").endsWith("bc").length(3);
 * </code></pre>
 * <hr>
 * The Actual base class stores the actual value, passed to its constructor and itself provides
 * assertion methods suitable to every Java object (most important the {@link #equal(Object)} method
 * to test equality of actual and expected value).
 * <hr>
 * The Actual constructor also receives a reference to an "owner" object (which can be null).
 * The owner can be another Actual object which allows to establish a fluent API across Actual objects
 * and in effect "dive deep" into assertions.
 * For example <code>StringActual</code> allows you to test the length of a String via its <code>length(int)</code> method
 * but also offers a {@link StringActual#length()} for more generalized testing of the String length
 * <pre><code>
 * import static deepdive.ExpectThat.*;
 * 
 * String s = ...
 * expectThat(s)
 *     .length()        // returning deepdive.actual.lang.IntegerActual()
 *         .greater(1) 
 *         .less(5)
 *         .back()      // going back to the StringActual
 *     .startsWith("a")
 *     .endsWith("bc")
 * </code></pre>
 * <hr>
 * Actual extends {@link ExpectProtected} in order to provide basic assert methods
 * with proper context to its implementations, but without polluting its public API
 * which should only contain the custom test methods provided by the base class
 * and derived classes.
 * <hr>
 * To support writing Actual implementation for your own classes {@link ActualGenerator}
 * is a command-line tool to create an initial stubs of a Actual implementation for
 * a specific type.
 * @param T the type of the tested actual value
 * @param BACK the type of the owner object which constructed the Actual
 * @param IMPL the implementation class of the Actual as recursive type bound 
 */
@ParametersAreNonnullByDefault
public class Actual<T,BACK,IMPL extends Actual<T,BACK,IMPL>> extends ExpectProtected
{
	/**
	 * Creates a new Actual.
	 * @param value the value under test
	 * @param back the object which created the Actual which can be accessed via a call to {@link #back()}
	 */
	public Actual(@Nullable T value, @Nullable BACK back)
	{
		value_	= value;
		back_ 	= back;
	}
	
	
	/**
	 * Returns this Actual casted to IMPL.
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	@NotMustBeOff protected IMPL self()
	{
		notMustBeOff();
		return (IMPL)this;
	}
	
	
	/**
	 * Returns the non-null owner passed to the Actual constructor.
	 * If the owner is null an IllegalArgumentError is thrown.
	 * To access the owner object without a null-check call {@link #backOrNull()}.
	 * @return the owner object
	 */
	public BACK back()
	{
		// we don't use assertNotNull since we don't want to consume any activated not().
		return rejectNull(back_, "back");
	}

	
	/**
	 * Returns the owner object passed to the Actual constructor.
	 * @return the owner object or null.
	 */
	@Nullable @Override public BACK backOrNull()
	{
		return back_;
	}

	
	//----------------------
	// actual value
	//----------------------
	
	
	/**
	 * Returns the non-null actual value.
	 * If it is null an error is thrown.
	 * @return the value
	 */
	public T value()
	{
		return rejectNull(value_, "actual value");
	}
	
	
	/**
	 * Returns the actual value.
	 * @return the value or null
	 */
	@Nullable public T valueOrNull()
	{
		return value_;
	}


	/**
	 * Sets the actual value.
	 * @param newValue the new value
	 * @return this
	 */
	@ActualChange public IMPL setValue(@Nullable T newValue)
	{
		value_ = newValue;
		return self();
	}
	
	
	/**
	 * Calls the function with the actual value (or null) and {@link #setValue(Object) sets} the value
	 * to the function result.
	 * @param fn a function with converts the actual value into the new value
	 * @return this
	 * @throws E thrown if the function fails
	 * @param<E> the type of the exceptions thrown by the function
	 */
	@ActualChange
	public <E extends Exception> IMPL setValue(CheckedFunction<T,T,E> fn) throws E
	{
		return setValue(rejectNull(fn, "fn").apply(valueOrNull()));
	}

	
	//----------------------
	// operations
	//----------------------
	

	/**
	 * Passes this Actual object to the given consumer.
	 * Use case: Pass this Actual to another method which implements some tests without breaking the fluent call chain:
	 * <pre><code>assertThat("abc")
	 *     .startsWith("a")
	 *     .call(this::doMoreTests)
	 *     .endsWith("bc");</code></pre>
	 * @param consumer a consumer   
	 * @return this
	 * @throws E can be thrown by the consumer
	 * @param <E> the type of exception thrown by the consumer
	 */
	@NotMustBeOff public <E extends Exception> IMPL call(CheckedConsumer<IMPL,E> consumer) throws E
	{
		IMPL self = self();
		rejectNull(consumer, "consumer").accept(self);
		return self;
	}
	
	
	//----------------------
	// assertions
	//----------------------
	
	
	/**
	 * Returns a ClassActual for the class of the actual value.
	 * @return the new actual
	 */
	public ClassActual<IMPL,?> clasz()
	{
		return new ClassActual<>(value().getClass(), self()).as("class");
	}

	
	/**
	 * Returns a Contained builder to specify a set of object 
	 * in order to test if this actual value is contained in that set. 
	 * If you call {@link #not()} before {@link #contained()} then
	 * the containment test is effectively negated.
	 * @return a Contained builder
	 */
	@CheckReturnValue
	public Contained contained()
	{
		return new Contained();
	}
	
	
	/**
	 * A builder class to specify a set of objects and
	 * to test if this actual value is contained in that set.
	 */
	public class Contained
	{
		/**
		 * Derived class may override {@link Actual#contained()}
		 * and return a Contained implementation with additional methods.
		 */
		protected Contained()
		{
		}
		
		
		/**
		 * Asserts that the actual value equals one of the given values.
		 * @param expected the expected values
		 * @return the Actual self
		 */
		public IMPL in(Object... expected) 
		{
			return in(expected, Arrays.asList(expected));
		}
		
		
		/**
		 * Asserts that the actual value equals one of the values returned by the iterable.
		 * @param iterable an iterable
		 * @return this
		 */
		public IMPL in(Iterable<?> iterable)
		{
			return in(iterable, iterable);
		}
		
		
		private IMPL in(Object expected, Iterable<?> iterable)
		{
			T actual = value();
			boolean found = false;
			if (expected != null)
			{
				for (Object exp : iterable)
				{
					if (Objects.equals(actual, exp))
					{
						found = true;
						break;
					}
				}
			}
			return expectTo(found, "be one of", expected);
		}
	}
	
	
	/**
	 * Asserts that the actual value equals the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL equal(Object expected)
	{
		expectEqual(expected, valueOrNull());
		return self();
	}

	
	/**
	 * Asserts that the given consumer will raise an error when receiving the actual value.
	 * @param consumer a consumer
	 * @return a ThrowableActual containing the thrown error
	 */
	@NotMustBeOff public ThrowableActual<Throwable,IMPL,?> fails(CheckedConsumer<? super T,?> consumer)
	{
		notMustBeOff();
		Throwable t = expectThrows(null, () -> consumer.accept(value())); 
		return new ThrowableActual<>(t, self());
	}


	/**
	 * Asserts that the given consumer will raise an error when receiving the actual value
	 * and this Actual
	 * @param consumer a consumer which will receive the thrown Throwable and this Actual
	 * @return a ThrowableActual containing the thrown error
	 */
	@NotMustBeOff public ThrowableActual<Throwable,IMPL,?> fails(CheckedBiConsumer<? super T,IMPL,?> consumer)
	{
		notMustBeOff();
		Throwable t = expectThrows(null, () -> consumer.accept(value(), self())); 
		return new ThrowableActual<>(t, self());
	}

	
	/**
	 * Asserts that the actual value has the expected class.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL hasClass(Class<?> expected)
	{
		expectSame(expected, value().getClass(), "class");
		return self();
	}

	
	/**
	 * Asserts that the hash code of the actual value equals the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL hashCode(int expected)
	{
		expectEqual(expected, value().hashCode(), "hashCode");
		return self();
	}
	

	/**
	 * Asserts that the actual value is an instance of the expected class
	 * @param expected the expected type
	 * @return this
	 */
	public IMPL isA(Class<?> expected)
	{
		expectInstance(expected, valueOrNull());
		return self();
	}
	

	/**
	 * Asserts that the actual value is null.
	 * @return this
	 */
	public IMPL isNull()
	{
		expectNull(valueOrNull());
		return self();
	}
	
	
	/**
	 * Toggles not mode.
	 * If not-mode is switched on then the next assertion result
	 * is negated before deciding if the assertion fails.
	 * Overrides the inherited method in order to change access to public. 
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	@CheckReturnValue @Override public IMPL not()
	{
		return (IMPL)super.not();
	}
	
	
	/**
	 * Checks that the current active Not is OFF, else throws an assertion error.
	 * Methods which can't be negated via {@link #not()} should enforce this 
	 * with a call to this check method or {@link #self()}.
	 * They also should mark themselves with the annotation {@link NotMustBeOff} 
	 */
	protected void notMustBeOff()
	{
		if (getNotHolder().get().isOn())
			failNotOn();
	}
	
	
	private void failNotOn()
	{
		failure().notOn().throwError(); 
	}
	

	protected <U> U rejectNull(@Nullable U value, String what)
	{
		if (value == null)
			fail(what + " is null");
		return value;
	}

	
	/**
	 * Extracts a property from the value using the given function and returns an Actual for the result.
	 * To set the proper context for following assertions call {@link Actual#as(CharSequence)} with a 
	 * property context description.<p>
	 * Example: <code>assertThat("abc").prop(Object::hashCode).as("hashCode").not().equal(0).back()</code>
	 * @param prop a function which extracts a property from the actual value
	 * @return the new actual
	 * @throws E can be thrown by the property function
	 * @param <E> the type of exception thrown by the function
	 * @param <P> the property type
	 */
	@NotMustBeOff
	public <P,E extends Exception> Actual<P,IMPL,?> prop(CheckedFunction<? super T,P,E> prop) throws E
	{
		notMustBeOff();
		return new Actual<>(prop.apply(value()), self());
	}

	
	/**
	 * Asserts that the actual value is the same as the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL same(Object expected)
	{
		expectSame(expected, valueOrNull());
		return self();
	}

	
	/**
	 * Asserts that the actual value turned into a String via its {@link Object#toString()} method
	 * equals the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL toString(String expected)
	{
		expectEqual(expected, value().toString(), "toString");
		return self();
	}

	
	/**
	 * Returns a StringActual for the value return by calling {@link Object#toString()} on the actual value.
	 * @return the new actual
	 */
	public StringActual<IMPL,?> toStringActual()
	{
		return new StringActual<>(value().toString(), self()).as("toString");
	}
	
	
    //----------------------
	// soft assertions
	//----------------------

	
	/**
	 * Calls the given consumer which is expected to invoke assertion methods on 
	 * this Actual. All assertion errors are collected and thrown at the end of the call.
	 * @param tester a consumer   
	 * @return this 
	 */
    public IMPL all(CheckedConsumer<IMPL,?> tester) 
    {
    	if (tester != null)
    	{
    		CheckedConsumer<ExpectInterface,?> t = a -> tester.accept(self());  
    		expectAll(t);
    	}
    	return self();
	}

    
     //----------------------
	// eval
	//----------------------

	
    /**
     * {@link #eval(boolean) Evaluates} the condition using the current {@link #not() not}.
     * If it evaluates to false an error is raised using the template, the {@link Input#what what} string
     * and the expected value.
     * @param condition a condition which is expected to be true if not is OFF
     * @param template an error template
     * @param what the what parameter to the template
     * @param expected the expected value
     * @return this
     */
    protected IMPL expectTrue(boolean condition, StmtTemplate template, String what, Object expected) 
    {
		ExpectResult result = ExpectResult.eval(getNotAndClear(), condition);
		if (!result.ok)
			failure().addStmts(template, expected, value(), result.not, what).throwError();
		return self();
    }
    
    
    protected IMPL expectTo(boolean ok, String what, Object expected) 
    {
    	return expectTrue(ok, StmtTemplate.ASSERT_EXPECTED_TO, what, expected);
    }
    
    
    protected ExpectResult eval(boolean ok)
    {
    	return ExpectResult.eval(getNotAndClear(), ok);
    }

    
    //----------------------
	// narrow
	//----------------------
    
    
    /**
     * Calls {@link #narrow(Class, BiFunction)} using the type and function
     * of the given Narrow object.<p>
     * {@link Narrows} provides factory methods to obtain Narrow objects for
     * most core Actual implementations.<p>
     * Example:
     * <pre><code>
     * Actual&lt;?,?,?&gt; a = ...
     * a.narrow(Narrows.string()) // narrow call returns a StringActual
     * 	.startsWith(...)          // invoke methods in StringActual
     * </code></pre>
     * @param narrow a Narrow 
     * @return the narrowed actual
     * @param <R> the return type of the narrow operation
     * @param <N> the narrowed type
     */
    @NotMustBeOff
    public <N extends T,R> R narrow(Narrow<N,BACK,R> narrow)
    {
		notMustBeOff();
		rejectNull(narrow, "narrow");
    	return narrow(narrow.type, narrow.fn);
    }
 
    
	/**
	 * Narrows the value type of this Actual and returns another object based
	 * on the narrowed value and the back object: 
	 * <ol>
	 * <li>Asserts that the value is an instance of the given type
	 * <li>Passes the narrowed value and the back object to the function and returns the result
	 * </ol>
	 * Use case: Pass the type and a constructor handle of another more specific Actual implementation 
	 * to construct and return a specific Actual for the current value and back object. 
	 * Example:
	 * <pre><code>Actual&lt;?,?,?&gt; a = ...
	 * a.narrow(MyType.class, MyTypeActual::new).myTypeMethod(...)
	 * </code></pre> 
	 * @param type a class
	 * @param fn a narrow function
	 * @return the function result
	 * @param <N> a type derived from our value type
	 * @param <R> the type of the function result
	 */
	@NotMustBeOff
	public <N extends T,R> R narrow(Class<N> type, BiFunction<? super N,? super BACK,R> fn)
	{
		notMustBeOff();
		N narrowed = expectInstance(type, valueOrNull());
		if (narrowed instanceof Actual)
			((Actual<?,?,?>)narrowed).name_ = name_;
		return rejectNull(fn, "fn").apply(narrowed, backOrNull());
	}

	
	//----------------------
	// internals
	//----------------------
	

	/**
	 * Called by {@link ActualMixin#internals(ActualMixin)}.
	 * @return an Internals object
	 */
	Internals<IMPL> internals()
	{
		if (internals_ == null)
			internals_ = new Internals<>(this, getNotHolder());
		return internals_;
	}
	

	/**
	 * Internals allows to access certain Actual methods which
	 * - since they are protected - are only available to derived classes.
	 * Protected access serves the primary purpose that clients which
	 * use the Actual - when invoking content assist techniques -  
	 * only see the public API without any implementation specific methods.
	 * The use case for opening up internals are {@link ActualMixin}s
	 * which provide partial Actual implementation without deriving from
	 * a Actual class.
	 */
	public static class Internals<IMPL extends Actual<?,?,?>> extends ExpectPublic
	{
		private Internals(Object actual, Not.Holder notHolder)
		{
			super(actual, null, notHolder);
		}
		
		
		@SuppressWarnings("unchecked")
		private IMPL actual()
		{
			return (IMPL)backOrNull();
		};
		
		
		@SuppressWarnings("unchecked")
		public IMPL self()
		{
			// first get a hold on the actual and then call self
			// in order to do the correct not off check.
			return (IMPL)actual().self();
		}
		
		
	    public ExpectResult eval(boolean ok)
	    {
	    	return actual().eval(ok);
	    }

		
		@Override public ExpectInterface getExpect(CharSequence context)
		{
			return actual().getExpect(context);
		}
		
		
	    @SuppressWarnings("unchecked")
		public IMPL expectTrue(boolean ok, StmtTemplate template, String what, Object expected) 
	    {
	    	return (IMPL)actual().expectTrue(ok, template, what, expected);
	    }
	    
	    
	    @SuppressWarnings("unchecked")
		public IMPL expectTo(boolean ok, String what, Object expected) 
	    {
	    	return (IMPL)actual().expectTo(ok, what, expected);
	    }
	    
	    
	    @Override public String toString()
	    {
	    	return actual().toString() + ".internals";
	    }
	}
	
		
	//----------------------
	// name
	//----------------------

	
	/**
	 * Sets the name of this Actual.
	 * If an assertion error is raised by this Actual then the error message 
	 * will contain that name + the actual value to provide context information
	 * of the error situation.
	 * By default the name of Actual consists of the value's simple class name.
	 * But if you are diving into a sub property of a starting Actual then
	 * naming the sub Actual according to that property will document the call
	 * chain and give proper context information if an error arises.
	 * @param name a context name 
	 * @return this
	 */
	public IMPL as(CharSequence name)
	{
		name_ = name;
		return self();
	}
	

	/**
	 * Returns the name of this Actual object.
	 * The name was either explicitly set via {@link #as(CharSequence)} or
	 * is derived from the actual value or Actual implementation.
	 * @return the name  
	 */
	private String getActualName()
	{
		if (name_ != null)
			return name_.toString();
		else if (value_ != null)
			return value_.getClass().getSimpleName();
		else
			return getClass().getSimpleName().replace("Actual", "");
	}

	
	//----------------------
	// Owned
	//----------------------

	
	@Override public String getContext()
	{
		String name = getActualName();
		if (name.length() > 0)
		{
			T value  = valueOrNull();
			String v = Value.format(value); 
			name += "=<" + v + '>';
		}
		return name;
	}
	

	/**
	 * Returns the {@link #as(CharSequence) name} of this Actual.
	 * @deprecated this methods is deprecated in order to warn you in case you wanted to call {@link #toString(String)}
	 * 		or {@link #toStringActual()}.
	 */
	@Deprecated
	@Override public String toString()
	{
		return getContext();
	}
	
	
	/**
	 * Returns if two Actual objects are equals.
	 * @deprecated this methods is deprecated in order to warn you in case you wanted to call {@link #equal(Object)} 
	 * to test if the actual value equals an expected value.
	 */
	@Deprecated
	@Override public boolean equals(Object other)
	{
		return super.equals(other);
	}
	

	/**
	 * The actual value.
	 */
	private T value_;
	
	/**
	 * The owner object.
	 */
	private final BACK back_;

	/**
	 * The name of the actual object, used in assertion messages.
	 */
	private CharSequence name_;
	
	/**
	 * A lazily constructed Internals instance returned by {@link #internals()}.
	 */
	private Internals<IMPL> internals_;
}
