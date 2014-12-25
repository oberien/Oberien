package model.map;

import logger.ErrorLogger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Field {
	String name;
	int evade;
	int strike;
	int moveSpeed;
	int viewRange;
	int viewPlus;
	int actionRange;
	int actionPlus;
	
	private Image image;
	
	public Field(String name, int evade, int strike, int moveSpeed, int viewRange, int viewPlus, int attackRange, int attackPlus, int i) {
		this.name = name;
		this.evade = evade;
		this.strike = strike;
		this.moveSpeed = moveSpeed;
		this.viewRange = viewRange;
		this.viewPlus = viewPlus;
		this.actionRange = attackRange;
		this.actionPlus = attackPlus;
		
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
		} catch (IOException e) {ErrorLogger.logger.severe(e.getMessage());}
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
	
	public int getActionRange() {
		return actionRange;
	}
	
	public int getActionPlus() {
		return actionPlus;
	}
	
	public Image getImage() {
		return image;
	}
}
