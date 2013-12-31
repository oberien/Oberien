/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.components;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.InputListener;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.particles.effects.FireEmitter;
import org.newdawn.slick.util.InputAdapter;

import view.event.ActionEvent;

public class Button extends AbstractButton {
    private int bwidth, bheight, bheight2, bwidth2;
    private Color color, bcolor, bcolor2;
    private int style;
    private UnicodeFont uf;
    private String letters;
	private Image img;
	private Image imgPressed;
    private static final int STYLE_SIMPLE_SQUARE = 1;
    private static final int STYLE_SQUARE_WITH_BORDER = 2; 
    private static final int STYLE_SQUARE_WITH_DOUBLE_BORDER = 3;    
    private static final int STYLE_SQUARE_WITH_LETTERS = 4;
    private static final int STYLE_SQUARE_WITH_BORDER_AND_LETTERS = 5;
	private static final int STYLE_SQUARE_TEXTURED = 6;
	private static final int STYLE_SQUARE_TEXTURED_WITH_LETTERS = 7;
    
    private static Input input;
    private boolean pressed;
    
    public static void setInput(Input in) {
        input = in;
    }
       
    public Button(int x, int y, int width, int height, Color color) {
    	super(x, y, width, height);
        this.color = color;
        this.style = STYLE_SIMPLE_SQUARE;
        init();
    } 
    
    public Button(int x, int y, int width, int height, int bwidth, int bheight, Color color, Color bcolor) {
    	super(x, y, width, height);
        this.color = color;
        this.bcolor = bcolor;
        this.style = STYLE_SQUARE_WITH_BORDER;
        this.bwidth = bwidth;
        this.bheight = bheight;
        init();
    } 
    
    public Button(int x, int y, int width, int height, int bwidth, int bheight, int bwidth2, int bheight2 , Color color, Color bcolor, Color bcolor2) {
    	super(x, y, width, height);
        this.color = color;
        this.bcolor = bcolor;
        this.style = STYLE_SQUARE_WITH_DOUBLE_BORDER;
        this.bwidth = bwidth;
        this.bheight = bheight;
        this.bheight2 = bheight2;
        this.bwidth2 = bwidth2;
        this.bcolor2 = bcolor2;
        init();
    } 
    
    public Button(int x, int y, int width, int height, Color color, String letters, UnicodeFont uf) {
    	super(x, y, width, height);
        this.color = color;
        this.style = STYLE_SQUARE_WITH_LETTERS;
        this.letters = letters;
        this.uf = uf;
        init();
    }  
    
    public Button(int x, int y, int width, int height, int bwidth, int bheight, Color color, Color bcolor, String letters, UnicodeFont uf) {
    	super(x, y, width, height);
        this.color = color;
        this.bcolor = bcolor;
        this.style = STYLE_SQUARE_WITH_BORDER_AND_LETTERS;
        this.bwidth = bwidth;
        this.bheight = bheight;
        this.letters = letters;
        this.uf = uf;
        init();
    }
	
    public Button(int x, int y, int width, int height, Color color, Image img, Image imgPressed) {
    	super(x, y, width, height);
        this.color = color;
        this.style = STYLE_SQUARE_TEXTURED;
		this.img = img;
		this.imgPressed = imgPressed;
		init();
    } 
	
	public Button(int x, int y, int width, int height, Color color, Image img, Image imgPressed, String letters, UnicodeFont uf) {		
		super(x, y, width, height);
        this.color = color;
        this.style = STYLE_SQUARE_TEXTURED_WITH_LETTERS;
		this.img = img;
		this.imgPressed = imgPressed;
		this.letters = letters;
		this.uf = uf;
		init();
    }
	
	private void init() {
		input.addPrimaryListener(new InputAdapter() {
			public void mouseClicked(int button, int x, int y, int clickCount) {
				if (belongsToThisComponent(x, y)) {
					ActionEvent e = new ActionEvent(getActionCommand());
					fireActionPerformed(e);
				}
				
			}

			public void mousePressed(int button, int x, int y) {
				if (belongsToThisComponent(x, y)) {
					pressed = true;
				}
			}

			public void mouseReleased(int button, int x, int y) {
				pressed = false;
			}
			
		});
	}
	
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
        g.setColor(color);
        switch (style) {
            case STYLE_SIMPLE_SQUARE:
                g.fillRect(getX(), getY(), getWidth(), getHeight());
                break;
            case STYLE_SQUARE_WITH_BORDER:
                g.fillRect(getX(), getY(), getWidth(), getHeight());
                g.setColor(bcolor);
                g.drawRect(getX() - (bwidth - getWidth())/2, getY() - (bwidth - getWidth())/2, bwidth, bheight);
                break;
            case STYLE_SQUARE_WITH_DOUBLE_BORDER:
                g.fillRect(getX(), getY(), getWidth(), getHeight());
                g.setColor(bcolor);
                g.drawRect(getX() - (bwidth - getWidth())/2, getY() - (bwidth - getWidth())/2, bwidth, bheight);
                g.drawRect(getX() - (bwidth2- getWidth())/2, getY() - (bwidth2 - getWidth())/2, bwidth2, bheight2);
                break;
            case STYLE_SQUARE_WITH_LETTERS:
                g.fillRect(getX(), getY(), getWidth(), getHeight());
                uf.drawString(getX() - uf.getWidth(letters)/2 + getWidth()/2, getY() - uf.getHeight(letters) + getHeight()/2, letters);
                break;
            case STYLE_SQUARE_WITH_BORDER_AND_LETTERS:
                g.fillRect(getX(), getY(), getWidth(), getHeight());
                g.setColor(bcolor);
                g.drawRect(getX() - (bwidth - getWidth())/2, getY() - (bwidth - getWidth())/2, bwidth, bheight);
                uf.drawString(getX() - uf.getWidth(letters)/2 + getWidth()/2, getY() - uf.getHeight(letters) + getHeight()/2, letters);
                break; 
			case STYLE_SQUARE_TEXTURED:
				g.resetTransform();
				if (pressed && imgPressed != null) {
					if (color != null) {
						imgPressed.draw(getX(), getY(), getWidth(), getHeight(), color);
					} else {
						imgPressed.draw(getX(), getY(), getWidth() , getHeight());
					}
				} else {
					if (color != null) {
						img.draw(getX(), getY(), getWidth(), getHeight(), color);
					} else {
						img.draw(getX(), getY(), getWidth() , getHeight());
					}
				}
				break;
			case STYLE_SQUARE_TEXTURED_WITH_LETTERS:
				g.resetTransform();
				if (pressed && imgPressed != null) {
					if (color != null) {
						imgPressed.draw(getX(), getY(), getWidth(), getHeight(), color);
					} else {
						imgPressed.draw(getX(), getY(), getWidth() , getHeight());
					}
				} else {
					if (color != null) {
						img.draw(getX(), getY(), getWidth(), getHeight(), color);
					} else {
						img.draw(getX(), getY(), getWidth() , getHeight());
					}
				}
				uf.drawString(getX() - uf.getWidth(letters)/2 + getWidth()/2, getY() - uf.getHeight(letters) + getHeight()/2, letters);
				break;
            default: 
                throw new RuntimeException();                
        }
    }
}
