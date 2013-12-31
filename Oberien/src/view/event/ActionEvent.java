package view.event;

public class ActionEvent {
	private String actionCommand;
	
	public ActionEvent(String actionCommand) {
		this.actionCommand = actionCommand;
	}
	
	public String getActionCommand() {
		return actionCommand;
	}
}
