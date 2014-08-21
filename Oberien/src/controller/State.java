package controller;

import java.util.ArrayList;
import java.util.HashMap;

import util.SerializableState;
import model.Model;
import model.map.Coordinate;
import model.map.Map;
import model.player.Player;

public class State {
	private final Map map;
	private Player[] players;
	
	private int currentPlayerIndex;
	private int round;
	
	private MyHashMap<Coordinate, Model> models;
	private MyHashMap<Model, Coordinate[]> viewranges;
	private MyHashMap<Model, Coordinate[]> moveranges;
	private MyHashMap<Model, Coordinate[]> fullAttackranges;
	private MyHashMap<Model, Coordinate[]> directAttackranges;
	private MyHashMap<Model, Coordinate[]> buildranges;
	
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
		viewranges = new MyHashMap<Model, Coordinate[]>();
		moveranges = new MyHashMap<Model, Coordinate[]>();
		fullAttackranges = new MyHashMap<Model, Coordinate[]>();
		directAttackranges = new MyHashMap<Model, Coordinate[]>();
		buildranges = new MyHashMap<Model, Coordinate[]>();
		playerModelList = new ArrayList<Model>();
		playerModelPositionList = new ArrayList<Coordinate>();
		allyModelPositionList = new ArrayList<Coordinate>();
		modelPositionsInSightList = new ArrayList<Coordinate>();
	}
	
	public Map getMap() {
		return map;
	}

	public Player[] getPlayers() {
		return players;
	}
	
	public void removePlayer(Player player) {
		Player[] playersNew = new Player[players.length-1];
		int i = 0;
		for (Player p : players) {
			if (!p.equals(player)) {
				playersNew[i] = p;
				i++;
			}
		}
		players = playersNew;
	}


	public int getRound() {
		return round;
	}
	public void setRound(int round) {
		this.round = round;
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

	
	public MyHashMap<Coordinate, Model> getModels() {
		return models;
	}
	public void setModels(MyHashMap<Coordinate, Model> models) {
		this.models = models;
	}
	/**
	 * @param c Coordinate of field
	 * @return Model on the field of c
	 */
	public Model getModel(Coordinate c) {
		return models.get(c);
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

	
	public MyHashMap<Model, Coordinate[]> getViewranges() {
		return viewranges;
	}
	public void setViewranges(MyHashMap<Model, Coordinate[]> viewranges) {
		this.viewranges = viewranges;
	}
	public Coordinate[] getViewrange(Coordinate c) {
		return viewranges.get(getModel(c));
	}
	public void updateViewrange(Model model, Coordinate[] neu) {
		viewranges.remove(model);
		viewranges.put(model, neu);
	}
	public void addViewrange(Model model, Coordinate[] neu) {
		viewranges.put(model, neu);
	}
	public void removeViewrange(Model model) {
		viewranges.remove(model);
	}
	
	public MyHashMap<Model, Coordinate[]> getMoveranges() {
		return moveranges;
	}
	public void setMoveranges(MyHashMap<Model, Coordinate[]> moveranges) {
		this.moveranges = moveranges;
	}
	public Coordinate[] getMoverange(Coordinate c) {
		return moveranges.get(getModel(c));
	}
	public void updateMoverange(Model model, Coordinate[] neu) {
		moveranges.remove(model);
		moveranges.put(model, neu);
	}
	public void addMoverange(Model model, Coordinate[] neu) {
		moveranges.put(model, neu);
	}
	public void removeMoverange(Model model) {
		moveranges.remove(model);
	}
	
	public MyHashMap<Model, Coordinate[]> getFullAttckranges() {
		return fullAttackranges;
	}
	public void setFullAttackranges(MyHashMap<Model, Coordinate[]> fullAttackranges) {
		this.fullAttackranges = fullAttackranges;
	}
	public Coordinate[] getFullAttackrange(Coordinate c) {
		return fullAttackranges.get(getModel(c));
	}
	public void updateFullAttackrange(Model model, Coordinate[] neu) {
		fullAttackranges.remove(model);
		fullAttackranges.put(model, neu);
	}
	public void addFullAttackrange(Model model, Coordinate[] neu) {
		fullAttackranges.put(model, neu);
	}
	public void removeFullAttackrange(Model model) {
		fullAttackranges.remove(model);
	}

	public MyHashMap<Model, Coordinate[]> getDirectAttackranges() {
		return directAttackranges;
	}
	public void setDirectAttackranges(MyHashMap<Model, Coordinate[]> directAttackranges) {
		this.directAttackranges = directAttackranges;
	}
	public Coordinate[] getDirectAttackrange(Coordinate c) {
		return directAttackranges.get(getModel(c));
	}
	public void updateDirectAttackrange(Model model, Coordinate[] neu) {
		directAttackranges.remove(model);
		directAttackranges.put(model, neu);
	}
	public void addDirectAttackrange(Model model, Coordinate[] neu) {
		directAttackranges.put(model, neu);
	}
	public void removeDirectAttackrange(Model model) {
		directAttackranges.remove(model);
	}

	public MyHashMap<Model, Coordinate[]> getBuildranges() {
		return buildranges;
	}
	public void setBuildranges(MyHashMap<Model, Coordinate[]> buildranges) {
		this.buildranges = buildranges;
	}
	public Coordinate[] getBuildrange(Coordinate c) {
		return buildranges.get(getModel(c));
	}
	public void updateBuildrange(Model model, Coordinate[] neu) {
		buildranges.remove(model);
		buildranges.put(model, neu);
	}
	public void addBuildrange(Model model, Coordinate[] neu) {
		buildranges.put(model, neu);
	}
	public void removeBuildrange(Model model) {
		buildranges.remove(model);
	}


	public Model[] getPlayerModels() {
		if (playerModels == null) {
			playerModels = new Model[playerModelList.size()];
			playerModels = playerModelList.toArray(playerModels);
		}
		return playerModels;
	}
	public void setPlayerModels(ArrayList<Model> playerModelList) {
		this.playerModelList = playerModelList;
		playerModels = null;
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
	public void setPlayerModelPositions(ArrayList<Coordinate> playerModelPositionList) {
		this.playerModelPositionList = playerModelPositionList;
		playerModelPositions = null;
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
	public void setAllyModelPositions(ArrayList<Coordinate> allyModelPositionList) {
		this.allyModelPositionList = allyModelPositionList;
		allyModelPositions = null;
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
	public void setModelPositionsInSight(ArrayList<Coordinate> modelPositionsInSightList) {
		this.modelPositionsInSightList = modelPositionsInSightList;
		modelPositionsInSight = null;
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
