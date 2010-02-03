package se.mockachino.matchers.matcher;

import se.mockachino.util.Formatting;

public class AndMatcher<T> extends Matcher<T> {
	private final Matcher<T>[] delegates;

	public AndMatcher(Matcher<T>... delegates) {
		this.delegates = delegates;
	}

	@Override
	public boolean matches(T value) {
		for (Matcher<T> delegate : delegates) {
			if (!delegate.matches(value)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public Class<T> getType() {
		if (delegates.length == 0) {
			return (Class<T>) Object.class;
		}
		return delegates[0].getType();
	}

	@Override
	protected String asString() {
		return "(" + Formatting.join(" & ", (Object[]) delegates) + ")";
	}
}