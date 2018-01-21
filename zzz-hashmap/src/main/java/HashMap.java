import java.lang.Iterable;

import java.util.Collection;
import java.util.Set;
import java.util.Map;
import java.util.Optional;
import java.util.Iterator;

import java.util.NoSuchElementException;

/**
 * generic HashMap implementation
 *
 * I'm probably going to have to do this eventually anyways, right
 *
 * TODO: rehashing lol
 */
public class HashMap<K, V> implements Map<K, V>, Iterable<Mapping<K, V>> {
	protected class HashMapIterator<K, V>
		implements Iterable<Mapping<K, V>>, Iterator<Mapping<K, V>> {

		protected Iterator<Chain<K, V>> chains;
		protected Iterator<Mapping<K, V>> chain;
		protected Mapping<K, V> next = null;

		HashMapIterator(HashMap<K, V> m) {
			chains = m.dat.iterator();
			hasNext();
		}

		public Iterator<Mapping<K, V>> iterator() {
			return this;
		}

		/**
		 * checks for a next element to return and caches it
		 */
		public boolean hasNext() {
			if(next != null) {
				// already cached next
				return true;
			} else if(chain != null && chain.hasNext()) {
				// non-empty chain
				next = chain.next();
				return true;
			} else if(chains.hasNext()) {
				// maybe a chain left?
				while(chains.hasNext()) {
					Chain<K, V> c = chains.next();
					if(c != null) {
						chain = c.iterator();
						// check again with our
						// non-null but MAYBE empty
						// chain
						return hasNext();
					}
				}
			}
			// nothin in `chains` or `chain`
			return false;
		}

		public Mapping<K, V> next() {
			if(hasNext()) {
				Mapping<K, V> ret = next;
				next = null;
				return ret;
			} else {
				throw new NoSuchElementException();
			}
		}

		public void remove() {
			// i think this should work
			if(chain != null) {
				chain.remove();
			}
		}
	}

	/**
	 * an unordered list of key-value mappings
	 */
	protected class Chain<K, V> implements Iterable<Mapping<K, V>> {
		ArrayList<Mapping<K, V>> arr;

		Chain() {
			arr = new ArrayList<>(1);
		}

		public Iterator<Mapping<K, V>> iterator() {
			return arr.iterator();
		}

		/**
		 * set key to value if present, otherwise add the mapping
		 */
		public V put(K key, V value) {
			Optional<Mapping<K, V>> m = getMapping(key);
			if(m.isPresent()) {
				m.get().value = value;
			} else {
				arr.add(new Mapping(key, value));
				super.size++;
			}
			return value;
		}

		public Optional<V> remove(Object key) {
			Iterator<Mapping<K, V>> itr = arr.iterator();
			while(itr.hasNext()) {
				Mapping<K, V> m = itr.next();
				if(m.key.equals(key)) {
					itr.remove();
					super.size--;
					return Optional.ofNullable(m.value);
				}
			}
			return Optional.empty();
		}

		public Optional<Mapping<K, V>> getMapping(Object key) {
			for(Mapping<K, V> m : arr) {
				if(m.key.equals(key)) {
					return Optional.ofNullable(m);
				}
			}
			return Optional.empty();
		}

		/**
		 * a get / contains COMBO function
		 */
		public Optional<V> get(Object key) {
			return getMapping(key).flatMap(m -> m.value);
		}
	}

	public static final int DEFAULT_INITIAL_CAPACITY = 16;
	public static final float DEFAULT_LOAD_FACTOR = 0.75f;

	protected float loadFactor = DEFAULT_LOAD_FACTOR;
	protected ArrayList<Chain<K, V>> dat;
	protected int size = 0;

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

	protected Chain<K, V> chain(Object key) {
		return dat.get(key.hashCode() % dat.size());
	}

	public void clear() {
		for(Chain<K, V> d : dat) {
			d = new Chain<>();
		}
		size = 0;
	}

	public boolean containsKey(Object key) {
		return chain(key).get(key).isPresent();
	}

	public boolean containsValue(Object value) {
		for(Mapping<K, V> m : this) {
			if(m.value.equals(value)) {
				return true;
			}
		}
		return false;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public V put(K key, V value) {
		return chain(key).put(key, value);
	}

	public V get(Object key) {
		return chain(key).get(key).orElse(null);
	}

	public void putAll(Map<? extends K, ? extends V> m) {
		m.forEach((k, v) -> put(k, v));
	}

	public V remove(Object key) {
		chain(key).remove(key);
	}

	public int size() {
		return size;
	}

	public Iterator<Mapping<K, V>> iterator() {
		return new HashMapIterator(this);
	}

	// TODO lol
	public Set<Map.Entry<K,V>> entrySet() { return null;  }
	public boolean equals(Object o)       { return false; }
	public int hashCode()                 { return 0;     }
	public Set<K> keySet()                { return null;  }
	public Collection<V> values()         { return null;  }

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
