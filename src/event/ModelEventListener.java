package event;

import model.Model;
import model.map.Coordinate;

public interface ModelEventListener{
	public void modelMoved(Coordinate from, Coordinate to);
	
	public void modelAttacked(Coordinate attacker, Coordinate defender);
	
	public void modelIsBuild(Coordinate model, int x, int y, String name);
	
	public void modelAddedToBuild(Coordinate builder, Coordinate model);
	
	public void modelAdded(int x, int y, String name);
	
	public void modelRemoved(Coordinate c, Model m);
}
