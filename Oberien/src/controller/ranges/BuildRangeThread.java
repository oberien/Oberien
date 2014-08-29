package controller.ranges;

import java.util.ArrayList;
import java.util.Collections;

import logger.ErrorLogger;
import model.Layer;
import model.map.Coordinate;
import model.map.FieldList;
import controller.MyHashMap;

public class BuildRangeThread extends Thread {
	private BuildRangeThread superThread;
	private controller.State state;
	private Coordinate c;
	private int builrange;
	private MyHashMap<Coordinate, Integer> fields;
	private boolean add;
	private ArrayList<Coordinate> ret;
	
	private int threadsLeft;
	private BuildRangeThread thisThread;
	
	public BuildRangeThread(BuildRangeThread superThread, controller.State state, Coordinate c, int builrange, MyHashMap<Coordinate, Integer> fields, boolean add) {
		this.superThread = superThread;
		this.state = state;
		this.c = c;
		this.builrange = builrange;
		this.fields = fields;
		this.add = add;
		
		ret = new ArrayList<Coordinate>();
		thisThread = this;
	}
	
	public void run() {
		ret.add(c);
		fields.put(c, builrange);
		threadsLeft = 4;
		new Thread() {
			public void run() {
				//0=North; 1=East; 2=South; 3=West
				for (int i = 0; i < 4; i++) {
					int build = builrange;
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
					build -= FieldList.getInstance().get(b).getActionRange();
					if (build >= 0 && (!fields.containsKey(act) || fields.get(act) < build)) {
						if (build > 0) {
							new BuildRangeThread(thisThread, state, act, build, fields, add).start();
						} else {
							ret.add(act);
							fields.put(act, build);
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
			Coordinate[] sight = state.getSight();
			for (int i = 0; i < ret.size(); i++) {
				boolean inSight = false;
				for (int j = 0; j < sight.length; j++) {
					if (ret.get(i).equals(sight[j])) {
						inSight = true;
					}
				}
				if (!inSight) {
					ret.remove(i);
				}
			}
			if (add) {
				state.addBuildrange(state.getModel(c), coords);
			} else {
				state.updateBuildrange(state.getModel(c), coords);
			}
//			System.out.println("finished");
//			System.out.println(Arrays.toString(state.getBuildrange(c)));
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