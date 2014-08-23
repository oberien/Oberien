/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.renderer;

import java.awt.Font;

import model.Model;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.state.StateBasedGame;

import view.huds.BuildHUD;
import view.huds.MainHUD;
import controller.State;

public class HUDRenderer {
	
	private MainHUD mhud;
	private BuildHUD bhud;
	
	public HUDRenderer(Font font, int width, Image[][] units, GameContainer gc) throws SlickException {
		mhud = new MainHUD(font, width);
		bhud = new BuildHUD();
		bhud.init(gc, units);
	}
	
	public void draw(Graphics g, State state, StateBasedGame sbg, Model selected) { 
		mhud.draw(g, state, sbg);
		bhud.draw(g, sbg, selected);
	}
	
	public void update(boolean mousePressed, Point mcoord) {
		bhud.update(mousePressed, mcoord);
	}
	
	public boolean isMouseEventAvailable() {
		return bhud.isMouseEventAvailable();
	}
	
	public Model getSelectedModel() {
		return bhud.getSelectedModel();
	}
	
	public void resetSelection() {
		bhud.resetSelection();
	}
}
