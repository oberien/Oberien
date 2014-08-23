/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.huds;

import model.BuildingModel;
import model.Model;
import model.ModelList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.state.StateBasedGame;

public class BuildHUD implements HUD {

	private int width = 400, height = 200, sWidth, sHeight, textureHeight, textureWidth, imagesPerRow, posx, posy;
	private ModelList modelList;
	private Model[] modelArray;
	private Image[][] units;
	private Model currentModel;
	private boolean available;
	private int index = -1;

	public void init(GameContainer gc, Image[][] units) {
		sWidth = gc.getWidth();
		sHeight = gc.getHeight();
		this.modelList = ModelList.getInstance();
		this.units = units;
		textureHeight = units[0][0].getHeight();
		textureWidth = units[0][0].getWidth();
		imagesPerRow = width / textureWidth;
		posx = 0;
		posy = sHeight - height;
	}

	public void draw(Graphics g, StateBasedGame sbg, Model unit) {
		g.setColor(new Color(0.8f, 0.8f, 0.7f, 0.7f));
		g.fillRoundRect(posx, posy, width, height, 20);
		if (unit != null && unit instanceof BuildingModel) {
			BuildingModel b = (BuildingModel)unit;
			if (index > -1) { 
				g.fillRect((index + posx) *32, posy, 32, 32);
			}
			modelArray = modelList.getModelsOfType(b.getBuilds());
			int currentRow = 0;
			int currentImage = 0;
			for (int i = 0; i < modelArray.length; i++) {
				g.drawImage(units[modelArray[i].getId()][0], currentImage * textureWidth, currentRow * textureHeight + sHeight - height);
				currentImage++;
				if (i >= imagesPerRow) {
					currentRow++;
					currentImage = 0;
				}
			}
		}
	}
	
	public void update(boolean mousePressed, Point mcoord) {
		available = mousePressed;
		if (mousePressed) {
			if (mcoord.getX() > 0 && mcoord.getX() <= width && mcoord.getY() < sHeight && mcoord.getY() >= sHeight - height) {
				int x = (int) ((mcoord.getX() - posx)/units[0][0].getWidth());
				int y = (int) ((mcoord.getY() - posy)/units[0][0].getHeight());
				index = x / (y * imagesPerRow + 1);
				try {
					currentModel = modelArray[index];
				} catch (NullPointerException e) {
					//TODO errormessage?
				} catch (ArrayIndexOutOfBoundsException e) {
					//TODO errormessage?
				}
				available = false;
			}
		}
	}

	@Override
	public int getPriority() {
		return 1;
	}
	
	public Model getSelectedModel() {
		return currentModel;
	}
	
	public boolean isMouseEventAvailable() {
		return available;
	}
	
	public void resetSelection() {
		index = 0;
		currentModel = null;
	}
}
