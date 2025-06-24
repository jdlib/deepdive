package deepdive.impl;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Difference is a service to describe the difference of two objects.
 * {@link #get()} returns the currently used Difference.
 * If you want to adjust the Difference, create a derived class 
 * and set it via {@link #set(Difference)}.  
 */
public class Difference
{
	/**
	 * Returns the currently active Difference. 
	 * @return the Difference
	 * @see #set(Difference)
	 */
	public static Difference get()
	{
		return current_;
	}
	
	
	/**
	 * Sets the current Difference.
	 * @param value the new Difference
	 * @see #get() 
	 */
	public static void set(Difference value)
	{
		current_ = Check.notNull(value, "value");
	}

	
	/**
	 * A service which receives difference statements.
	 */
	public static interface Log
	{
		public void add(String msg);
		
		
		public void add(String msg, Object value);
		
		
		public void addNotEqual(String what, Object expected, Object actual);
	}
	
	
	/**
	 * Logs the difference of expected and actual object.
	 * The default implementation recognizes and logs arrays, lists, sets and maps.
	 * Any other values are not logged.
	 * @param log receives messages
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public void log(Log log, Object expected, Object actual)
	{
		if (Value.isArray(expected) && Value.isArray(actual))
			logArray(log, expected, actual);
		else if (instancesOf(List.class, expected, actual))
			logList(log, (List<?>)expected, (List<?>)actual);	
		else if (instancesOf(Set.class, expected, actual))
			logSet(log, (Set<?>)expected, (Set<?>)actual);
		else if (instancesOf(Map.class, expected, actual))
			logMap(log, (Map<?,?>)expected, (Map<?,?>)actual);
	}
	
	
	/**
	 * 1) Test component type according to {@link Value#arraysEqual(Object, Object, boolean)}
	 * 2) Test element and size equality
	 * @param log the log
	 * @param expecteds the expected elements
	 * @param actuals the actual elements
	 */
	protected void logArray(Log log, Object expecteds, Object actuals)
	{
		// 1)
		Class<?> typeE = expecteds.getClass().getComponentType();
		Class<?> typeA = actuals.getClass().getComponentType();
		if (typeE != typeA)
		{
			log.add("component type");
			log.addNotEqual("type", typeE, typeA);
		}
		
		// 2)
		logIndexed(log, new GenericArrayList(expecteds), new GenericArrayList(actuals), "len");
	}
	
	
	protected void logList(Log log, List<?> expected, List<?> actual)
	{
		logIndexed(log, expected, actual, "size");
	}

	
	protected void logIndexed(Log log, List<?> expected, List<?> actual, String sizeName)
	{
		int expectedSize	= expected.size();
		int actualSize 		= actual.size();
		int minSize			= Math.min(expectedSize, actualSize);
		
		for (int i=0; i<minSize; i++)
		{
			Object expectedElem = expected.get(i);
			Object actualElem   = actual.get(i);
			if (!Value.equal(expectedElem, actualElem))
				logElemDiffAt(log, i, expectedElem, actualElem);
		}
		if (expectedSize != actualSize)
		{
			log.addNotEqual(sizeName, Integer.valueOf(expectedSize), Integer.valueOf(actualSize));
			logIndexAdditional(log, expected, "missing ", minSize);
			logIndexAdditional(log, actual, "unexpected ", minSize);
		}
	}
	
	
	private void logIndexAdditional(Log log, List<?> list, String what, int start)
	{
		for (int i=start; i<list.size(); i++)
			log.add(what + '[' + i + ']', list.get(i));
	}
	
	
	protected void logElemDiffAt(Log log, int index, Object expectedElem, Object actualElem)
	{
		log.addNotEqual("[" + index + ']', expectedElem, actualElem);
	}
	
	
	protected void logSet(Log log, Set<?> expectedSet, Set<?> actualSet)
	{
		List<Object> missing     = new ArrayList<>();
		List<Object> unexpected  = new ArrayList<>();

		// assert that all expected elements are contained in the actual set
		for (Object expectedElem : expectedSet)
		{
			if (!actualSet.contains(expectedElem))
				missing.add(expectedElem);
		}
		// assert that all actual elements are contained in the expected set
		for (Object actualElem : actualSet)
		{
			if (!expectedSet.contains(actualElem))
				unexpected.add(actualElem);
		}
		
		if (!missing.isEmpty())
			log.add("missing", Value.onlyOrCollection(missing));
		if (!unexpected.isEmpty())
			log.add("unexpected", Value.onlyOrCollection(unexpected));
	}

	
	protected void logMap(Log log, Map<?,?> expectedMap, Map<?,?> actualMap)
	{
	      Map<Object,Object> unexpected 	= new LinkedHashMap<>(actualMap);
	      Map<Object,Object> missing 		= new LinkedHashMap<>();
	      Map<Object,Pair<?,?>> wrongValues = new LinkedHashMap<>();
	      for (Map.Entry<?,?> expEntry : expectedMap.entrySet()) 
	      {
		        Object expKey 	= expEntry.getKey();
		        Object expValue = expEntry.getValue();
		        if (actualMap.containsKey(expKey)) 
		        {
		        	Object actValue = unexpected.remove(expKey);
		        	if (!Value.equal(expValue, actValue)) 
		        		wrongValues.put(expKey, new Pair<>(actValue, expValue));
		        } 
		        else 
		        	missing.put(expKey, expValue);
	      }
	      
	      if (!wrongValues.isEmpty()) 
	      {
	    	  log.add("keys with wrong values");
	    	  for (Map.Entry<?,Pair<?,?>> entry : wrongValues.entrySet()) 
	    	  {
	    		  log.add("for key", entry.getKey());
	    		  Pair<?,?> pair = entry.getValue();
	    		  log.addNotEqual(null, pair.v1, pair.v2);
	    	  }
	      }
	      if (!missing.isEmpty())
	      {
	    	  log.add("missing entries in actual");
	    	  for (Map.Entry<?,?> entry : missing.entrySet()) 
	    	  {
	    		  log.add("for key", entry.getKey());
	    		  log.add("expected", entry.getValue());
	    	  }
	      }
	      if (!unexpected.isEmpty()) 
	      {
	    	  log.add("unexpected entries in actual");
	    	  for (Map.Entry<?,?> entry : unexpected.entrySet()) 
	    	  {
	    		  log.add("for key", entry.getKey());
	    		  log.add("unexpected", entry.getValue());
	    	  }
	      }
	}
	
	
	protected static boolean instancesOf(Class<?> type, Object expected, Object actual)
	{
		return type.isInstance(expected) && type.isInstance(actual);
	}
	
	
	@Override public String toString()
	{
		return "default";
	}
	
	
	private static Difference current_ = new Difference();
}


