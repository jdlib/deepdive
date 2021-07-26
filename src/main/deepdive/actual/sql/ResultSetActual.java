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
package deepdive.actual.sql;


import java.sql.ResultSet;
import java.sql.SQLException;
import deepdive.Context;
import deepdive.actual.Actual;


/**
 * An Actual implementation for {@link ResultSet} objects.
 * @param <BACK> the type of the owner of the ResultSetActual
 * @param <IMPL> the type of the concrete ResultSetActual implementation 
 */
public class ResultSetActual<BACK,IMPL extends ResultSetActual<BACK,IMPL>> extends Actual<ResultSet,BACK,IMPL>
{
	/**
	 * Creates a new ResultSetActual.
	 * @param value the actual value
	 * @param back the owner object
	 */
	public ResultSetActual(ResultSet value, BACK back)
	{
		super(value, back);
	}
	
	
	public IMPL next() throws SQLException
	{
		expectTrue(value().next());
		return self();
	}
	
	
	public BACK end$() throws SQLException
	{
		not().next();
		return backOrNull();
	}
	
	
	public RowActual<IMPL,?> row()
	{
		return new RowActual<>(value(), self());
	}

	
	/**
	 * An Actual for a single row in an ResultSet.
	 */
	public static class RowActual<BACK,IMPL extends RowActual<BACK,IMPL>> extends Actual<ResultSet,BACK,IMPL>
	{
		public RowActual(ResultSet actual, BACK back)
		{
			super(actual, back);
		}
		
		
		public IMPL nextColumn(Object expected) throws SQLException
		{
			return column(nextColumn_++, expected);
		}
		
		
		public IMPL column(int columnIndex, Object expected) throws SQLException
		{
			Object actual = expected == null ? 
				value().getObject(columnIndex) :
				value().getObject(columnIndex, expected.getClass());
			expectEqual(expected, actual, Context.call("column", columnIndex));
			return self();
		}

		
		public IMPL column(String columnName, Object expected) throws SQLException
		{
			Object actual = expected == null ? 
				value().getObject(columnName) :
				value().getObject(columnName, expected.getClass());
			expectEqual(expected, actual, Context.call("column", columnName));
			return self();
		}

		
		private int nextColumn_ = 1;
	}
}
