/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.eventhandler;

import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Point;

import view.ViewStarter;

/**
 *
 * @author Bobthepeanut
 */
public class MouseEvents {
    
    private Input in;
    
    public void init() {
//    	try {
//			Mouse.create();
//		} catch (LWJGLException e) {
//			e.printStackTrace();
//		}
        in = ViewStarter.getInput();
    }
    
    public boolean isMousePressed(int button) {
        return in.isMousePressed(button);
    }
    
    public Point getMousePos() {
    	return new Point(in.getMouseX(), in.getMouseY());
    }
}
