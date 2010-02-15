package se.mockachino;

import se.mockachino.listener.ListenerAdder;
import se.mockachino.matchers.Matchers;
import se.mockachino.order.BetweenVerifyContext;
import se.mockachino.order.MockPoint;
import se.mockachino.order.OrderingContext;
import se.mockachino.stub.StubAnswer;
import se.mockachino.stub.StubReturn;
import se.mockachino.stub.StubThrow;
import se.mockachino.verifier.VerifyRangeStart;

import java.lang.reflect.InvocationHandler;

/**
 * This is the main entry point of all your mocking needs.
 * All operations delegates to a singleton {@link se.mockachino.MockContext}.
 *
 * <p>
 * For most use cases, using this singleton is the most convenient approach,
 * but are also free to use a {@link se.mockachino.MockContext} object directly.
 * <p>
 * Consider statically import this class to increase the readability.
 * 
 * <p>
 * See {@link se.mockachino.matchers.MatchersBase} for available matchers
 * and
 * see {@link se.mockachino.matchers.Matchers} for useful matcher shortcuts.
 */
public class Mockachino extends Matchers {
	public static final MockContext DEFAULT_CONTEXT = new MockContext();

	/**
	 * Creates a new mock with a default handler
	 * @param clazz the interface or class to mock
	 * @return a mock object of the same class
	 */
	public static <T> T mock(Class<T> clazz) {
		return DEFAULT_CONTEXT.mock(clazz);
	}

	/**
	 * Creates a new mock with a custom handler.
	 * @param clazz the class of the returned object
	 * @param handler the handler that is called for each mock method invocation
	 * @return a mock object of the same class
	 */
	public static <T> T mock(Class<T> clazz, InvocationHandler handler) {
		return DEFAULT_CONTEXT.mock(clazz, handler);
	}

	/**
	 * Creates a mock that spies on a specific object.
	 * Unless overridden by stubbing, spied on object will be called for all invocations
	 * @param clazz the class to spy with, must be a superclass of impl
	 * @param impl the object to spy on.
	 * @return the mock object
	 */
	public static <T> T spy(Class<T> clazz, T impl) {
		return DEFAULT_CONTEXT.spy(clazz, impl);
	}

	/**
	 * Creates a mock that spies on a specific object.
	 * Unless overridden by stubbing, spied on object will be called for all invocations
	 * @param impl the object to spy on
	 * @return a mock object of the same class as impl
	 */
	public static <T> T spy(T impl) {
		return DEFAULT_CONTEXT.spy(impl);
	}

	/**
	 * Creates a new ordering context which is used to verify method calls in order.
	 * Ordering contexts are completely independent of each other.
	 *
	 * Typical usage:
	 * <pre>
	 * OrderingContext context = Mockachino.verifyOrder();
	 * context.verifyAtLeast(3).on(mock).method();
	 * </pre>
	 *
	 * @return the new ordering context
	 */
	public static OrderingContext newOrdering() {
		return DEFAULT_CONTEXT.newOrdering();
	}

	/**
	 * Verifies that a method call is never called.
	 *
	 * Typical usage:
	 * <pre>
	 * Mockachino.verifyNever().on(mock).method();
	 * </pre>
	 *
	 * @return a verifier
	 */
	public static VerifyRangeStart verifyNever() {
		return DEFAULT_CONTEXT.verifyNever();
	}

	/**
	 * Verifies that a method call is only called exactly once.
	 *
	 * Typical usage:
	 * <pre>
	 * Mockachino.verifyOnce().on(mock).method();
	 * </pre>
	 *
	 * @return a verifier
	 */
	public static VerifyRangeStart verifyOnce() {
		return DEFAULT_CONTEXT.verifyOnce();
	}

	/**
	 * Verifies that a method call is called at least a specific number of times.
	 *
	 * Typical usage:
	 * <pre>
	 * Mockachino.verifyAtLeast(3).on(mock).method();
	 * </pre>
	 *
	 * @param min number of times the method should be called
	 * @return a verifier
	 */
	public static VerifyRangeStart verifyAtLeast(int min) {
		return DEFAULT_CONTEXT.verifyAtLeast(min);
	}

	/**
	 * Verifies that a method call is called at most a specific number of times.
	 *
	 * Typical usage:
	 * <pre>
	 * Mockachino.verifyAtMost(3).on(mock).method();
	 * </pre>
	 *
	 * @param max number of times the method should be called
	 * @return a verifier
	 */
	public static VerifyRangeStart verifyAtMost(int max) {
		return DEFAULT_CONTEXT.verifyAtMost(max);
	}

	/**
	 * Verifies that a method call is called between min and max times, inclusive.
	 *
	 * Typical usage:
	 * <pre>
	 * Mockachino.verifyRange(1, 2).on(mock).method();
	 * </pre>
	 *
	 * @param min minimum amount of matching method calls
	 * @param max maximum amount of matching method calls
	 * @return a verifier
	 */
	public static VerifyRangeStart verifyRange(int min, int max) {
		return DEFAULT_CONTEXT.verifyRange(min, max);
	}

