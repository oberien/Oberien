package controller;

import org.newdawn.slick.Color;

import model.*;
import model.map.*;

public class Main {
	public static void main(String[] args) {
		MapList.getInstance().getMap("Four Tribes");
		StateMap sm = new StateMap(new Player[]{new Player("BH16", Color.green, 0), new Player("Enemy", Color.red, 1)});
		sm.addModel(9, 12, 0);
		long time = System.currentTimeMillis();
		Coordinate[] c = sm.getRange(new Coordinate(9, 12, Layer.Ground), StateMap.VIEWRANGE);
		System.out.println("time getSight: " + (System.currentTimeMillis()-time));
		for (Coordinate c1 : c) {
			System.out.println(c1);
		}
//		System.out.println(sm.getCurrentPlayer().getMoney());
//		System.out.println(sm.buildModel(new Coordinate(0, 2, Layer.Ground), 0, 1, 0));
//		sm.endTurn();
//		Coordinate[] c = sm.getModelPositionsInArea(sm.getSight());
//		System.out.println(sm.getModel(c[0]));
//		System.out.println(sm.getModel(c[2]).getCurrentBuilding());
//		int ret = sm.attack(c[0], c[2]);
//		System.out.println(ret);
//		System.out.println(sm.getModel(c[1]).getCurrentBuilding());
	}
}
