package view.components;

import java.util.ArrayList;

import view.event.ActionEvent;
import view.event.ActionListener;

public class AbstractButton extends Panel {
	private ArrayList<ActionListener> listeners = new ArrayList<ActionListener>();
	private String actionCommand;
	
	public AbstractButton(float x, float y, float width, float height) {
		super(x, y, width, height);
	}
	
	public void addActionListener(ActionListener l) {
		listeners.add(l);
	}
	
	public void fireActionPerformed(ActionEvent e) {
		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).actionPerformed(e);
		}
	}
	
	public void setActionCommand(String s) {
		this.actionCommand = s;
	}
	
	public String getActionCommand() {
		return actionCommand;
	}
}
