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


import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import deepdive.actual.Actual;
import deepdive.actual.io.*;
import deepdive.actual.lang.*;
import deepdive.actual.net.*;
import deepdive.actual.nio.file.*;
import deepdive.actual.time.*;
import deepdive.actual.util.*;
import deepdive.actual.util.regex.*;
import deepdive.actual.util.stream.StreamActual;
import deepdive.function.CheckedRunnable;


/**
 * ExpectThat provides factory methods to construct Actual implementations for various JDK core classes. 
 */
public class ExpectThat 
{
	public static final int VERSION = 1;
	
	
	public static BooleanActual<?,?> expectThat(boolean actual)
	{
		return new BooleanActual<>(actual, null);
	}

	
	public static BooleanActual<?,?> expectThat(Boolean actual)
	{
		return new BooleanActual<>(actual, null);
	}

	
	public static ByteActual<?,?> expectThat(byte actual)
	{
		return new ByteActual<>(actual, null);
	}

	
	public static ByteActual<?,?> expectThat(Byte actual)
	{
		return new ByteActual<>(actual, null);
	}

	
	public static CharacterActual<?,?> expectThat(char actual)
	{
		return new CharacterActual<>(actual, null);
	}

	
	public static CharacterActual<?,?> expectThat(Character actual)
	{
		return new CharacterActual<>(actual, null);
	}

	
	public static  ClassActual<?,?> expectThat(Class<?> actual)
	{
		return new ClassActual<>(actual, null);
	}

	
	public static <ITEM,COL extends Collection<ITEM>> CollectionActual<ITEM,COL,?,?> expectThat(COL actual)
	{
		return new CollectionActual<>(actual, null);
	}


	public static DoubleActual<?,?> expectThat(double actual)
	{
		return new DoubleActual<>(actual, null);
	}

	
	public static DoubleActual<?,?> expectThat(Double actual)
	{
		return new DoubleActual<>(actual, null);
	}

	
	public static <E extends Enum<E>> EnumActual<E,?,?> expectThat(E actual)
	{
		return new EnumActual<>(actual, null);
	}
	

	public static <T> FileActual<?,?> expectThat(File actual)
	{
		return new FileActual<>(actual, null);
	}


	public static FloatActual<?,?> expectThat(float actual)
	{
		return new FloatActual<>(actual, null);
	}

	
	public static FloatActual<?,?> expectThat(Float actual)
	{
		return new FloatActual<>(actual, null);
	}

	
	public static <T extends InputStream> InputStreamActual<T,?,?> expectThat(T actual)
	{
		return new InputStreamActual<>(actual, null);
	}

	
	public static InstantActual<?,?> expectThat(Instant actual)
	{
		return new InstantActual<>(actual, null);
	}

	
	public static IntegerActual<?,?> expectThat(int actual)
	{
		return new IntegerActual<>(actual, null);
	}

	
	public static IntegerActual<?,?> expectThat(Integer actual)
	{
		return new IntegerActual<>(actual, null);
	}


	public static <T> IteratorActual<T,?,?> expectThat(Iterator<T> actual)
	{
		return new IteratorActual<>(actual, null);
	}

	
	public static <ELEM> IterableActual<ELEM,?,?,?> expectThat(Iterable<ELEM> actual)
	{
		return new IterableActual<>(actual, null);
	}

	
	public static <ELEM,LIST extends List<ELEM>> ListActual<ELEM,LIST,?,?> expectThat(LIST actual)
	{
		return new ListActual<>(actual, null);
	}


	public static LocalDateActual<?,?> expectThat(LocalDate actual)
	{
		return new LocalDateActual<>(actual, null);
	}

	
	public static LocalDateTimeActual<?,?> expectThat(LocalDateTime actual)
	{
		return new LocalDateTimeActual<>(actual, null);
	}

	
	public static LocalTimeActual<?,?> expectThat(LocalTime actual)
	{
		return new LocalTimeActual<>(actual, null);
	}

	
	public static LongActual<?,?> expectThat(long actual)
	{
		return new LongActual<>(actual, null);
	}


	public static LongActual<?,?> expectThat(Long actual)
	{
		return new LongActual<>(actual, null);
	}


	public static <K,V> MapActual<K,V,Map<K,V>,?,?> expectThat(Map<K,V> actual)
	{
		return new MapActual<>(actual, null);
	}


	public static <T> MatcherActual<?,?> expectThat(Matcher actual)
	{
		return new MatcherActual<>(actual, null);
	}
	
	
	public static OffsetDateTimeActual<?,?> expectThat(OffsetDateTime actual)
	{
		return new OffsetDateTimeActual<>(actual, null);
	}


