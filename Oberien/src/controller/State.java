package controller;

import java.util.ArrayList;
import java.util.HashMap;

import model.Model;
import model.Player;
import model.map.Coordinate;
import model.map.Map;

public class State {
	private final Map map;
	private final Player[] players;
	
	private int currentPlayerIndex;
	private int round;
	
	private MyHashMap<Coordinate, Model> models;
	private HashMap<Model, Coordinate[]> viewrange;
	private HashMap<Model, Coordinate[]> moverange;
	private HashMap<Model, Coordinate[]> fullAttackrange;
	private HashMap<Model, Coordinate[]> directAttackrange;
	private HashMap<Model, Coordinate[]> buildrange;
	
	private ArrayList<Model> playerModelList;
	private Model[] playerModels;
	private ArrayList<Coordinate> playerModelPositionList;
	private Coordinate[] playerModelPositions;
	private ArrayList<Coordinate> allyModelPositionList;
	private Coordinate[] allyModelPositions;
	private ArrayList<Coordinate> modelPositionsInSightList;
	private Coordinate[] modelPositionsInSight;
	
	private Coordinate[] sight;
	
	public State(Map map, Player[] players) {
		this.map = map;
		this.players = players;
		
		models = new MyHashMap<Coordinate, Model>();
		viewrange = new HashMap<Model, Coordinate[]>();
		moverange = new HashMap<Model, Coordinate[]>();
		fullAttackrange = new HashMap<Model, Coordinate[]>();
		directAttackrange = new HashMap<Model, Coordinate[]>();
		buildrange = new HashMap<Model, Coordinate[]>();
		playerModelList = new ArrayList<Model>();
		playerModelPositionList = new ArrayList<Coordinate>();
		allyModelPositionList = new ArrayList<Coordinate>();
		modelPositionsInSightList = new ArrayList<Coordinate>();
	}
	
	public void reinitiate(ArrayList<Model> playerModelList,
			ArrayList<Coordinate> playerModelPositionList,
			ArrayList<Coordinate> allyModelPositionList,
			ArrayList<Coordinate> modelPositionsInSightList) {
		this.playerModelList = playerModelList;
		playerModels = null;
		this.playerModelPositionList = playerModelPositionList;
		playerModelPositions = null;
		this.allyModelPositionList = allyModelPositionList;
		allyModelPositions = null;
		this.modelPositionsInSightList = modelPositionsInSightList;
		modelPositionsInSight = null;
	}
	
	public Map getMap() {
		return map;
	}

	public Player[] getPlayers() {
		return players;
	}


	public int getRound() {
		return round;
	}
	public void increaseRound() {
		round++;
	}
	
	/**
	 * Returns all Modelpositons (allies+enemies) in the given area
	 *
	 * @param c Coordinate-array of area
	 * @return Coordinate-array of all models in area
	 */
	public Coordinate[] getModelPositionsInArea(Coordinate[] c) {
		ArrayList<Coordinate> ret = new ArrayList<Coordinate>();
		for (int i = 0; i < c.length; i++) {
			Model model = getModel(c[i]);
			if (model != null) {
				ret.add(c[i]);
			}
		}
		Coordinate[] retur = new Coordinate[ret.size()];
		retur = ret.toArray(retur);
		return retur;
	}
	
	/**
	 * Returns all Modelpositions (allies+enemies) in the given area
	 *
	 * @param c Coordinate-array of area
	 * @return Coordinate-array of all models in area
	 */
	public Coordinate[] getEnemyModelPositionsInArea(Coordinate[] c) {
		ArrayList<Coordinate> ret = new ArrayList<Coordinate>();
		for (int i = 0; i < c.length; i++) {
			Model model = getModel(c[i]);
			if (model != null && model.getPlayer().getTeam() != getCurrentPlayer().getTeam()) {
				ret.add(c[i]);
			}
		}
		Coordinate[] retur = new Coordinate[ret.size()];
		retur = ret.toArray(retur);
		return retur;
	}


	/**
	 *
	 * @param c Coordinate of field
	 * @return Model on the field of c
	 */
	public Model getModel(Coordinate c) {
		return models.get(c);
	}
	public MyHashMap<Coordinate, Model> getModels() {
		return models;
	}
	public void updateModel(Coordinate from, Coordinate to) {
		Model m = getModel(from);
		models.remove(from);
		models.put(to, m);
	}
	public void addModel(Coordinate c, Model model) {
		models.put(c, model);
	}
	public void removeModel(Coordinate c) {
		models.remove(c);
	}

	
	public HashMap<Model, Coordinate[]> getViewranges() {
		return viewrange;
	}
	public Coordinate[] getViewrange(Coordinate c) {
		return viewrange.get(getModel(c));
	}
	public void updateViewrange(Model model, Coordinate[] neu) {
		viewrange.remove(model);
		viewrange.put(model, neu);
	}
	public void addViewrange(Model model, Coordinate[] neu) {
		viewrange.put(model, neu);
	}
	public void removeViewrange(Model model) {
		viewrange.remove(model);
	}

