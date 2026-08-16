package deepdive.actual.java.lang;


import static deepdive.ExpectThat.*;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
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
			.iterator()
				.hasNext()
				.back()
			.not().empty()
			.size(3)
			.size()
				.less(4)
				.back();

		expectThat((Iterable<String>)null)
			.blank();

		expectThat((Iterable<String>)List.<String>of())
			.blank();

		expectThat((Iterable<String>)List.of("a", "a"))
			.contains().exactly("a");
		expectThat((Iterable<String>)Set.of("a", "b"))
			.contains().exactly("a", "b");
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
