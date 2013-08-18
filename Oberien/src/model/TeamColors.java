package model;

import java.awt.Color;

public class TeamColors {
	private static Color[] col = new Color[]{
		Color.BLUE,
		Color.RED,
		Color.GREEN,
		Color.CYAN,
		Color.YELLOW
	};
	
	public static Color get(int i) {
		return col[i];
	}
}