	public Coordinate[] getMoverange(Coordinate c) {
		return moverange.get(getModel(c));
	}
	public void updateMoverange(Model model, Coordinate[] neu) {
		moverange.remove(model);
		moverange.put(model, neu);
	}
	public void addMoverange(Model model, Coordinate[] neu) {
		moverange.put(model, neu);
	}
	public void removeMoverange(Model model) {
		moverange.remove(model);
	}

	public Coordinate[] getFullAttackrange(Coordinate c) {
		return fullAttackrange.get(getModel(c));
	}
	public void updateFullAttackrange(Model model, Coordinate[] neu) {
		fullAttackrange.remove(model);
		fullAttackrange.put(model, neu);
	}
	public void addFullAttackrange(Model model, Coordinate[] neu) {
		fullAttackrange.put(model, neu);
	}
	public void removeFullAttackrange(Model model) {
		fullAttackrange.remove(model);
	}


	public Coordinate[] getDirectAttackrange(Coordinate c) {
		return directAttackrange.get(getModel(c));
	}
	public void updateDirectAttackrange(Model model, Coordinate[] neu) {
		directAttackrange.remove(model);
		directAttackrange.put(model, neu);
	}
	public void addDirectAttackrange(Model model, Coordinate[] neu) {
		directAttackrange.put(model, neu);
	}
	public void removeDirectAttackrange(Model model) {
		directAttackrange.remove(model);
	}


	public Coordinate[] getBuildrange(Coordinate c) {
		return buildrange.get(getModel(c));
	}
	public void updateBuildrange(Model model, Coordinate[] neu) {
		buildrange.remove(model);
		buildrange.put(model, neu);
	}
	public void addBuildrange(Model model, Coordinate[] neu) {
		buildrange.put(model, neu);
	}
	public void removeBuildrange(Model model) {
		buildrange.remove(model);
	}


	public Model[] getPlayerModels() {
		if (playerModels == null) {
			playerModels = new Model[playerModelList.size()];
			playerModels = playerModelList.toArray(playerModels);
		}
		return playerModels;
	}
	public void addPlayerModel(Model model) {
		playerModelList.add(model);
		playerModels = null;
	}
	public void removePlayerModel(Model model) {
		playerModelList.remove(model);
		playerModels = null;
	}


	public Coordinate[] getPlayerModelPositions() {
		if (playerModelPositions == null) {
			playerModelPositions = new Coordinate[playerModelPositionList.size()];
			playerModelPositions = playerModelList.toArray(playerModelPositions);
		}
		return playerModelPositions;
	}
	public void updatePlayerModelPosition(Coordinate from, Coordinate to) {
		playerModelPositionList.remove(from);
		playerModelPositionList.add(to);
	}
	public void addPlayerModelPosition(Coordinate c) {
		playerModelPositionList.add(c);
		playerModelPositions = null;
	}
	public void removePlayerModelPosition(Coordinate c) {
		playerModelPositionList.remove(c);
		playerModelPositions = null;
	}


	public Coordinate[] getAllyModelPositions() {
		if (allyModelPositions == null) {
			allyModelPositions = new Coordinate[allyModelPositionList.size()];
			allyModelPositions = allyModelPositionList.toArray(allyModelPositions);
		}
		return allyModelPositions;
	}
	public void updateAllyModelPosition(Coordinate from, Coordinate to) {
		allyModelPositionList.remove(from);
		allyModelPositionList.add(to);
	}
	public void addAllyModelPosition(Coordinate c) {
		allyModelPositionList.add(c);
		allyModelPositions = null;
	}
	public void removeAllyModelPosition(Coordinate c) {
		allyModelPositionList.remove(c);
		allyModelPositions = null;
	}


	public Coordinate[] getModelPositionsInSight() {
		if (modelPositionsInSight == null) {
			modelPositionsInSight = new Coordinate[modelPositionsInSightList.size()];
			modelPositionsInSight = modelPositionsInSightList.toArray(modelPositionsInSight);
		}
		return modelPositionsInSight;
	}
	public void updateModelPositionInSight(Coordinate from, Coordinate to) {
		modelPositionsInSightList.remove(from);
		modelPositionsInSightList.add(to);
	}
	public void addModelPositionInSight(Coordinate c) {
		modelPositionsInSightList.add(c);
		modelPositionsInSight = null;
	}
	public void removeModelPositionInSight(Coordinate c) {
		modelPositionsInSightList.remove(c);
		modelPositionsInSight = null;
	}
	
	public Coordinate[] getSight() {
		return sight;
	}
	public void setSight(Coordinate[] sight) {
		this.sight = sight;
	}

	public Player getCurrentPlayer() {
		return players[currentPlayerIndex];
	}
	public int getCurrentPlayerIndex() {
		return currentPlayerIndex;
	}
	public void setCurrentPlayerIndex(int currentPlayerIndex) {
		this.currentPlayerIndex = currentPlayerIndex;
	}
}
