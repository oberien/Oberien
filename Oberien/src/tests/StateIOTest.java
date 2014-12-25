package tests;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

import model.map.Map;
import model.map.MapList;
import model.player.Player;

import org.newdawn.slick.Color;

import util.SerializableState;
import util.SerializableStateIO;
import controller.Controller;
import controller.State;
import controller.wincondition.Conquest;

public class StateIOTest {
	private static Controller controller;
	private static State state;
	
	public static void main(String[] args) throws InterruptedException {
		Map map = MapList.getInstance().getMap("Tera Rising");
		
		controller = new Controller(MapList.getInstance().getCurrentMap(), new Player[]{new Player("Player 1", Color.red, 0), new Player("Player 2", Color.green, 1)}, "Conquest");
		state = controller.getState();
		controller.addModel(60, 75, "Base");
		Thread.sleep(2000);
		
		SerializableStateIO sio = new SerializableStateIO();
		sio.addObserver(new Observer() {
			@Override
			public void update(Observable obs, Object o) {
				System.out.println(obs);
				System.out.println(o);
				if (o instanceof SerializableState) {
					SerializableState s = (SerializableState) o;
					controller = new Controller(s);
					state = controller.getState();
					System.out.println(state.getModels());
				}
			}
		});
		sio.writeSerializableState(new File("./saves/sav.dat"), controller.getSerializableState());
		Thread.sleep(1000);
		sio.readSerializableState(new File("./saves/sav.dat"));
	}
}
