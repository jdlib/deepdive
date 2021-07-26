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


import java.util.List;
import java.util.Map;
import java.util.Set;
import deepdive.actual.lang.ArrayActual;
import deepdive.actual.lang.ByteArrayActual;
import deepdive.actual.lang.CharacterActual;
import deepdive.actual.lang.DoubleActual;
import deepdive.actual.lang.IntArrayActual;
import deepdive.actual.lang.IntegerActual;
import deepdive.actual.lang.LongActual;
import deepdive.actual.lang.LongArrayActual;
import deepdive.actual.lang.NumberActual;
import deepdive.actual.lang.StringActual;
import deepdive.actual.lang.StringArrayActual;
import deepdive.actual.util.ListActual;
import deepdive.actual.util.MapActual;
import deepdive.actual.util.SetActual;


/**
 * Provides factory methods to obtain Narrow objects
 * to narrow a certain Actual to a more specific Actual implementation.
 * @see Actual#narrow(Narrow)
 */
public class Narrows
{
	private static final Narrow<Object[],?,?>	ARRAY 			= new Narrow<>(Object[].class, ArrayActual::new);
	private static final Narrow<byte[],?,?> 	BYTE_ARRAY 		= new Narrow<>(byte[].class, ByteArrayActual::new);
	private static final Narrow<Character,?,?> 	CHARACTER	 	= new Narrow<>(Character.class, CharacterActual::new);
	private static final Narrow<Double,?,?> 	DOUBLE	 		= new Narrow<>(Double.class, DoubleActual::new);
	private static final Narrow<Integer,?,?> 	INTEGER 		= new Narrow<>(Integer.class, IntegerActual::new);
	private static final Narrow<int[],?,?> 		INT_ARRAY 		= new Narrow<>(int[].class, IntArrayActual::new);
	@SuppressWarnings("rawtypes")
	private static final Narrow					LIST 			= new Narrow<>(List.class, ListActual::new);
	private static final Narrow<Long,?,?> 		LONG 			= new Narrow<>(Long.class, LongActual::new);
	private static final Narrow<long[],?,?> 	LONG_ARRAY 		= new Narrow<>(long[].class, LongArrayActual::new);
	private static final Narrow<Number,?,?> 	NUMBER			= new Narrow<>(Number.class, NumberActual::new);
	@SuppressWarnings("rawtypes")
	private static final Narrow					MAP 			= new Narrow<>(Map.class, MapActual::new);
	@SuppressWarnings("rawtypes")
	private static final Narrow					SET 			= new Narrow<>(Set.class, SetActual::new);
	private static final Narrow<String,?,?> 	STRING 			= new Narrow<>(String.class, StringActual::new);
	private static final Narrow<String[],?,?> 	STRING_ARRAY 	= new Narrow<>(String[].class, StringArrayActual::new);


	public static <BACK> Narrow<Object[],BACK,ArrayActual<Object,BACK,?>> array()
	{
		return cast(ARRAY); 
	}


	public static <BACK> Narrow<byte[],BACK,ByteArrayActual<BACK,?>> byteArray()
	{
		return cast(BYTE_ARRAY); 
	}


	public static <BACK> Narrow<Character,BACK,CharacterActual<BACK,?>> character()
	{
		return cast(CHARACTER); 
	}


	public static <BACK> Narrow<Double,BACK,DoubleActual<BACK,?>> double_()
	{
		return cast(DOUBLE); 
	}


	public static <BACK> Narrow<int[],BACK,IntArrayActual<BACK,?>> intArray()
	{
		return cast(INT_ARRAY); 
	}


	public static <BACK> Narrow<Integer,BACK,IntegerActual<BACK,?>> integer()
	{
		return cast(INTEGER); 
	}


	@SuppressWarnings("unchecked")
	public static <BACK> Narrow<List<Object>,BACK,ListActual<Object,List<Object>,BACK,?>> list()
	{
		return LIST; 
	}

	
	public static <BACK> Narrow<Long,BACK,IntegerActual<BACK,?>> long_()
	{
		return cast(LONG); 
	}
	
	
	public static <BACK> Narrow<long[],BACK,LongArrayActual<BACK,?>> longArray()
	{
		return cast(LONG_ARRAY); 
	}


	@SuppressWarnings("unchecked")
	public static <BACK> Narrow<Map<Object,Object>,BACK,MapActual<Object,Object,Map<Object,Object>,BACK,?>> map()
	{
		return MAP; 
	}
	
	
	public static <BACK> Narrow<Number,BACK,NumberActual<Number,BACK,?>> number()
	{
		return cast(NUMBER); 
	}

	
	@SuppressWarnings("unchecked")
	public static <BACK> Narrow<Set<?>,BACK,SetActual<Object,Set<Object>,BACK,?>> set()
	{
		return SET; 
	}

	
	public static <BACK> Narrow<String,BACK,StringActual<BACK,?>> string()
	{
		return cast(STRING); 
	}
	
	
	public static <BACK> Narrow<String[],BACK,StringArrayActual<BACK,?>> stringArray()
	{
		return cast(STRING_ARRAY); 
	}


	@SuppressWarnings("unchecked")
	private static <T,BACK,R> Narrow<T,BACK,R> cast(Narrow<T,?,?> narrow)
	{
		return (Narrow<T,BACK,R>)narrow;
	}
}
