import java.util.Map;

public class Mapping<K, V> implements Map.Entry<K, V> {
	protected K key;
	protected V value;

	Mapping(K key, V value) {
		this.key   = key;
		this.value = value;
	}

	public boolean equals(Object o) {
		if(o instanceof Map.Entry) {
			Map.Entry<K, V> e = (Map.Entry) o;
			return e.getKey().equals(key)
				&& e.getValue().equals(value);
		}
	}

	public K getKey() {
		return key;
	}

	public V getValue() {
		return value;
	}

	public V setValue(V value) {
		this.value = value;
		return this.value;
	}
}
