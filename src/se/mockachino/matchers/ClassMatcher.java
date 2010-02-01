package se.mockachino.matchers;

public class ClassMatcher<T> implements Matcher<T> {
	private final Class<T> clazz;
	private final Class[] classes;

	public ClassMatcher(Class<T> clazz, Class... classes) {
		this.clazz = clazz;
		this.classes = classes;
	}

	public static <T> ClassMatcher<T> create(Class<T> clazz, Class... classes) {
		return new ClassMatcher<T>(clazz, classes);
	}

	public static ClassMatcher<Integer> anyInt() {
		return create(Integer.class);
	}

	public static ClassMatcher<Long> anyLong() {
		return create(Long.class, Integer.class);
	}

	public static ClassMatcher<Double> anyDouble() {
		return create(Double.class);
	}

	public static ClassMatcher<Float> anyFloat() {
		return create(Float.class, Double.class);
	}

	public static ClassMatcher<Short> anyShort() {
		return create(Short.class, Integer.class);
	}

	public static ClassMatcher<Byte> anyByte() {
		return create(Byte.class, Integer.class);
	}

	public static ClassMatcher<Boolean> anyBoolean() {
		return create(Boolean.class);
	}

	@Override
	public boolean matches(T value) {
		if (clazz.isInstance(value)) {
			return true;
		}
		for (Class clazz : classes) {
			if (clazz.isInstance(value)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Class<T> getType() {
		return clazz;

	}

	public String toString() {
		return "<create:" + list() + ">";
	}

	private String list() {
		StringBuilder builder = new StringBuilder();
		builder.append(clazz.getSimpleName());
		for (Class arg : classes) {
			builder.append(",");
			builder.append(arg.getSimpleName());
		}
		return builder.toString();
	}
}
