/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.menus.elements;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.UnicodeFont;

/**
 *
 * @author Bobthepeanut
 */
public class Button {
    private int x, y, width, height, bwidth, bheight, bheight2, bwidth2;
    private Color color, bcolor, bcolor2;
    private int style;
    private UnicodeFont uf;
    private String letters;
    private static final int STYLE_SIMPLE_SQUARE = 1;
    private static final int STYLE_SQUARE_WITH_BORDER = 2; 
    private static final int STYLE_SQUARE_WITH_DOUBLE_BORDER = 3;    
    private static final int STYLE_SQUARE_WITH_LETTERS = 4;
    private static final int STYLE_SQUARE_WITH_BORDER_AND_LETTERS = 5;
    
    private static Input input;
    
    public static void init(Input in) {
        input = in;
    }
       
    public Button(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.style = STYLE_SIMPLE_SQUARE;
    } 
    
    public Button(int x, int y, int width, int height, int bwidth, int bheight, Color color, Color bcolor) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.bcolor = bcolor;
        this.style = STYLE_SQUARE_WITH_BORDER;
        this.bwidth = bwidth;
        this.bheight = bheight;
    } 
    
    public Button(int x, int y, int width, int height, int bwidth, int bheight, int bwidth2, int bheight2 , Color color, Color bcolor, Color bcolor2) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.bcolor = bcolor;
        this.style = STYLE_SQUARE_WITH_DOUBLE_BORDER;
        this.bwidth = bwidth;
        this.bheight = bheight;
        this.bheight2 = bheight2;
        this.bwidth2 = bwidth2;
        this.bcolor2 = bcolor2;
    } 
    
    public Button(int x, int y, int width, int height, Color color, String letters, UnicodeFont uf) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.style = STYLE_SQUARE_WITH_LETTERS;
        this.letters = letters;
        this.uf = uf;
    }  
    
    public Button(int x, int y, int width, int height, int bwidth, int bheight, Color color, Color bcolor, String letters, UnicodeFont uf) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.bcolor = bcolor;
        this.style = STYLE_SQUARE_WITH_BORDER_AND_LETTERS;
        this.bwidth = bwidth;
        this.bheight = bheight;
        this.letters = letters;
        this.uf = uf;
    }
    
    public void draw(Graphics g) {
        g.setColor(color);
        switch (style) {
            case STYLE_SIMPLE_SQUARE:
                g.fillRect(x, y, width, height);
                break;
            case STYLE_SQUARE_WITH_BORDER:
                g.fillRect(x, y, width, height);
                g.setColor(bcolor);
                g.drawRect(x - (bwidth - width)/2, y - (bwidth - width)/2, bwidth, bheight);
                break;
            case STYLE_SQUARE_WITH_DOUBLE_BORDER:
                g.fillRect(x, y, width, height);
                g.setColor(bcolor);
                g.drawRect(x - (bwidth - width)/2, y - (bwidth - width)/2, bwidth, bheight);
                g.drawRect(x - (bwidth2- width)/2, y - (bwidth2 - width)/2, bwidth2, bheight2);
                break;
            case STYLE_SQUARE_WITH_LETTERS:
                g.fillRect(x, y, width, height);
                uf.drawString(x - uf.getWidth(letters)/2 + width/2, y - uf.getHeight(letters) + height/2, letters);
                break;
            case STYLE_SQUARE_WITH_BORDER_AND_LETTERS:
                g.fillRect(x, y, width, height);
                g.setColor(bcolor);
                g.drawRect(x - (bwidth - width)/2, y - (bwidth - width)/2, bwidth, bheight);
                uf.drawString(x - uf.getWidth(letters)/2 + width/2, y - uf.getHeight(letters) + height/2, letters);
                break;                
            default: 
                throw new RuntimeException();                
        }
    }
    
    public boolean isClicked() {
        int mx = input.getMouseX();
        int my = input.getMouseY();
        if (input.isMouseButtonDown(0) && mx >= x && mx <= x + width && my >= y && my < y + height) {
            return true;            
        } else {
            return false;
        }
    }
}
