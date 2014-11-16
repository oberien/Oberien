/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.renderer;

import model.AttackingModel;
import model.BuildingModel;
import model.Model;
import model.map.Coordinate;
import model.unit.Unit;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import controller.State;

import logger.ErrorLogger;

import java.util.logging.Level;

public class ActionGroundRenderer {

	public ActionGroundRenderer() {
		
	}
	
	public void draw(Graphics g, State state, Coordinate selectedModelCoordinate, boolean build) {
		try {
			Coordinate[] range;
			
			if (selectedModelCoordinate == null) {
				return;
			}
			Model m = state.getModel(selectedModelCoordinate);
			if (m != null && !m.isActionDone()) {
				if (build || (m instanceof Unit && ((Unit)m).isMoved() && m instanceof BuildingModel)) {
					g.setColor(new Color(0.1f, 0.1f, 0.1f, 0.5f));
					range = state.getBuildrange(selectedModelCoordinate);
					for (Coordinate aRange : range) {
						g.fillRect(aRange.getX() * 32, aRange.getY() * 32, 32, 32);
					}
				} else {
					if (m instanceof Unit && !((Unit)m).isMoved()) {
						g.setColor(new Color(0.3f, 0.9f, 0.3f, 0.5f));
						range = state.getMoverange(selectedModelCoordinate);
						for (Coordinate aRange : range) {
							g.fillRect(aRange.getX() * 32, aRange.getY() * 32, 32, 32);
						}
					}

					if (m instanceof AttackingModel) {
						g.setColor(new Color(1.0f, 0.1f, 0.1f, 0.5f));
						if (m instanceof Unit && !((Unit)m).isMoved()) {
							range = state.getFullAttackrange(selectedModelCoordinate);
						} else {
							range = state.getDirectAttackrange(selectedModelCoordinate);
						}
						Coordinate[] enmop = state.getEnemyModelPositionsInArea(range);
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
		} catch (NullPointerException e) {
			ErrorLogger.logger.log(Level.SEVERE, "ERROR: ", e);
		}
	}
}
