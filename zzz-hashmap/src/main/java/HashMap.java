import java.util.Map;
import java.util.Set;
import java.util.Optional;

import java.util.NoSuchElementException;

/**
 * generic HashMap implementation
 *
 * I'm probably going to have to do this eventually anyways, right
 */

/**
 * put:
 * hash modulo buckets, look in cell
 * look in chain list (k-v tuples) to find k
 * none found, add
 *
 * get:
 * "
 */
public class HashMap<K, V> implements Map<K, V> {
	public class Mapping<K, V> {
		public final K key;
		public final V value;

		Mapping(K key, V value) {
			this.key   = key;
			this.value = value;
		}
	}

	protected class Chain<K, V> {
		ArrayList<Mapping<K, V>> arr;

		Chain() {
			arr = new ArrayList<>(1);
		}

		public void add(K key, V val) {
			arr.add(new Mapping(key, val));
		}

		public Optional<V> get(K key) {
			for(Mapping<> m : arr) {
				if(m.key.equals(key)) {
					return Optional.of(m.value);
				}
			}
			return Optional.empty();
		}
	}

	public static final int DEFAULT_INITIAL_CAPACITY = 16;
	public static final int DEFAULT_LOAD_FACTOR = 0.75;

	protected float loadFactor = DEFAULT_LOAD_FACTOR;
	protected ArrayList<Chain<K, V>> dat;

	HashMap() {
		this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
	}

	HashMap(int initialCapacity) {
		this(initialCapacity, DEFAULT_LOAD_FACTOR);
	}

	HashMap(int initialCapacity, float loadFactor) {
		this.loadFactor = loadFactor;
		dat = new ArrayList<>(initialCapacity);
	}

	HashMap(Map<? extends K, ? extends V> m) {
		putAll(m);
	}

	void clear()
	boolean containsKey(Object key)
	boolean containsValue(Object value)
	Set<Map.Entry<K,V>> entrySet()
	boolean equals(Object o)
	int hashCode()
	boolean isEmpty()
	Set<K> keySet()
	V put(K key, V value)
	V get(Object key)

	void putAll(Map<? extends K, ? extends V> m) {
		m.forEach((k, v) -> put(k, v));
	}

	V remove(Object key)
	int size()
	Collection<V> values()

	// DEFAULT METHODS !!!!
	/*
	 * default V compute(K key,
	 * 	BiFunction<? super K,? super V,? extends V> remappingFunction)
	 * default V computeIfAbsent(K key,
	 * 	Function<? super K,? extends V> mappingFunction)
	 * default V computeIfPresent(K key,
	 * 	BiFunction<? super K,? super V,? extends V> remappingFunction)
	 * default void forEach(BiConsumer<? super K,? super V> action)
	 * default V getOrDefault(Object key, V defaultValue)
	 * default V merge(K key, V value,
	 * 	BiFunction<? super V,? super V,? extends V> remappingFunction)
	 * default V putIfAbsent(K key, V value)
	 * default boolean remove(Object key, Object value)
	 * default V replace(K key, V value)
	 * default boolean replace(K key, V oldValue, V newValue)
	 * default void replaceAll(
	 * 	BiFunction<? super K,? super V,? extends V> function)
	 */
}
