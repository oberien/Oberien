package model.map;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Field {
	String name;
	int evade;
	int strike;
	int movespeed;
	int viewrange;
	int viewplus;
	int attackrange;
	int attackplus;
	
	Image image;
	
	public Field(String name, int evade, int strike, int movespeed, int viewrange, int viewplus, int attackrange, int attackplus, int i) {
		this.name = name;
		this.evade = evade;
		this.strike = strike;
		this.movespeed = movespeed;
		this.viewrange = viewrange;
		this.viewplus = viewplus;
		this.attackrange = attackrange;
		this.attackplus = attackplus;
		
		try {
			image = ImageIO.read(new File("res/tiles/" + i + ".png"));
		} catch (IOException e) {e.printStackTrace();}
	}
	
	public String getName() {
		return name;
	}
	
	public int getDefense() {
		return evade;
	}
	
	public int getAttack() {
		return strike;
	}
	
	public int getMovespeed() {
		return movespeed;
	}
	
	public int getViewrange() {
		return viewrange;
	}
	
	public int getViewplus() {
		return viewplus;
	}
	
	public int getAttackrange() {
		return attackrange;
	}
	
	public int getAttackplus() {
		return attackplus;
	}
	
	public Image getImage() {
		return image;
	}
}
