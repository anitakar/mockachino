package se.mockachino.matchers;

public class ArgumentCatcher<T> implements Matcher<T> {
	private final Matcher<T> delegate;
	private T value;

	public ArgumentCatcher(Matcher<T> delegate) {
		this.delegate = delegate;
	}

	public static <T> ArgumentCatcher<T> create(Matcher<T> delegate) {
		return new ArgumentCatcher<T>(delegate);
	}

	@Override
	public boolean matches(T value) {
		this.value = value;
		return delegate.matches(value);
	}

	@Override
	public Class<T> getType() {
		return delegate.getType();
	}

	public T getValue() {
		return value;
	}
}
