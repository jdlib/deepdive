package deepdive.actual.java.lang;


import static deepdive.ExpectThat.*;
import org.junit.Test;
import deepdive.actual.AbstractActualTest;


/**
 * Tests {@link CharacterActual}.
 */
public class CharacterActualTest extends AbstractActualTest
{
	@Test public void test()
	{
		expectThat('a')
			.isDefined()
			.not().isDigit()
			.not().isHighSurrogate()
			.isJavaIdentifierPart()
			.isJavaIdentifierStart()
			.isLowerCase()
			.not().isLowSurrogate()
			.not().isSurrogate()
			.not().isTitleCase()
			.not().isUpperCase()
			.isLetter()
			.isUnicodeIdentifierPart()
			.isUnicodeIdentifierStart()
			.less('b')
			.greater(' ')
			.equal('a');
	}
}
