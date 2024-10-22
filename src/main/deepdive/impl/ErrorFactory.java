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


import java.lang.reflect.Constructor;
import java.util.List;
import java.util.stream.Collectors;


/**
 * A factory for AssertionErrors.
 * If AssertionErrors are thrown by failed assertions the {@link #get() currently active ErrorFactory}
 * is asked to create an AssertionError.
 * The default error factory detects if opentest4j or JUnit libraries are in the classpath and if so
 * returns 
 * <ol>
 * <li><code>org.opentest4j.AssertionFailedError</code> for assertion failures
 * <li><code>org.opentest4j.MultipleFailuresError</code> for multiple assertion failures in soft mode
 * <li><code>org.junit.ComparisonFailure</code> if `org.opentest4j.AssertionFailedError` is not available
 * <li><code>java.lang.AssertionError</code> else.
 * </ol>
 * If you want to use other AssertionErrors you can implement and {@link #set(ErrorFactory)} an own ErrorFactory.  
 */
public abstract class ErrorFactory
{
	/**
	 * Returns the currently active ErrorFactory. 
	 * @return the ErrorFactory
	 * @see #set(ErrorFactory)
	 */
	public static ErrorFactory get()
	{
		return current_;
	}

	
	/**
	 * Sets the default ErrorFactory.
	 * @param value the new ErrorFactory object
	 * @see #get() 
	 */
	public static void set(ErrorFactory value)
	{
		current_ = Check.notNull(value, "ErrorFactory");
	}
	
	
	public abstract AssertionError create(String message, Throwable cause, boolean hasValues, Object expected, Object actual);
	
	
	public abstract AssertionError createMulti(String message, List<AssertionError> list);
	
	
	protected AssertionError create(Constructor<? extends AssertionError> ctor, Object... params)
	{
		try
		{
			return ctor.newInstance(params);
		}
		catch (Exception e)
		{
			throw new IllegalStateException("can't create " + ctor.getName(), e);
		}
	}
	
	
	@Override public abstract String toString();


	/**
	 * Default implementation of ErrorFactory.
	 */
	public static class Default extends ErrorFactory
	{
		@Override public AssertionError create(String message, Throwable cause, boolean hasValues, Object expected, Object actual)
		{
			return new AssertionError(message, cause); 
		}


		@Override public AssertionError createMulti(String message, List<AssertionError> list)
		{
			return new AssertionError(message);
		}

		
		@Override public String toString()
		{
			return AssertionError.class.getName();
		}
	}


	/**
	 * A ErrorFactory implementation which uses org.junit.ComparisonFailure
	 * if the error message contains an expected and actual value, 
	 * else just creates an AssertionError
	 */
	public static class JUnit4 extends ErrorFactory
	{
		@SuppressWarnings("unchecked")
		public JUnit4() throws Exception
		{
			Class<? extends AssertionError> c = (Class<? extends AssertionError>)Class.forName("org.junit.ComparisonFailure");
			comparisonFailureCtor_ = c.getConstructor(String.class, String.class, String.class);
		}
		
		
		@Override public AssertionError create(String message, Throwable cause, boolean hasValues, Object expected, Object actual)
		{
			try
			{
				return hasValues ? 
					comparisonFailureCtor_.newInstance(message, Value.format(expected), Value.format(actual)) :
					new AssertionError(message, cause);
			}
			catch (Exception e)
			{
				throw new IllegalStateException("can't create " + toString(), e);
			}
		}
		
		
		@Override public AssertionError createMulti(String message, List<AssertionError> list)
		{
			if (message == null)
				message = "";
			else
				message += "\n\n";
			message += list.stream().map(Throwable::getMessage).collect(Collectors.joining("\n\n"));
			return new AssertionError(message);
		}
		
		
		@Override public String toString()
		{
			return "Junit4";
		}
		
		
		private final Constructor<? extends AssertionError> comparisonFailureCtor_;
	}
	
	
	/**
	 * An ErrorFactory which creates org.opentest4j.AssertionFailedError.
	 * AssertionFailedError is not directly referenced but only via reflection
	 * in order to not create a dependency on that library. 
	 */
	public static class Opentest4j extends ErrorFactory
	{
		/**
		 * Creates a new Opentest4jErrorFactory.
		 * @throws Exception thrown if the opentest4j library is not in the classpath.
		 */
		@SuppressWarnings("unchecked")
		public Opentest4j() throws Exception
		{
			Class<? extends AssertionError> assertionFailedClass = (Class<? extends AssertionError>)Class.forName("org.opentest4j.AssertionFailedError");
			Class<? extends AssertionError> multipleFailures = (Class<? extends AssertionError>)Class.forName("org.opentest4j.MultipleFailuresError");
			withValues_ 	= assertionFailedClass.getConstructor(String.class, Object.class, Object.class, Throwable.class);
			withoutValues_ 	= assertionFailedClass.getConstructor(String.class, Throwable.class);
			multi_ 			= multipleFailures.getConstructor(String.class, List.class);
		}
		
		
		@Override public AssertionError create(String message, Throwable cause, boolean hasValues, Object expected, Object actual)
		{
			return hasValues ? 
				create(withValues_, message, expected, actual, cause) :
				create(withoutValues_, message, cause);
		}
		
		
		@Override public AssertionError createMulti(String message, List<AssertionError> list)
		{
			return create(multi_, message, list);
		}
		
		
		/**
		 * Returns "Opentest4j".
		 */
		@Override public String toString()
		{
			return "Opentest4j";
		}
		
		
		private final Constructor<? extends AssertionError> withoutValues_;
		private final Constructor<? extends AssertionError> withValues_;
		private final Constructor<? extends AssertionError> multi_;
	}


	private static <T extends ErrorFactory> T tryCreate(Class<T> type)
	{
		try
		{
			return type.getConstructor().newInstance();
		}
		catch (Exception e)
		{
			return null;
		}
	}
	
	
	private static ErrorFactory current_;
	static
	{
		ErrorFactory f = tryCreate(ErrorFactory.Opentest4j.class);
		if (f == null)
		{
			f = tryCreate(ErrorFactory.JUnit4.class);
			if (f == null)
				f = new ErrorFactory.Default();
		}
		current_ = f;
	}
}


