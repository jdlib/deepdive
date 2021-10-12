package deepdive.actual.lang;


import static deepdive.ExpectThat.*;
import java.util.Arrays;
import org.junit.Test;
import deepdive.actual.AbstractActualTest;


public class IterableActualTest extends AbstractActualTest
{
	@Test public void test()
	{
		Iterable<String> it = Arrays.asList("a", "bc", "def");
		expectThat(it)
			.contains("a")
			.not().contains("x")
			.contains()
				.noneOf("x", "y")
				.someOf("a", "b")
				.back()
			.not().empty()
			.size(3)
			.size()
				.less(4)
				.back();
	}


	@Test public void testErrorMessages()
	{
		Iterable<String> it2 = Arrays.asList("a", "b");
		Iterable<String> it0 = Arrays.asList();
		
		failAssert(() -> expectThat(it2).empty()).msgLines(
			"ArrayList=<[a, b]>",
			"expected to be empty", 
			"but was: [a, b]");
		
		failAssert(() -> expectThat(it0).not().empty()).msgLines(
			"ArrayList=<[]>",
			"expected not to be empty",
			"but was: []");
	}
}
