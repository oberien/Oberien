package model.player;

import java.awt.*;

public class PlayerColors {
	private static Color[] col = new Color[]{
		Color.BLUE,
		Color.RED,
		new Color(14, 69, 18),
		new Color(153, 0, 204),
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
