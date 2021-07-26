package deepdive.impl;


import java.util.ArrayList;
import java.util.List;


/**
 * Config allows you to customize what type of assertion errors are thrown
 * and how error messages and value are formatted.
 */
public class Config
{
	/**
	 * Returns a string which describes the current config.
	 * @return the info string
	 */
	public static String info()
	{
		List<Stmt> stmts = new ArrayList<>();
		info(stmts, ErrorFactory.class, ErrorFactory.get());
		info(stmts, ErrorFormat.class,  ErrorFormat.get());
		info(stmts, ValueFormat.class,  ValueFormat.get());
		info(stmts, Difference.class,   Difference.get());
		return Stmt.formatAll(stmts, new StringBuilder()).toString();
	}
	
	
	private static <T> void info(List<Stmt> stmts, Class<T> type, T impl)
	{
		stmts.add(new Stmt(type.getSimpleName(), impl.toString()));
	}
}
