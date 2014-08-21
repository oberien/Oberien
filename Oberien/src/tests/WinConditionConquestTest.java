package tests;

import model.Layer;
import model.map.Coordinate;
import model.map.Map;
import model.map.MapList;
import model.player.Player;

import org.newdawn.slick.Color;

import controller.Controller;
import controller.State;
import controller.wincondition.Conquest;

public class WinConditionConquestTest {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		Map map = MapList.getInstance().getMap("Tera Rising");
		
		Controller controller = new Controller(MapList.getInstance().getCurrentMap(), new Player[]{new Player("Player 1", Color.red, 0), new Player("Player 2", Color.green, 1)}, new Conquest());
		State state = controller.getState();
		controller.addModel(60, 75, "Base");
		Thread.sleep(2000);
		controller.endTurn();
		controller.addModel(20, 20, "Base");
		controller.addModel(60, 74, "Leopard 5");
		Thread.sleep(3000);
		Coordinate atk = new Coordinate(60, 74, Layer.Ground);
		Coordinate def = new Coordinate(60, 75, Layer.Ground);
		for (int i = 0; i < 6; i++) {
			controller.attack(atk, def);
			controller.endTurn();
			controller.endTurn();
		}
	}

}
