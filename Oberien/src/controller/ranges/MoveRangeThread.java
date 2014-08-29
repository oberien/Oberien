package controller.ranges;

import java.util.ArrayList;
import java.util.Collections;

import logger.ErrorLogger;
import model.Layer;
import model.Model;
import model.map.Coordinate;
import model.map.FieldList;
import model.unit.Unit;
import controller.MyHashMap;

public class MoveRangeThread extends Thread {
	private MoveRangeThread superThread;
	private controller.State state;
	private Coordinate c;
	private Model model;
	private Unit u;
	private int movespeed;
	private MyHashMap<Coordinate, Integer> fields;
	private boolean add;
	private ArrayList<Coordinate> ret;
	
	int threadsLeft;
	MoveRangeThread thisThread;
	
	public MoveRangeThread(MoveRangeThread superThread, controller.State state, Coordinate c, Model model, int movespeed, MyHashMap<Coordinate, Integer> fields, boolean add) {
		this.superThread = superThread;
		this.state = state;
		this.c = c;
		this.model = model;
		this.movespeed = movespeed;
		this.fields = fields;
		this.add = add;
		
		ret = new ArrayList<Coordinate>();
		thisThread = this;
	}
	
	public void run() {
		if (!(model instanceof Unit)) {
			return;
		}
		u = (Unit)model;
		ret.add(c);
		fields.put(c, movespeed);
		threadsLeft = 4;
		new Thread() {
			public void run() {
				//0=North; 1=East; 2=South; 3=West
				for (int i = 0; i < 4; i++) {
					int move = movespeed;
					Coordinate act;
					if (i == 0) {
						act = new Coordinate(c.getX(), c.getY() - 1, Layer.Ground);
					} else if (i == 1) {
						act = new Coordinate(c.getX() + 1, c.getY(), Layer.Ground);
					} else if (i == 2) {
						act = new Coordinate(c.getX(), c.getY() + 1, Layer.Ground);
					} else {
						act = new Coordinate(c.getX() - 1, c.getY(), Layer.Ground);
					}
					byte b = state.getMap().get(act.getX(), act.getY());
					if (b < 0) {
						decreaseThreadsLeft();
						continue;
					}
					if (u.canPass(b)) {
						move -= FieldList.getInstance().get(b).getMoveSpeed();
						if (move >= 0 && (!fields.containsKey(act) || fields.get(act) < move)) {
							if (move > 0) {
								new MoveRangeThread(thisThread, state, act, model, move, fields, add).start();
							} else {
								ret.add(act);
								fields.put(act, move);
								decreaseThreadsLeft();
							}
						} else {
							decreaseThreadsLeft();
						}
					} else {
						decreaseThreadsLeft();
					}
				}
			}
		}.start();
		
		if (threadsLeft > 0) {
			synchronized (this) {
				try {
					this.wait();
				} catch (InterruptedException e) {ErrorLogger.logger.severe(e.getMessage());}
			}
		}
		
		removeDuplicates(ret);
		Coordinate[] coords = new Coordinate[ret.size()];
		coords = ret.toArray(coords);
		if (superThread == null) {
			if (add) {
				state.addMoverange(state.getModel(c), coords);
			} else {
				state.updateMoverange(state.getModel(c), coords);
			}
//			System.out.println("finishedMove");
//			System.out.println(Arrays.toString(state.getMoverange(c)));
		} else {
			superThread.add(coords);
			superThread.decreaseThreadsLeft();
		}
	}
	
	public synchronized void add(Coordinate[] coords) {
		for (Coordinate coord : coords) {
			ret.add(coord);
		}
	}
	
	public synchronized void decreaseThreadsLeft() {
		threadsLeft--;
		if (threadsLeft == 0) {
			synchronized (this) {
				this.notify();
			}
		}
	}
	
	private void removeDuplicates(ArrayList<Coordinate> list) {
		Collections.sort(list);
		Coordinate prove = null;
		for (int i = 0; i < list.size(); i++) {
			if (prove == null) {
				prove = list.get(i);
			} else if (prove.equals(list.get(i))) {
				list.remove(i);
				i--;
			} else {
				prove = list.get(i);
			}
		}
	}
}