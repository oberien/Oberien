package view.components;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class Panel extends Component {
	public Panel(float x, float y, float width, float height) {
		super(x, y, width, height);
	}
	
	public void paintComponents(Graphics g) {
		g.setColor(Color.lightGray);
		g.fillRect(getX(), getY(), getWidth(), getHeight());
		super.paintComponent(g);
	}
}