	/**
	 * Verifies that a method call is called an exact number of times.
	 *
	 * Typical usage:
	 * <pre>
	 * Mockachino.verifyExactly(3).on(mock).method();
	 * </pre>
	 *
	 * @param count number of times the method should be called
	 * @return a verifier
	 */
	public static VerifyRangeStart verifyExactly(int count) {
		return DEFAULT_CONTEXT.verifyExactly(count);
	}

	/**
	 * Stubs a method call to throw an exception
	 *
	 * Typical usage:
	 * <pre>
	 * Mockachino.stubThrow(myException).on(mock).method();
	 * </pre>
	 *
	 * @param e the exception to throw
	 * @return a stubber
	 */
	public static StubThrow stubThrow(Throwable e) {
		return DEFAULT_CONTEXT.stubThrow(e);
	}

	/**
	 * Stubs a method call to return a specific value
	 *
	 * Typical usage:
	 * <pre>
	 * Mockachino.stubReturn(value).on(mock).method();
	 * </pre>
	 *
	 * Note that the type of the value must match the return type of the method call.
	 * This is checked at runtime and will throw a UsageError if they don't match.
	 *
	 * @param returnValue the returnValue to return when the method is called.
	 * @return a stubber
	 */
	public static StubReturn stubReturn(Object returnValue) {
		return DEFAULT_CONTEXT.stubReturn(returnValue);
	}

	/**
	 * Stubs a method call with a specific answer strategy
	 *
	 * Typical usage:
	 * <pre>
	 * Mockachino.stubAnswer(answer).on(mock).method();
	 * </pre>
	 *
	 * All matching method calls will invoke the getValue()-method on the answer-object.
	 *
	 * @param answer the answer to use
	 * @return a stubber
	 */
	public static StubAnswer stubAnswer(CallHandler answer) {
		return DEFAULT_CONTEXT.stubAnswer(answer);
	}

	/**
	 * Add a listener on a specific method. Every time the method is called on the mock,
	 * this listener will get a callback.
	 *
	 * Typical usage:
	 * <pre>
	 * Mockachino.listenWith(myListener).on(mock).method();
	 * </pre>

	 * @param listener the listener
	 * @return a listener adder
	 */
	public static ListenerAdder listenWith(CallHandler listener) {
		return DEFAULT_CONTEXT.listenWith(listener);
	}

	/**
	 * Get a list of all calls made for a mock.
	 * This may be useful for debugging.
	 *
	 * @param mock
	 * @return the list of calls.
	 */
	public static Iterable<MethodCall> getCalls(Object mock) {
		return DEFAULT_CONTEXT.getCalls(mock);
	}

	/**
	 * Resets calls, stubs and listeners for a mock
	 * @param mock
	 */
	public static void reset(Object mock) {
		DEFAULT_CONTEXT.reset(mock);
	}

	/**
	 * Resets calls, stubs and listeners for mocks
	 * @param mocks
	 */
	public static void reset(Object mock, Object... mocks) {
		DEFAULT_CONTEXT.reset(mock, mocks);
	}

	/**
	 * Resets list of calls for a mock
	 * @param mock
	 */
	public static void resetCalls(Object mock) {
		DEFAULT_CONTEXT.resetCalls(mock);
	}

	/**
	 * Resets list of calls for mocks
	 * @param mocks
	 */
	public static void resetCalls(Object mock, Object... mocks) {
		DEFAULT_CONTEXT.resetCalls(mock, mocks);
	}

	/**
	 * Resets list of stubs for a mock
	 * @param mock
	 */
	public static void resetStubs(Object mock) {
		DEFAULT_CONTEXT.resetStubs(mock);
	}

	/**
	 * Resets list of stubs for mocks
	 * @param mocks
	 */
	public static void resetStubs(Object mock, Object... mocks) {
		DEFAULT_CONTEXT.resetStubs(mock, mocks);
	}

	/**
	 * Resets list of listeners for a mock
	 * @param mock
	 */
	public static void resetListeners(Object mock) {
		DEFAULT_CONTEXT.resetListeners(mock);
	}

	/**
	 * Resets list of listeners for mocks
	 * @param mocks
	 */
	public static void resetListeners(Object mock, Object... mocks) {
		DEFAULT_CONTEXT.resetListeners(mock, mocks);
	}

	public MockPoint getCurrentPoint() {
		return DEFAULT_CONTEXT.getCurrentPoint();
	}


	public static BetweenVerifyContext between(MockPoint start, MockPoint end) {
		return DEFAULT_CONTEXT.between(start, end);
	}

	public static BetweenVerifyContext after(MockPoint start) {
		return DEFAULT_CONTEXT.after(start);
	}

	public static BetweenVerifyContext before(MockPoint end) {
		return DEFAULT_CONTEXT.before(end);
	}

}
