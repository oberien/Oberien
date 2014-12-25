package controller;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

public class MyHashMap<K, V> extends ConcurrentHashMap implements Serializable {
	private static final long serialVersionUID = 1L;

	public synchronized V get(Object o) {
		Object[] keys = keySet().toArray();
		K k = (K) o;
		for (int i = 0; i < keys.length; i++) {
			K key = (K) keys[i];
			if (key.equals(k)) {
				return (V)super.get(keys[i]);
			}
		}
		return null;
	}
	
	public synchronized Object remove(Object o) {
		Object[] keys = keySet().toArray();
		K k = (K) o;
		for (int i = 0; i < keys.length; i++) {
			K key = (K) keys[i];
			if (key.equals(k)) {
				return super.remove(keys[i]);
			}
		}
		return null;
	}
	
	public synchronized boolean containsKey(Object o) {
		Object[] keys = keySet().toArray();
		K k = (K) o;
		for (int i = 0; i < keys.length; i++) {
			K key = (K) keys[i];
			if (key.equals(k)) {
				return true;
			}
		}
		return false;
	}
}
