package controller.ranges;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import model.Layer;
import model.map.Coordinate;
import model.map.FieldList;
import controller.MyHashMap;

public class FullAttackRangeThread extends Thread {
	private FullAttackRangeThread superThread;
	private controller.State state;
	private Coordinate c;
	private int attackrange;
	private MyHashMap<Coordinate, Integer> fields;
	private ArrayList<Coordinate> ret;
	private boolean add;
	
	private int threadsLeft;
	private FullAttackRangeThread thisThread;
	
	public FullAttackRangeThread(FullAttackRangeThread superThread, controller.State state, Coordinate c, int attackrange, MyHashMap<Coordinate, Integer> fields, boolean add) {
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
		
		if (superThread == null) {
			threadsLeft++;
			new Thread() {
				public void run() {
					Coordinate[] moverange = state.getMoverange(c);
					while (moverange == null) {
						try {
							Thread.sleep(10);
							moverange = state.getMoverange(c);
						} catch (InterruptedException e) {e.printStackTrace();}
					}
					threadsLeft += moverange.length;
					threadsLeft--;
					System.out.println("THREADSLEFT: " + threadsLeft);
					for (Coordinate c : moverange) {
						new FullAttackRangeThread(thisThread, state, c, attackrange, fields, add).start();
					}
				}
			}.start();
			
		}
		
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
							new FullAttackRangeThread(thisThread, state, act, attack, fields, add).start();
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
				state.addFullAttackrange(state.getModel(c), coords);
			} else {
				state.updateFullAttackrange(state.getModel(c), coords);
			}
//			System.out.println("finishedAttack");
//			System.out.println(Arrays.toString(state.getFullAttackrange(c)));
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