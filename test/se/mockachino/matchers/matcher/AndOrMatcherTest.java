package se.mockachino.matchers.matcher;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static se.mockachino.matchers.Matchers.*;

public class AndOrMatcherTest {
	@Test
	public void testSimple() {
		OrMatcher<Object> orMatcher = orM();
		assertEquals("false", orMatcher.asString());

		AndMatcher<Object> andMatcher = andM();
		assertEquals("true", andMatcher.asString());

	}

	@Test
	public void testOr() {
		Matcher<String> orMatcher = orM(containsM("Foo"), eqM("Hello"));
		assertEquals(false, orMatcher.matches("X"));
		assertEquals(true, orMatcher.matches("Hello"));
		assertEquals(true, orMatcher.matches("AFooA"));
		assertEquals("(regexp(\".*Foo.*\") | \"Hello\")", orMatcher.asString());
	}

	@Test
	public void testAnd() {
		Matcher<String> andMatcher = andM(containsM("lo"), containsM("Hell"));
		assertEquals(false, andMatcher.matches("aloa"));
		assertEquals(false, andMatcher.matches("Hellas"));
		assertEquals(true, andMatcher.matches("Hello"));
		assertEquals("(regexp(\".*lo.*\") & regexp(\".*Hell.*\"))", andMatcher.asString());
	}


}