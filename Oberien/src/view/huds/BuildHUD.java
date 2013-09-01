/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.huds;

import controller.StateMap;
import model.Model;
import model.ModelList;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Bobthepeanut
 */
public class BuildHUD implements HUD {

	private int width = 400, height = 200, sWidth, sHeight, textureHeight, textureWidth, imagesPerRow;
	private ModelList modelList;
	private Model[] modelArray;
	private Image[][] units;
	private Model currentModel;
	private boolean available;

	public void init(GameContainer gc, Image[][] units) {
		sWidth = gc.getWidth();
		sHeight = gc.getHeight();
		this.modelList = ModelList.getInstance();
		this.units = units;
		textureHeight = units[0][0].getHeight();
		textureWidth = units[0][0].getWidth();
		imagesPerRow = width / textureWidth;
	}

	public void draw(Graphics g, StateMap sm, StateBasedGame sbg, Model unit) {
		g.setColor(new Color(0.8f, 0.8f, 0.7f, 0.7f));
		g.fillRoundRect(0, sHeight - height, width, height, 20);
		if (unit != null) {
			modelArray = modelList.getModelsOfType(unit.getBuilds());
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
		if (mousePressed) {
			if (mcoord.getX() > 0 && mcoord.getX() <= width && mcoord.getY() < sHeight && mcoord.getY() >= sHeight - height) {
				int x = (int) (mcoord.getX()/units[0][0].getWidth());
				int y = (int) (mcoord.getY()/units[0][0].getHeight());
				int i = x*y;
				currentModel = modelArray[i];
				available = false;
			} else {
				available = true;
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
}
