package org.becca.cosi21a;

import java.util.Map;
import java.lang.UnsupportedOperationException;

public class Mapping<K, V> implements Map.Entry<K, V> {
	protected K key;
	protected V value;

	Mapping(K key, V value) {
		this.key   = key;
		this.value = value;
	}

	public boolean equals(Object o) {
		if(o instanceof Map.Entry) {
			Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
			return e.getKey().equals(key)
				&& e.getValue().equals(value);
		} else {
			return false;
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
		return value;
	}

	public String toString() {
		return "(" + key + " -> " + value + ")";
	}
}
