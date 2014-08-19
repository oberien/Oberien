package model.map;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Field {
	String name;
	int evade;
	int strike;
	int moveSpeed;
	int viewRange;
	int viewPlus;
	int attackRange;
	int attackPlus;
	
	Image image;
	
	public Field(String name, int evade, int strike, int moveSpeed, int viewRange, int viewPlus, int attackRange, int attackPlus, int i) {
		this.name = name;
		this.evade = evade;
		this.strike = strike;
		this.moveSpeed = moveSpeed;
		this.viewRange = viewRange;
		this.viewPlus = viewPlus;
		this.attackRange = attackRange;
		this.attackPlus = attackPlus;
		
		StringBuilder field;
		if (i == 0) {
			field = new StringBuilder("000");
		} else {
			int digits = (int)Math.log10((double)i);
			field = new StringBuilder();
			for (int j = 2-digits; j > 0; j--) {
				field.append("0");
			}
			field.append(i);
		}
		
		try {
			image = ImageIO.read(new File("res/imgs/tiles/" + field.toString() + ".png"));
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
	
	public int getMoveSpeed() {
		return moveSpeed;
	}
	
	public int getViewRange() {
		return viewRange;
	}
	
	public int getViewPlus() {
		return viewPlus;
	}
	
	public int getAttackRange() {
		return attackRange;
	}
	
	public int getAttackPlus() {
		return attackPlus;
	}
	
	public Image getImage() {
		return image;
	}
}
