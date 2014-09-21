package tests;

import model.Layer;
import model.map.Coordinate;
import model.map.Map;
import model.map.MapList;
import model.player.Player;

import org.newdawn.slick.Color;

import controller.Controller;
import controller.State;
import controller.ranges.FowToPolygonThread;
import controller.wincondition.Conquest;

public class FowToPolygonTest {
	public static void main(String[] args) throws InterruptedException {
		Map map = MapList.getInstance().getMap("Tera Rising");
		
		Controller controller = new Controller(MapList.getInstance().getCurrentMap(), new Player[]{new Player("Player 1", Color.red, 0), new Player("Player 2", Color.green, 1)}, "Conquest");
		State state = controller.getState();
		controller.addModel(60, 75, "Base");
		Thread.sleep(2000);
		controller.endTurn();
		controller.addModel(20,20, "Base");
		
		state.setSight(new Coordinate[]{new Coordinate(1,1,Layer.Ground), new Coordinate(2,1,Layer.Ground)});
		new FowToPolygonThread(null, state, null, null).start();
		Thread.sleep(2000);
	}
}
