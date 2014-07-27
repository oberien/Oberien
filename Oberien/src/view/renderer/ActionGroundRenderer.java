/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.renderer;

import controller.Controller;
import model.AttackingModel;
import model.Layer;
import model.Model;
import model.map.Coordinate;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class ActionGroundRenderer {

	private Coordinate[] enmop;
	
	public ActionGroundRenderer() {
		
	}
	
	public void draw(Graphics g, Controller controller, Coordinate selectedModel, boolean build) {
		Coordinate[] range;
		
		if (selectedModel != null) {
			Model m = controller.getModel(selectedModel);
			if (m != null && !m.isActionDone()) {
				if (build) {
					g.setColor(new Color(0.1f, 0.1f, 0.1f, 0.5f));
					range = controller.getRange(selectedModel, Controller.BUILDRANGE);
					for (int i = 0; i < range.length; i++) {
						g.fillRect(range[i].getX() * 32, range[i].getY() * 32, 32, 32);
					}
				} else {
					if (!m.isMoved()) {
						g.setColor(new Color(0.3f, 0.9f, 0.3f, 0.5f));
						range = controller.getRange(selectedModel, Controller.MOVERANGE);
						for (int i = 0; i < range.length; i++) {
							g.fillRect(range[i].getX() * 32, range[i].getY() * 32, 32, 32);
						}
					}
					
					if (m instanceof AttackingModel) {
						g.setColor(new Color(1.0f, 0.1f, 0.1f, 0.5f));
						if (!m.isMoved()) {
							range = controller.getRange(selectedModel, Controller.FULL_ATTACKRANGE);
						} else {
							range = controller.getRange(selectedModel, Controller.DIRECT_ATTACKRANGE);
						}
						enmop = controller.getEnemyModelPositionsInArea(range);
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
	}
}
