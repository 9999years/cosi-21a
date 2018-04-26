import java.util.Objects;

/**
 * Optimized for frequent hashCode access
 * @param <K> the key type
 * @param <V> the value type
 */
public class Entry<K, V> {
	private final int hashCode;
	private final K key;
	private final V value;

	Entry(K k, V v) {
		key = k;
		value = v;
		hashCode = Objects.hash(k, v);
	}

	@Override
	public int getHashCode() {
		return hashCode;
	}
}
