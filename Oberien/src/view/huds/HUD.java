/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.huds;

import controller.StateMap;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Bobthepeanut
 */
public interface HUD {
    public void draw(Graphics g, StateMap sm, StateBasedGame sbg);
    /**
     * Used to determine the rendering order in HUDRenderer.
     * 
     * @return The Priority level of this HUD. 0 = highest
     */
    public int getPriority();
}
