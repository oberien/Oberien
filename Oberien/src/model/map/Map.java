package model.map;

public class Map {
	private String name;
	private int width;
	private int height;
	private int[] startPosX1;
	private int[] startPosY1;
	private int[] startPosX2;
	private int[] startPosY2;
	private byte[][] map;
	
	private int actWidth;
	private int actHeight;
	
	
	public Map(String name, int width, int height, int[] startPosX1, int[] startPosY1, int[] startPosX2, int[] startPosY2) {
		this.name = name;
		this.width = width;
		this.height = height;
		this.startPosX1 = startPosX1;
		this.startPosY1 = startPosY1;
		this.startPosX2 = startPosX2;
		this.startPosY2 = startPosY2;
		
		map = new byte[width][height];
	}
	
	public int[] getStartPosX1() {
		return startPosX1;
	}

	public int[] getStartPosY1() {
		return startPosY1;
	}

	public int[] getStartPosX2() {
		return startPosX2;
	}

	public int[] getStartPosY2() {
		return startPosY2;
	}

	public boolean add(byte b) {
		if (actWidth > width || actHeight > height) {
			return false;
		}
		map[actWidth][actHeight] = b;
		actWidth++;
		if (actWidth % width == 0) {
			actWidth = 0;
			actHeight++;
		}
		return true;
	}
	
	public byte get(int x, int y) {
		if (x < 0 || y < 0) {
			return -1;
		}
		return map[x][y];
	}
	
	public byte[][] getMap() {
		return map;
	}
	
	public String getName() {
		return name;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
			
				sb.append(map[i][j] + " ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
