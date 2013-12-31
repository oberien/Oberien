/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.huds;

public interface HUD {
    /**
     * Used to determine the rendering order in HUDRenderer.
     * 
     * @return The Priority level of this HUD. 0 = highest
     */
    public int getPriority();
}
