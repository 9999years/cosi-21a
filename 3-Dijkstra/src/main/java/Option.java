import java.util.function.Predicate;
import java.util.function.Function;
import java.util.function.Consumer;
import java.util.function.Supplier;

import java.lang.Throwable;

import java.util.NoSuchElementException;

/**
 * drop-in replacement for java.util.Optional which supports encoding null
 * values
 *
 * @See java.util.Optional
 */
public class Option<T> {
	protected final T t;
	protected final boolean present;

	protected Option(T t, boolean present) {
		this.t = t;
		this.present = present;
	}

	public static <T> Option<T> empty() {
		return new Option<T>(null, false);
	}

	/**
	 * differs from Optional.of in that it always returns a non-empty
	 * optional
	 */
	public static <T> Option<T> of(T value) {
		return new Option<T>(value, true);
	}

	public Option<T> filter(Predicate<? super T> predicate) {
		if(present && predicate.test(t)) {
			return this;
		} else {
			return empty();
		}
	}

	public <U> Option<U> flatMap(Function<? super T, Option<U>> mapper) {
		return present ? mapper.apply(t) : empty();
	}

	/**
	 * unlike java.util.Optional.map this always returns a non-empty Option
	 * if this Option is non-empty even if the mapper's result is null
	 */
	public <U> Option<U> map(Function<? super T, ? extends U> mapper) {
		return present ? Option.of(mapper.apply(t)) : empty();
	}

	public T get() {
		if(present) {
			return t;
		} else {
			throw new NoSuchElementException();
		}
	}

	public T orElse(T other) {
		return present ? t : other;
	}

	public T orElseGet(Supplier<? extends T> other) {
		return present ? t : other.get();
	}

	public <X extends Throwable> T orElseThrow(
			Supplier<? extends X> exceptionSupplier)
			throws X {
		if(present) {
			return t;
		} else {
			throw exceptionSupplier.get();
		}
	}

	public boolean isPresent() {
		return present;
	}

	public void ifPresent(Consumer<? super T> consumer) {
		consumer.accept(t);
	}

	public boolean equals(Object o) {
		if(o instanceof Option) {
			Option<?> opt = (Option<?>) o;
			if(present && opt.isPresent()) {
				// both present
				return opt.get().equals(t);
			} else {
				// both empty
				return !present && !opt.isPresent();
			}
		}
		return false;
	}

	public int hashCode() {
		return present ? t.hashCode() : 0;
	}

	public String toString() {
		if(present) {
			return "Option.of[" + t + "]";
		} else {
			return "Option.empty";
		}
	}
}
