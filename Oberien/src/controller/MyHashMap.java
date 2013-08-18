package controller;

import java.util.HashMap;

import model.map.Coordinate;

public class MyHashMap<K, V> extends HashMap {
	public V get(Object o) {
		Object[] keys = keySet().toArray();
		Coordinate c = (Coordinate) o;
		for (int i = 0; i < keys.length; i++) {
			Coordinate key = (Coordinate) keys[i];
			if (key.equals(c)) {
				return (V)super.get(keys[i]);
			}
		}
		return null;
	}
	
	public Object remove(Object o) {
		Object[] keys = keySet().toArray();
		Coordinate c = (Coordinate) o;
		for (int i = 0; i < keys.length; i++) {
			Coordinate key = (Coordinate) keys[i];
			if (key.equals(c)) {
				return super.remove(keys[i]);
			}
		}
		return null;
	}
	
	public boolean containsKey(Object o) {
		Object[] keys = keySet().toArray();
		Coordinate c = (Coordinate) o;
		for (int i = 0; i < keys.length; i++) {
			Coordinate key = (Coordinate) keys[i];
			if (key.equals(c)) {
				return true;
			}
		}
		return false;
	}
}
