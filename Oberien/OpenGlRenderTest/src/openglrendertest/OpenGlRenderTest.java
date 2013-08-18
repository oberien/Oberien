/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package openglrendertest;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Bobthepeanut
 */
public class OpenGlRenderTest extends BasicGame{
    
    private Image tex;
    private float texWidth = ((float)1920)/500, texHeight = ((float)1080)/500;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SlickException {
        AppGameContainer agc = new AppGameContainer(new OpenGlRenderTest());
        agc.setDisplayMode(1920, 1080, true);
        agc.setVSync(false);
        agc.setClearEachFrame(true);
        agc.start();
    }
    
    public OpenGlRenderTest() {
        super("RenderTest");
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        tex = new Image("res/2.png");
        System.out.println(texWidth + " " + texHeight);
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {
        
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        for (int x = 0; x < 1920; x += texWidth) {
            for (int y = 0; y < 1080; y += texHeight) {
                tex.draw(x, y, texWidth, texHeight);
            }
        }
    }
}
