package controller;

import java.util.Arrays;

import org.newdawn.slick.Color;

import controller.ranges.FullAttackRangeThread;
import controller.ranges.MoveRangeThread;

import model.*;
import model.map.*;

public class Main {
	static State state;
	
	public static void main(String[] args) throws InterruptedException {
		Map map = MapList.getInstance().getMap("Tera Rising");
		
		state = new State(MapList.getInstance().getCurrentMap(), new Player[]{new Player("Player 1", Color.green, 0), new Player("Player 2", Color.red, 1)});
		Controller controller = new Controller(state);
		
		Coordinate c = new Coordinate(76, 60, Layer.Ground);
//		controller.addModel(76, 60, "Base");
//		Coordinate c = new Coordinate(0, 0, Layer.Ground);
		controller.addModel(c, ModelList.getInstance().getModel("Producing Builder", state.getCurrentPlayer()));
		Thread.sleep(1000);
		System.out.println(Arrays.toString(state.getMoverange(c)));
	}
}