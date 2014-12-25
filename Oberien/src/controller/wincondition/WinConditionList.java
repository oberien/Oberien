package controller.wincondition;

import controller.State;

public class WinConditionList {
	public static String[] getWinConditionNames() {
		return new String[] {
				"Conquest"
		};
	}
	
	public static WinCondition getWinCondition(String name, State state) {
		switch (name) {
			case "Conquest":
				return new Conquest(state);
			default:
				return null;
		}
	}
}
