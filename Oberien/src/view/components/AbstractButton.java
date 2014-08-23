package view.components;

import java.util.ArrayList;

import view.event.ActionEvent;
import view.event.ActionListener;

public class AbstractButton extends Panel {
	
	public AbstractButton(float x, float y, float width, float height) {
		super(x, y, width, height);
	}
	
	private ArrayList<ActionListener> actionListeners = new ArrayList<ActionListener>();
	private String actionCommand;
	
	public void addActionListener(ActionListener l) {
		actionListeners.add(l);
	}
	
	public void fireActionPerformed(final ActionEvent e) {
		new Thread() {
			public void run() {
				for (int i = 0; i < actionListeners.size(); i++) {
					actionListeners.get(i).actionPerformed(e);
				}
			}
		}.start();
	}
	
	public void setActionCommand(String s) {
		this.actionCommand = s;
	}
	
	public String getActionCommand() {
		return actionCommand;
	}
}
