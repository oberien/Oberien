/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.renderer;

import controller.StateMap;
import model.Layer;
import model.Model;
import model.map.Coordinate;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class ActionGroundRenderer {

    private Coordinate[] enmop;
    
    public ActionGroundRenderer() {
    	
    }
    
    public void draw(Graphics g, StateMap sm, Coordinate selectedModel, boolean build) {
    	Coordinate[] range;
    	
        if (selectedModel != null) {
        	Model m = sm.getModel(selectedModel);
        	if (m != null && !m.isActionDone()) {
        		if (build) {
        			g.setColor(new Color(0.1f, 0.1f, 0.1f, 0.5f));
        			range = sm.getRange(selectedModel, StateMap.BUILDRANGE);
        			for (int i = 0; i < range.length; i++) {
        				g.fillRect(range[i].getX() * 32, range[i].getY() * 32, 32, 32);
        			}
        		} else {
	        		if (!m.isMoved()) {
			            g.setColor(new Color(0.3f, 0.9f, 0.3f, 0.5f));
			            range = sm.getRange(selectedModel, StateMap.MOVERANGE);
			            for (int i = 0; i < range.length; i++) {
			                g.fillRect(range[i].getX() * 32, range[i].getY() * 32, 32, 32);
			            }
	        		}
	        		
	        		if (m.getAttackrange() != 0) {
			            g.setColor(new Color(1.0f, 0.1f, 0.1f, 0.5f));
			            if (!m.isMoved()) {
			            	range = sm.getRange(selectedModel, StateMap.FULL_ATTACKRANGE);
			            } else {
			            	range = sm.getRange(selectedModel, StateMap.DIRECT_ATTACKRANGE);
			            }
			            enmop = sm.getEnemyModelPositionsInArea(range);
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
