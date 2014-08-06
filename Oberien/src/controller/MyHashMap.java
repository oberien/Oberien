package controller;

import java.util.concurrent.ConcurrentHashMap;

import model.map.Coordinate;

public class MyHashMap<K, V> extends ConcurrentHashMap {
	public synchronized V get(Object o) {
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
	
	public synchronized Object remove(Object o) {
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
	
	public synchronized boolean containsKey(Object o) {
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
