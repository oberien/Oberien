package controller;

import java.util.ArrayList;
import java.util.Arrays;

import logger.ErrorLogger;
import model.Layer;
import model.map.Coordinate;
import model.map.Map;

import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Vector2f;

import view.data.Globals;

//TODO make this a single-threaded, recursive function

public class FowToPolygonThread extends Thread {
	private FowToPolygonThread superThread;
	private controller.State state;
	private ArrayList<Line> ret;
	
	private int threadsLeft;
	private FowToPolygonThread thisThread;
	private Coordinate c;
	private ArrayList<Coordinate> sight;
	private int tileSize;
	
	private long time;
	
	public FowToPolygonThread(FowToPolygonThread superThread, controller.State state, Coordinate c, ArrayList<Coordinate> sight) {
		this.superThread = superThread;
		this.state = state;
		this.sight = sight;
		this.c = c;
		
		ret = new ArrayList<Line>();
		thisThread = this;
		tileSize = Globals.TILE_SIZE;
	}
	
	public void run() {
		threadsLeft = 0;
		if (superThread == null) {
			time = System.currentTimeMillis();
			final Map map = state.getMap();
			final int mapWidth = map.getWidth();
			final int mapHeight = map.getHeight();
			threadsLeft = mapWidth * mapHeight;
			new Thread() {
				public void run() {
					//initiate variables
					sight = new ArrayList<Coordinate>();
					Coordinate[] c = state.getSight();
					for (int i = 0; i < c.length; i++) {
						sight.add(c[i]);
					}
					
					//start a new Thread for each tile
					System.out.println("START : " + (System.currentTimeMillis()-time));
					for (int x = 0; x < mapWidth; x++) {
						for (int y = 0; y < mapHeight; y++) {
							Coordinate current = new Coordinate(x, y, Layer.Ground);
								new FowToPolygonThread(thisThread, state, current, sight).run();
						}
					}
					System.out.println("END : " + (System.currentTimeMillis()-time));
				}
			}.start();
		} else {
			//if this tile is not inside the sightrange
			if (!sight.contains(c)) {
				//go through each adjascend tile and test for sightrange
				//0=North; 1=East; 2=South; 3=West
				for (int i = 0; i < 4; i++) {
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
					
					//if current tile adjacends the sightrange, add a line
					if (sight.contains(act)) {
						if (i == 0) {
							ret.add(new Line(c.getX()*tileSize, c.getY()*tileSize, (c.getX()+1)*tileSize, c.getY()*tileSize));
						} else if (i == 1) {
							ret.add(new Line(act.getX()*tileSize, act.getY()*tileSize, act.getX()*tileSize, (act.getY()+1)*tileSize));
						} else if (i == 2) {
							ret.add(new Line(act.getX()*tileSize, act.getY()*tileSize, (act.getX()+1)*tileSize, act.getY()*tileSize));
						} else {
							ret.add(new Line(c.getX()*tileSize, c.getY()*tileSize, c.getX()*tileSize, (c.getY()+1)*tileSize));
						}
					}
				}
			}
		}
		
		//superThread has to wait for childThreads
		if (threadsLeft > 0) {
			synchronized (this) {
				try {
					this.wait();
					System.out.println(System.currentTimeMillis()-time);
				} catch (InterruptedException e) {ErrorLogger.logger.severe(e.getMessage());}
			}
		}
		
		
		//if this is the superThread, the lines will be sorted and casted to Vector2f, then set in the state
		if (superThread == null) {
			//TODO handle NPE when there is no FoW
			
			/**
			 * List containing all polygons as Vector2f-Array
			 */
			ArrayList<Vector2f[]> polygons = new ArrayList<Vector2f[]>();
			/**
			 * List containing all Vector2f of the current polygon
			 */
			ArrayList<Vector2f> currentPolygon = new ArrayList<Vector2f>();
			Line currentLine = ret.get(0);
			
			Vector2f end = null;
			while (currentLine != null) {
				//if a new polygon has to be created, both the start and the end of the line are taken
				if (end == null) {
					currentPolygon.add(currentLine.getStart());
					end = currentLine.getEnd();
				//if new vectors have to be added to current Polygon, only the missing point is added
				//the missing point depends on wheather the last point is the start or the end of the current line
				} else if (currentLine.getStart().equals(end)) {
					end = currentLine.getEnd();
				} else if (currentLine.getEnd().equals(end)) {
					end = currentLine.getStart();
				}
				
				//the current line will be deleted from the line-ArrayList and set to null, so a new one can be chosen and selected
				ret.remove(currentLine);
				currentLine = null;
				
				//if the current point isn't already in the list, it will be added
				if (!currentPolygon.contains(end)) {
					currentPolygon.add(end);
				}
				
				
				//get a new line, which has the last added point either at the start or at the end
				Line act;
				for (int i = 0; i  < ret.size(); i++) {
					act = ret.get(i);
					if (act.getStart().equals(end) || act.getEnd().equals(end)) {
						currentLine = act;
						break;
					}
				}
				
				//if no line is found containing the last added point, the current polygon will be finished and added to the list of polygons
				//a new line will be selected and a new polygon will be calculated
				if (currentLine == null) {
					removePointsOnLine(currentPolygon);
					Vector2f[] polygon = new Vector2f[currentPolygon.size()];
					polygon = currentPolygon.toArray(polygon);
					polygons.add(polygon);
					currentPolygon = new ArrayList<Vector2f>();
					if (ret.size() > 0) {
						currentLine = ret.get(0);
					}
					end = null;
				}
			}
			
			//sysout
			for (int i = 0; i < polygons.size(); i++) {
				System.out.println(Arrays.toString(polygons.get(i)));
			}
			System.out.println("You needed " + (System.currentTimeMillis()-time) + "ms");
			
//			state.addViewrange(state.createNewModel(c), coords);
		} else {
			//cast ArrayList to Array
//			Line[] lines = new Line[ret.size()];
//			lines = ret.toArray(lines);
//			if (lines.length > 0) {
//				superThread.add(lines);
//			}
			if (ret.size() > 0) {
				superThread.add(ret);
			}
			superThread.decreaseThreadsLeft();
		}
	}
	
	/**
	 * eleminate points in between two points of a line to create max-length-lines
	 * @param al the array list to iterate through
	 */
	private void removePointsOnLine(ArrayList<Vector2f> al) {
		Vector2f act = null, last = null, preLast = null;
		for (int j = 0; j < al.size(); j++) {
			if (act == null) {
				act = al.get(0);
				last = al.get(al.size()-1);
				preLast = al.get(al.size()-2);
			} else {
				preLast = last;
				last = act;
				act = al.get(j);
			}
			
			if (act.getX() == last.getX() && act.getX() == preLast.getX()
					|| act.getY() == last.getY() && act.getY() == preLast.getY()) {
				al.remove(last);
			}
		}
	}
	
//	public synchronized void add(Line[] lines) {
//		for (Line l : lines) {
//			ret.add(l);
//		}
//	}
	
	public synchronized void add(ArrayList<Line> al) {
		for (int i = 0; i < al.size(); i++) {
			ret.add(al.get(i));
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
}