	/**
	 * {@link Actual} itself operates like an Optional:
	 * <ul>
	 * <li>May contain a null value
	 * <li>If the value is accessed directly, it fails if value is null
	 * </ul>
	 * Therefore we don't need an Actual implementation for Optionals.
	 * Instead this simply returns an Actual for the nullable value of the Optional.
	 * @param actual the Optional
	 * @param <T> the type of the Optional valze
	 * @return the new Actual 
	 */
	public static <T> Actual<T,?,?> expectThat(Optional<T> actual)
	{
		return new Actual<>(actual.orElse(null), null);
	}

	
	public static PathActual<?,?> expectThat(Path actual)
	{
		return new PathActual<>(actual, null);
	}


	public static <T> PatternActual<?,?> expectThat(Pattern actual)
	{
		return new PatternActual<>(actual, null);
	}

	
	public static PeriodActual<?,?> expectThat(Period actual)
	{
		return new PeriodActual<>(actual, null);
	}


	public static <T extends Reader> ReaderActual<T,?,?> expectThat(T actual)
	{
		return new ReaderActual<>(actual, null);
	}

	
	public static <T> SetActual<T,Set<T>,?,?> expectThat(Set<T> actual)
	{
		return new SetActual<>(actual, null);
	}


	public static ShortActual<?,?> expectThat(short actual)
	{
		return new ShortActual<>(actual, null);
	}


	public static ShortActual<?,?> expectThat(Short actual)
	{
		return new ShortActual<>(actual, null);
	}


	public static <T> StreamActual<T,?,?> expectThat(Stream<T> actual)
	{
		return new StreamActual<>(actual, null);
	}


	public static StringActual<?,?> expectThat(String actual)
	{
		return new StringActual<>(actual, null);
	}
	
	
	public static <T extends Throwable> ThrowableActual<?,?,?> expectThat(T actual)
	{
		return new ThrowableActual<>(actual, null);
	}


	public static URIActual<?,?> expectThat(URI actual)
	{
		return new URIActual<>(actual, null);
	}


	public static URLActual<?,?> expectThat(URL actual)
	{
		return new URLActual<>(actual, null);
	}


	public static ZonedDateTimeActual<?,?> expectThat(ZonedDateTime actual)
	{
		return new ZonedDateTimeActual<>(actual, null);
	}
	
	
	public static <T> ArrayActual<T,?,?> expectThat(T[] actual)
	{
		return new ArrayActual<>(actual, null);
	}

	
	public static BooleanArrayActual<?,?> expectThat(boolean[] actual)
	{
		return new BooleanArrayActual<>(actual, null);
	}

	
	public static ByteArrayActual<?,?> expectThatByte(byte[] actual)
	{
		return new ByteArrayActual<>(actual, null);
	}

	
	public static CharArrayActual<?,?> expectThat(char[] actual)
	{
		return new CharArrayActual<>(actual, null);
	}

	
	public static DoubleArrayActual<?,?> expectThat(double[] actual)
	{
		return new DoubleArrayActual<>(actual, null);
	}

	
	public static FloatArrayActual<?,?> expectThat(float[] actual)
	{
		return new FloatArrayActual<>(actual, null);
	}

	
	public static IntArrayActual<?,?> expectThat(int[] actual)
	{
		return new IntArrayActual<>(actual, null);
	}

	
	public static LongArrayActual<?,?> expectThat(long[] actual)
	{
		return new LongArrayActual<>(actual, null);
	}

	
	public static ShortArrayActual<?,?> expectThat(short[] actual)
	{
		return new ShortArrayActual<>(actual, null);
	}

	
	public static StringArrayActual<?,?> expectThat(String... actuals)
	{
		return new StringArrayActual<>(actuals, null);
	}

	
	/**
	 * Returns a Actual for the given object.
	 * This method is not named assertThat since this would 
	 * always return a value even if there is not Actual implementation
	 * for the given object.
	 * @param actual a value
	 * @param <T> the type of the actual value
	 * @return the new actual
	 */
	public static <T> Actual<T,?,?> expectThatObject(T actual)
	{
		return new Actual<>(actual, null);
	}


	/**
	 * Runs a runnable which is expected to throw an exception or error 
	 * and returns a ThrowableActual for that error.
	 * @param runnable the runnable
	 * @return the ThrowableActual
	 */
	public static ThrowableActual<?,?,?> expectError(CheckedRunnable<?> runnable)
	{
		return new ThrowableActual<>(ExpectStatic.expectThrows(null, runnable), null);
	}
}
