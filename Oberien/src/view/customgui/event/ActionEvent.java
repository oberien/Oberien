package view.customgui.event;

public class ActionEvent {
	private final String actionCommand;
	
	public ActionEvent(String actionCommand) {
		this.actionCommand = actionCommand;
	}
	
	public String getActionCommand() {
		return actionCommand;
	}
}
