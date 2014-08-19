package controller.ranges;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import model.Layer;
import model.map.Coordinate;
import model.map.FieldList;
import controller.MyHashMap;

public class DirectAttackRangeThread extends Thread {
	private DirectAttackRangeThread superThread;
	private controller.State state;
	private Coordinate c;
	private int attackrange;
	private MyHashMap<Coordinate, Integer> fields;
	private boolean add;
	private ArrayList<Coordinate> ret;
	
	private int threadsLeft;
	private DirectAttackRangeThread thisThread;
	
	public DirectAttackRangeThread(DirectAttackRangeThread superThread, controller.State state, Coordinate c, int attackrange, MyHashMap<Coordinate, Integer> fields, boolean add) {
		this.superThread = superThread;
		this.state = state;
		this.c = c;
		this.attackrange = attackrange;
		this.fields = fields;
		this.add = add;
		
		ret = new ArrayList<Coordinate>();
		thisThread = this;
	}
	
	public void run() {
		ret.add(c);
		fields.put(c, attackrange);
		threadsLeft = 4;
		new Thread() {
			public void run() {
				//0=North; 1=East; 2=South; 3=West
				for (int i = 0; i < 4; i++) {
					int attack = attackrange;
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
					attack -= FieldList.getInstance().get(b).getActionRange();
					if (attack >= 0 && (!fields.containsKey(act) || fields.get(act) < attack)) {
						if (attack > 0) {
							new DirectAttackRangeThread(thisThread, state, act, attack, fields, add).start();
						} else {
							ret.add(act);
							fields.put(act, attack);
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
				} catch (InterruptedException e) {e.printStackTrace();}
			}
		}
		
		removeDuplicates(ret);
		Coordinate[] coords = new Coordinate[ret.size()];
		coords = ret.toArray(coords);
		if (superThread == null) {
			if (add) {
				state.addDirectAttackrange(state.getModel(c), coords);
			} else {
				state.updateDirectAttackrange(state.getModel(c), coords);
			}
//			System.out.println("finished");
//			System.out.println(Arrays.toString(state.getDirectAttackrange(c)));
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