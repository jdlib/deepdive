package deepdive.actual.java.lang;


import deepdive.actual.Actual;
import deepdive.actual.java.lang.reflect.AnnotatedElementActual;
import deepdive.actual.java.lang.reflect.ConstructorActual;
import deepdive.actual.java.lang.reflect.FieldActual;
import deepdive.actual.java.lang.reflect.MethodActual;
import deepdive.actual.java.lang.reflect.ModifierActual;


/**
 * An Actual implementation for Class objects.
 * @param <BACK> the type of the owner of the ClassActual
 * @param <IMPL> the type of the concrete ClassActual implementation 
 */
public class ClassActual<BACK,IMPL extends ClassActual<BACK,IMPL>> extends Actual<Class<?>,BACK,IMPL>
	implements AnnotatedElementActual<Class<?>,IMPL>
{
	/**
	 * Creates a new ClassActual.
	 * @param value the value
	 * @param back the owner
	 */
	public ClassActual(Class<?> value, BACK back)
	{
		super(value, back);
	}


	public IMPL canonicalName(String expected)
	{
		expectEqual(expected, value().getCanonicalName());
		return self();
	}

	
	public ConstructorActual<IMPL,?> constructorPublic(Class<?>... parameterTypes) throws NoSuchMethodException, SecurityException
	{
		return new ConstructorActual<>(value().getConstructor(parameterTypes), self());
	}


	public ConstructorActual<IMPL,?> constructorDeclared(Class<?>... parameterTypes) throws NoSuchMethodException, SecurityException
	{
		return new ConstructorActual<>(value().getDeclaredConstructor(parameterTypes), self());
	}
	
	
	public ClassActual<IMPL,?> declaringClass()
	{
		return new ClassActual<>(value().getDeclaringClass(), self());
	}


	public ClassActual<IMPL,?> enclosingClass()
	{
		return new ClassActual<>(value().getEnclosingClass(), self());
	}


	public ConstructorActual<IMPL,?> enclosingConstructor()
	{
		return new ConstructorActual<>(value().getEnclosingConstructor(), self());
	}


	public MethodActual<IMPL,?> enclodingMethod()
	{
		return new MethodActual<>(value().getEnclosingMethod(), self());
	}

	
	public FieldActual<IMPL,?> fieldPublic(String name) throws NoSuchFieldException, SecurityException
	{
		return new FieldActual<>(value().getField(name), self());
	}


	public FieldActual<IMPL,?> fieldDeclared(String name) throws NoSuchFieldException, SecurityException
	{
		return new FieldActual<>(value().getDeclaredField(name), self());
	}


	public ClassArrayActual<IMPL,?> interfaces()
	{
		return new ClassArrayActual<>(value().getInterfaces(), self()).as("interfaces");
	}
	
	
	public IMPL isAnonymous()
	{
		expectTrue(value().isAnonymousClass());
		return self();
	}


	public IMPL isArray()
	{
		expectTrue(value().isArray());
		return self();
	}

	
	public IMPL isAssignableFrom(Class<?> expected)
	{
		return expectTo(value().isAssignableFrom(expected), "be assignable from", expected);  
	}
	

	public IMPL isAssignableTo(Class<?> expected)
	{
		rejectNull(expected, "expected");
		return expectTo(expected.isAssignableFrom(value()), "be assignable to", expected);  
	}

	
	public IMPL isInstance(Object object, boolean expected)
	{
		expectEqual(expected, value().isInstance(object));
		return self();
	}


	public IMPL isInterface()
	{
		expectTrue(value().isInterface());
		return self();
	}


	public IMPL isPrimitive()
	{
		expectTrue(value().isPrimitive());
		return self();
	}


	public MethodActual<IMPL,?> methodPublic(String name, Class<?>... parameterTypes) throws NoSuchMethodException, SecurityException
	{
		return new MethodActual<>(value().getMethod(name, parameterTypes), self());
	}


	public MethodActual<IMPL,?> methodDeclared(String name, Class<?>... parameterTypes) throws NoSuchMethodException, SecurityException
	{
		return new MethodActual<>(value().getDeclaredMethod(name, parameterTypes), self());
	}
	
	
	public ModifierActual<IMPL,?> modifiers()
	{
		return new ModifierActual<>(value().getModifiers(), self());
	}
	
	
	public IMPL name(String expected)
	{
		expectEqual(expected, value().getName());
		return self();
	}
	
	
	public StringActual<IMPL,?> name()
	{
		return new StringActual<>(value().getName(), self());
	}

	
	public IMPL packageName(String expected)
	{
		String name = value().getName();
		int n = name.lastIndexOf('.');
		String actual = n >= 0 ? name.substring(0, n) : "";
		expectEqual(expected, actual);
		return self();
	}


	public IMPL simpleName(String expected)
	{
		expectEqual(expected, value().getSimpleName());
		return self();
	}
	
	
	public ClassActual<IMPL,?> superClass()
	{
		return new ClassActual<>(value().getSuperclass(), self());
	}
}
