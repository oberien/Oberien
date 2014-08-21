package model.player;

import java.awt.Color;

public class PlayerColors {
	private static Color[] col = new Color[]{
		Color.BLUE,
		Color.RED,
		Color.GREEN,
		Color.CYAN,
		Color.MAGENTA,
		Color.YELLOW,
		Color.ORANGE,
		Color.PINK,
		Color.BLACK,
		Color.WHITE,
		Color.GRAY
	};
	
	public static Color get(int i) {
		return col[i];
	}
	
	public static Color getNext(Color c) {
		for (int i = 0; i < col.length; i++) {
			if (c.equals(col[i])) {
				if (i < col.length-1) {
					return col[i+1];
				} else {
					return col[0];
				}
			}
		}
		return null;
	}
}
