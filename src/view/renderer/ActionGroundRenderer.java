/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.renderer;

import controller.State;
import logger.ErrorLogger;
import model.AttackingModel;
import model.Model;
import model.map.Coordinate;
import model.unit.Unit;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class ActionGroundRenderer {

	private Coordinate[] enmop;
	
	public ActionGroundRenderer() {
		
	}
	
	public void draw(Graphics g, State state, Coordinate selectedModel, boolean build) {
		try {
			Coordinate[] range;
			
			if (selectedModel != null) {
				Model m = state.getModel(selectedModel);
				if (m != null && !m.isActionDone()) {
					if (build) {
						g.setColor(new Color(0.1f, 0.1f, 0.1f, 0.5f));
						range = state.getBuildrange(selectedModel);
						for (int i = 0; i < range.length; i++) {
							g.fillRect(range[i].getX() * 32, range[i].getY() * 32, 32, 32);
						}
					} else {
						if (m instanceof Unit && !((Unit)m).isMoved()) {
							g.setColor(new Color(0.3f, 0.9f, 0.3f, 0.5f));
							range = state.getMoverange(selectedModel);
							for (int i = 0; i < range.length; i++) {
								g.fillRect(range[i].getX() * 32, range[i].getY() * 32, 32, 32);
							}
						}
						
						if (m instanceof AttackingModel) {
							g.setColor(new Color(1.0f, 0.1f, 0.1f, 0.5f));
							if (m instanceof Unit && !((Unit)m).isMoved()) {
								range = state.getFullAttackrange(selectedModel);
							} else {
								range = state.getDirectAttackrange(selectedModel);
							}
							enmop = state.getEnemyModelPositionsInArea(range);
							for (Coordinate c : range) {
								for (Coordinate p : enmop) {
									if (c.equals(p)) {
										g.fillRect(p.getX() * 32, p.getY() * 32, 32, 32);
									}
								}
							}
						}
					}
				}
			}
		} catch (NullPointerException e) {
			ErrorLogger.logger.severe(e.getMessage());
		}
	}
}
