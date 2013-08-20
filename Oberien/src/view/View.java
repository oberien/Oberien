/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;


import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import view.data.StartData;
import view.gamesstates.GameRunning;
import view.gamesstates.GameLoading;
import view.gamesstates.Menu;

/**
 *
 * @author Bobthepeanut
 */
public class View extends StateBasedGame {

    public View() {
        super("Futuristic roundbased game");
    }
    
    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
        StartData sd = new StartData();
        this.addState(new Menu(sd));
        this.addState(new GameLoading(sd));
        this.addState(new GameRunning(sd));
    }
}
