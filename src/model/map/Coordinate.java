package model.map;

import model.Layer;

import java.io.Serializable;

public class Coordinate implements Comparable<Coordinate>, Serializable {
	private static final long serialVersionUID = 1L;
	
	private int x;
	private int y;
	private Layer layer;
	
	public Coordinate(int x, int y, Layer layer) {
		this.x = x;
		this.y = y;
		this.layer = layer;
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public Layer getLayer() {
		return layer;
	}
	
	public String toString() {
		return "X=" + x + ", Y=" + y + ", layer=" + layer;
	}
	
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		Coordinate c = (Coordinate) o;
		return this.x == c.x && this.y == c.y && this.layer == c.layer;
	}
	
	public int compareTo(Coordinate o) {
		Coordinate c = (Coordinate) o;
		if (this.x == c.x && this.y == c.y) {
			if (this.layer == c.layer) {
				return 0;
			}
			if (this.layer == Layer.Underground) {
				return -1;
			}
			if (this.layer == Layer.Underwater) {
				if (c.layer == Layer.Underground) {
					return 1;
				} else {
					return -1;
				}
			}
			if (this.layer == Layer.Water) {
				if (c.layer == Layer.Underground || c.layer == Layer.Underwater) {
					return 1;
				} else {
					return -1;
				}
			}
			if (this.layer == Layer.Ground) {
				if (c.layer == Layer.Underground || c.layer == Layer.Underwater || c.layer == Layer.Water) {
					return 1;
				} else {
					return -1;
				}
			}
			if (this.layer == Layer.Air) {
				return 1;
			}
		}
		if (this.x < c.x) {
			return -1;
		}
		if (this.x > c.x) {
			return 1;
		}
		if (this.x == c.x){
			if (this.y < c.y) {
				return -1;
			}
			if (this.y > c.y) {
				return 1;
			}
		}
		return 1;
	}
}
