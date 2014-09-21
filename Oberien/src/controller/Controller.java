package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import logger.GameLogger;
import model.AttackingModel;
import model.BuildingModel;
import model.Model;
import model.ModelList;
import model.ProducingModel;
import model.StoringModel;
import model.map.Coordinate;
import model.map.Field;
import model.map.FieldList;
import model.map.Map;
import model.map.MapList;
import model.player.Player;
import model.unit.Unit;
import util.SerializableState;
import controller.ranges.BuildRangeThread;
import controller.ranges.DirectAttackRangeThread;
import controller.ranges.FullAttackRangeThread;
import controller.ranges.MoveRangeThread;
import controller.ranges.ViewRangeThread;
import controller.wincondition.WinCondition;
import controller.wincondition.WinConditionList;
import event.ModelEventAdapter;
import event.WinEventListener;

public class Controller extends ModelEventAdapter implements WinEventListener {
	public static final int VIEWRANGE = 0;
	public static final int MOVERANGE = 1;
	public static final int FULL_ATTACKRANGE = 2;
	public static final int DIRECT_ATTACKRANGE = 3;
	public static final int BUILDRANGE = 4;
	
	private State state;
	private String winConditionName;
	
	/**
	 * Initialize Controller for a new game
	 * @param map The map which is played on
	 * @param players Array of Players playing
	 * @param winConditionName Name of the WinCondition selected, which is used to get the WinCondition-class via {@link WinConditionList#getWinCondition(String, State)}
	 */
	public Controller(Map map, Player[] players, String winConditionName) {
		GameLogger.logger.info("Initializing Controller with:");
		GameLogger.logger.info("    Map: " + map.getName());
		GameLogger.logger.info("    Players: " + Arrays.toString(players));
		GameLogger.logger.info("    WinConditionName: " + winConditionName);
		state = new State(map, players);
		state.setSight(getSight());
		WinCondition wc = WinConditionList.getWinCondition(winConditionName, state);
		wc.addWinEventListener(this);
	}
	
	/**
	 * Initialized the Controller to use a saved game
	 * @param s SerializableState loaded from a file used to get important initializing data from
	 */
	public Controller(SerializableState s) {
		GameLogger.logger.info("Initializing Controller with SerializableState with:");
		GameLogger.logger.info("    Map: " + s.getMapName());
		GameLogger.logger.info("    Players: " + Arrays.toString(s.getPlayers()));
		GameLogger.logger.info("    WinConditionName: " + s.getWinConditionName());
		GameLogger.logger.info("    currentPlayerIndex: " + s.getCurrentPlayerIndex());
		GameLogger.logger.info("    round: " + s.getRound());
		GameLogger.logger.info("    models: " + s.getModels().toString());
		winConditionName = s.getWinConditionName();
		state = new State(MapList.getInstance().getMap(s.getMapName()), s.getPlayers());
		state.setCurrentPlayerIndex(s.getCurrentPlayerIndex());
		state.setRound(s.getRound());
		state.setModels(s.getModels());
		state.setViewranges(s.getViewranges());
		state.setMoveranges(s.getMoveranges());
		state.setFullAttackranges(s.getFullAttackranges());
		state.setDirectAttackranges(s.getDirectAttackranges());
		state.setBuildranges(s.getBuildranges());
		reinitiateState();
	}
	
	/**
	 * Returns the State used for this instance of game
	 * @return State of current Game
	 */
	public State getState() {
		return state;
	}
	
	/**
	 * Creates a SerializableState from data stored in State and Controller, which can be saved via ObjectOutputStream
	 * @return SerializableState of current game
	 */
	public SerializableState getSerializableState() {
		SerializableState s = new SerializableState(state.getMap().getName(), winConditionName,
				state.getPlayers(), state.getCurrentPlayerIndex(), state.getRound(),
				state.getModels(), state.getViewranges(), state.getMoveranges(), 
				state.getFullAttckranges(), state.getDirectAttackranges(),
				state.getBuildranges());
		return s;
	}
	
	/**
	 * Moves the Model on Coordinate old to Coordinate new
	 *
	 * @param old Coordinate of Model
	 * @param neu Coordinate where the Model should move
	 * @return <ul>
	 * <li>-4 when there is already a model on that field</li>
	 * <li>-3 when the Model doesn't belong to the current palyer</li>
	 * <li>-2 when the Model's action is done</li>
	 * <li>-1 when the Model can't move</li>
	 * <li>0 when the Field where to move isn't in Moverange</li>
	 * <li>1 when Model moved successfully</li>
	 * </ul>
	 */
	public int move(Coordinate old, Coordinate neu) {
		GameLogger.logger.info("move " + old + " " + neu);
		int ret = moveInternal(old, neu);
		if (ret > 0)
			modelMoved(old, neu);
		return ret;
	}
	
	/**
	 * @see Controller#move(Coordinate, Coordinate)
	 */
	private int moveInternal(Coordinate old, Coordinate neu) {
		Model m = state.getModel(old);
		if (!(m instanceof Unit)) {
			return -1;
		}
		Unit u = (Unit) m;
		if (u.isActionDone() || u.isMoved()) {
			return -2;
		}
		if (!u.getPlayer().equals(state.getCurrentPlayer())) {
			return -3;
		}
		if (state.getModel(neu) != null) {
			return -4;
		}
		Coordinate[] c = state.getMoverange(old);
		for (int i = 0; i < c.length; i++) {
			if (c[i].equals(neu)) {
				updateModel(old, neu);
				u.setDirection(getDirectionOf(old, neu));
				u.setMoved(true);
				return 1;
			}
		}
		return 0;
	}
	
	/**
	 * Attacker damages Defender
	 *
	 * @param attacker Coordinate of attacker
	 * @param defender Coordinate of Defender
	 * @return <ul>
	 * <li>-2 when the Model's action is done</li>
	 * <li>-1 when the Model can't attack (no damage/not finished to build)</li>
	 * <li>0 when the Field where to attack isn't in Attackrange</li>
	 * <li>1 when both Models attacked</li> n 
	 * <li>2 when only the attacker attacked</li>
	 * <li>3 when the attacker attacked and the defender missed</li>
	 * <li>4 when the attacker missed but the defender attacked</li>
	 * <li>5 when both the attacker and the defender missed</li>
	 * </ul>
	 */
	public int attack(Coordinate attacker, Coordinate defender) {
		GameLogger.logger.info("attack " + attacker + " " + defender);
		int ret = attackInternal(attacker, defender);
		if (ret > 0)
			modelAttacked(attacker, defender);
		return ret;
	}
	
	/**
	 * @see Controller#attack(Coordinate, Coordinate)
	 */
	private int attackInternal(Coordinate attacker, Coordinate defender) {
		Model atk = state.getModel(attacker);
		if (!(atk instanceof AttackingModel)) {
			return -1;
		}
		AttackingModel am = (AttackingModel) atk;
		Unit au = (Unit)atk;
		if (atk.isActionDone() || atk.getTimeToBuild() != 0) {
			return -2;
		}
		boolean attackerMissed = false;
		Coordinate[] c = state.getDirectAttackrange(attacker);
		int damage;
		int strikeChance;
		int random;
		boolean survived;
		for (int i = 0; i < c.length; i++) {
			//if enemy in range
			if (c[i].equals(defender)) {
				//get models
				Model def = state.getModels().get(defender);
				
				Unit du = null;
				if (def instanceof Unit) {
					du = (Unit)def;
				}
				
				//calc and evaluate strikeChance
				strikeChance = am.getStrike() - (du == null ? 0 : du.getEvasion());
				random = (int) (Math.random() * 100);
				if (random <= strikeChance) {
					//ATTACKER
					//calculating damage
					damage = am.getDamage();
					if (am.getStrongAgainst() == def.getType()) {
						damage *= 2;
					}
					atk.setDirection(getDirectionOf(attacker, defender));
					atk.setActionDone(true);
					survived = def.damage(damage);
					if (!survived) {
						atk.levelUp();
						//if died unit isn't built yet all builders are removed from it
						if (def.getTimeToBuild() != 0) {
							Model[] builders = state.getPlayerModels();
							for (int j = 0; j < builders.length; j++) {
								if (builders[j] instanceof BuildingModel && ((BuildingModel)builders[j]).getCurrentBuilding() == def) {
									((BuildingModel)builders[j]).setCurrentBuilding(null);
								}
							}
						}
						removeModel(defender);
						return 2;
					}
				} else {
					attackerMissed = true;
				}
				c = state.getDirectAttackrange(defender);
				if (c == null) {
					return 2;
				}
				for (int j = 0; j < c.length; j++) {
					//if attacker in range
					if (c[j].equals(attacker)) {
						//DEFENDER
						AttackingModel dm = null;
						if (def instanceof AttackingModel) {
							dm = (AttackingModel) def;
						}
						if (def.getTimeToBuild() != 0 || dm == null) {
							return 2;
						}
						//strikeChance
						strikeChance = dm.getStrike() - au.getEvasion();
						random = (int) (Math.random() * 100);
						if (random > strikeChance) {
							if (attackerMissed) {
								return 5;
							}
							return 3;
						}
						//damage
						damage = dm.getDamage();
						if (dm.getStrongAgainst() == atk.getType()) {
							damage *= 2;
						}
						def.setDirection(getDirectionOf(defender, attacker));
						survived = atk.damage(damage);
						if (!survived) {
							def.levelUp();
							removeModel(attacker);
						}
						if (attackerMissed) {
							return 4;
						}
						return 1;
					}
				}
				if (!attackerMissed) {
					return 2;
				}
				return 0;
			}
		}
		return 0;
	}
	
	/**
	 * Adds an Model to the map - Only use for testing/debugging reasons To
	 * build an Model use buildModel()
	 *
	 * @param x x-Coordinate where to add
	 * @param y y-Coordinate where to add
	 * @param i ID of Model to add
	 * @return whether the Model is added or not (e.g. when on that spot there
	 * already is an Model)
	 */
	public void addModel(int x, int y, String name) {
		GameLogger.logger.info("addModel " + x + " " + y + " " + name);
		addModelInternal(x, y, name);
		modelAdded(x, y, name);
	}
	
	/**
	 * @see Controller#addModel(int, int, String)
	 */
	private void addModelInternal(int x, int y, String name) {
		Model m = ModelList.getInstance().getModel(name, state.getCurrentPlayer());
		Coordinate c = new Coordinate(x, y, m.getDefaultLayer());
		m.decreaseTimeToBuild(m.getTimeToBuild());
		addModel(c, m);
	}
	
	/**
	 * Starts building an Model
	 *
	 * @param model Model which is building
	 * @param x x-Coordinate where to build
	 * @param y y-Coordinate where to build
	 * @param i ID of Model to build
	 * @return <ul>
	 * <li>0 when there already is an Model on that spot</li>
	 * <li>1 when the Model is built</li>
	 * <li>-1 when there is not enough Money to build the Model</li>
	 * <li>-2 when there is not enough Energy to build the Model</li>
	 * <li>-3 when there is not enough Population to build the Model</li>
	 * <li>-4 when the unit to build is out of range</li>
	 * <li>-5 when the Buildingtype of model and the Type of build doesn't
	 * fit</li>
	 * </ul>
	 */
	public int buildModel(Coordinate model, int x, int y, String name) {
		GameLogger.logger.info("buildModel " + model + " " + x + " " + y + " " + name);
		int ret = buildModelInternal(model, x, y, name);
		if (ret > 0)
			modelIsBuild(model, x, y, name);
		return ret;
	}
	
	/**
	 * @see Controller#buildModel(Coordinate, int, int, String)
	 */
	private int buildModelInternal(Coordinate model, int x, int y, String name) {
		Model b = ModelList.getInstance().getModel(name, state.getCurrentPlayer());
		Model m = state.getModel(model);
		Coordinate build = new Coordinate(x, y, b.getDefaultLayer());
		if (!state.getModels().containsKey(build)) {
			if (m instanceof BuildingModel && ((BuildingModel)m).getBuilds() != b.getType()) {
				return -5;
			}
			Coordinate[] range = state.getBuildrange(model);
			for (int j = 0; j < range.length; j++) {
				if (range[j].equals(build)) {
					if (state.getCurrentPlayer().getMoney() < b.getCostMoney()) {
						return -1;
					}
					if (state.getCurrentPlayer().getEnergy() < b.getCostEnergy()) {
						return -2;
					}
					if (state.getCurrentPlayer().getPopulation() < b.getCostPopulation()) {
						return -3;
					}
					state.getCurrentPlayer().useMoney(b.getCostMoney());
					state.getCurrentPlayer().useEnergy(b.getCostEnergy());
					state.getCurrentPlayer().usePopulation(b.getCostPopulation());
					addModel(build, b);
					((BuildingModel)m).setCurrentBuilding(b);
					return 1;
				}
			}
			return -4;
		}
		return 0;
	}
	
	/**
	 * Adds an Model to build another model
	 *
	 * @param builder Model willing to build
	 * @param model Model that will be built
	 * @return whether model is added to build (1) or not (0) (when model can't build
	 * Type of build / build is already finished / action is done)
	 */
	public int addModelToBuild(Coordinate builder, Coordinate model) {
		GameLogger.logger.info("addModelToBuild " + builder + " " + model);
		int ret = addModelToBuildInternal(builder, model);
		if (ret > 0)
			modelAddedToBuild(builder, model);
		return ret;
	}
	
	/**
	 * @see Controller#addModelToBuild(Coordinate, Coordinate)
	 */
	private int addModelToBuildInternal(Coordinate builder, Coordinate model) {
		Model m = state.getModel(builder);
		Model b = state.getModel(model);
		if (b.getTimeToBuild() == 0 || !(m instanceof BuildingModel) || ((BuildingModel)m).getBuilds() != b.getType() || m.isActionDone()) {
			return 0;
		}
		((BuildingModel)m).setCurrentBuilding(b);
		return 1;
	}
	
	/**
	 * Sets a Model's action to be finished. This Model can't be selected anymore this turn
	 * @param c Coordinate the Model is standing on
	 */
	public void setActionDone(Coordinate c) {
		GameLogger.logger.info("setActionDone " + c);
		state.getModel(c).setActionDone(true);
	}
	
	/**
	 * Returns the Viewrange of all allied Models
	 *
	 * @return Coordinate-array of all fields in viewrange
	 */
	private Coordinate[] getSight() {
		if (state.getRound() == 0) {
			return state.getMap().getStartAreaOfTeam(state.getCurrentPlayer().getTeam());
		}
		
		ArrayList<Coordinate> ret = new ArrayList<Coordinate>();
		MyHashMap<Model, Coordinate[]> viewrange = state.getViewranges();
		Object[] keys = viewrange.keySet().toArray();
		for (Object o : keys) {
			Model m = (Model) o;
			if (m.getPlayer().equals(state.getCurrentPlayer())) {
				Coordinate[] coords = (Coordinate[]) viewrange.get(m);
				for (Coordinate c : coords) {
					ret.add(c);
				}
			}
		}
		removeDuplicates(ret);
		Coordinate[] retur = new Coordinate[ret.size()];
		retur = ret.toArray(retur);
		return retur;
	}
	
	/**
	 * Ends the turn.<br/>
	 * Calculates the building status of Models which are built at the moment.<br/>
	 * Resets every model's action.<br/>
	 * Calculates new Money and Energy of the following player.
	 */
	public void endTurn() {
		GameLogger.logger.info("endTurn");
		//START finish up current player
		Model[] models = state.getPlayerModels();
		for (Model m : models) {
			m.setActionDone(false);
			if (m instanceof Unit) {
				Unit u = (Unit) m;
				u.setMoved(false);
			}
			if (m.getTimeToBuild() == 0) {
				if (m instanceof BuildingModel) {
					BuildingModel b = (BuildingModel) m;
					if (b.getCurrentBuilding() != null) {
						b.getCurrentBuilding().decreaseTimeToBuild(b.getBuildSpeed());
						if (b.getCurrentBuilding().getTimeToBuild() == 0) {
							b.setCurrentBuilding(null);
						} else {
							m.setActionDone(true);
						}
					}
				}
			}
		}
		//END
		
		//START set new Player
		int currentPlayerIndex = state.getCurrentPlayerIndex();
		if (currentPlayerIndex == state.getPlayers().length - 1) {
			state.increaseRound();
			state.setCurrentPlayerIndex(0);
		} else {
			state.setCurrentPlayerIndex(currentPlayerIndex+1);
		}
		//END
		
		//START reinitiate State
		reinitiateState();
//		new FowToPolygonThread(null, state, null, null).start();
		
		//END
		
		//START calculate new player's stats
		if (state.getRound() != 0) {
			models = state.getPlayerModels();
			int storage = 0;
			int populationStorage = 0;
			for (Model m : models) {
				if (m.getTimeToBuild() == 0) {
					//normal buildings
					if (m instanceof ProducingModel) {
						ProducingModel pm = (ProducingModel) m;
						state.getCurrentPlayer().addMoney(pm.getProducingMoney());
						state.getCurrentPlayer().addEnergy(pm.getProducingEnergy());
						state.getCurrentPlayer().addPopulation(pm.getProducingPopulation());
					}
					if (m instanceof StoringModel) {
						StoringModel sm = (StoringModel) m;
						storage += sm.getStoragePlus();
						populationStorage += sm.getPopulationStoragePlus();
					}
				}
			}
			state.getCurrentPlayer().setStorage(storage);
			state.getCurrentPlayer().setPopulationStorage(populationStorage);
		}
		//END
	}
	
	/**
	 * Reinitializes State after a game has been loaded (SerializableState) and Controller has been constructed.
	 */
	private void reinitiateState() {
		MyHashMap<Coordinate, Model> modelMap = state.getModels();
		
		ArrayList<Model> playerModelList = new ArrayList<Model>();
		ArrayList<Coordinate> playerModelPositionList = new ArrayList<Coordinate>();
		ArrayList<Coordinate> allyModelPositionList = new ArrayList<Coordinate>();
		Object[] keys = modelMap.keySet().toArray();
		for (Object o : keys) {
			Coordinate c = (Coordinate) o;
			Model m = modelMap.get(c);
			if (m.getPlayer().equals(state.getCurrentPlayer())) {
				playerModelList.add(m);
				playerModelPositionList.add(c);
			}
			if (m.getPlayer().getTeam() == state.getCurrentPlayer().getTeam()) {
				allyModelPositionList.add(c);
			}
		}
		state.setPlayerModels(playerModelList);
		state.setPlayerModelPositions(playerModelPositionList);
		state.setAllyModelPositions(allyModelPositionList);
		state.setSight(getSight());
		ArrayList<Coordinate> modelPositionsInSightList = new ArrayList<Coordinate>(Arrays.asList(state.getModelPositionsInArea(state.getSight())));
		state.setModelPositionsInSight(modelPositionsInSightList);
	}
	
	/**
	 * Updates all references to the model, which is moving (Ranges, ModelPositions).<br/>
	 * Removes current references from State and calculates the new ones, adding them afterwards to State.
	 * @param from Coordinate the Model was standing on
	 * @param to Coordinate the model is moving to
	 * @return <ul>	<li>true if all references are being updated correctly</li>
	 * 				<li>false if the coordinate the unit wants to move to isn't free</li></li>
	 */
	private boolean updateModel(final Coordinate from, final Coordinate to) {
		if (state.getModels().containsKey(to)) {
			return false;
		}
		new Thread() {
			public void run() {
				Model model = state.getModel(from);
				state.updateModel(from, to);
				Field actField = FieldList.getInstance().get(state.getMap().get(to.getX(), to.getY()));
				
				new ViewRangeThread(null, state, to, model.getViewRange() + actField.getViewPlus(), new MyHashMap<Coordinate, Integer>(), false).start();
				if (model instanceof Unit) {
					new MoveRangeThread(null, state, to, model, ((Unit)model).getMovespeed(), new MyHashMap<Coordinate, Integer>(), false).start();
				}
				if (model instanceof AttackingModel) {
					new DirectAttackRangeThread(null, state, to, ((AttackingModel)model).getAttackRange() + actField.getActionPlus(), new MyHashMap<Coordinate, Integer>(), false).start();
					if (model instanceof Unit) {
						new FullAttackRangeThread(null, state, to, ((AttackingModel)model).getAttackRange() + actField.getActionPlus(), new MyHashMap<Coordinate, Integer>(), false).start();
					}
				} 
				if (model instanceof BuildingModel) {
					new BuildRangeThread(null, state, to, ((BuildingModel)model).getBuildRange() + actField.getActionPlus(), new MyHashMap<Coordinate, Integer>(), false).start();
				}
				if (model.getPlayer().equals(state.getCurrentPlayer())) {
					state.updatePlayerModelPosition(from, to);
				}
				if (model.getPlayer().getTeam() == state.getCurrentPlayer().getTeam()) {
					state.updateAllyModelPosition(from, to);
				}
				state.setSight(getSight());
//				new FowToPolygonThread(null, state, null, null).start();
				Coordinate[] sight = state.getSight();
				for (int i = 0; i < sight.length; i++) {
					if (sight[i].equals(to)) {
						state.updateModelPositionInSight(from, to);
						break;
					}
				}
			}
		}.start();
		return true;
	}
	
	/**
	 * Adds a model to the given coordinate and calculates all references. The Model is not built yet.
	 * @param c Coordinate the Model has to be added to
	 * @param model Model to be added
	 * @return <ul>	<li>true if the model is added and all references are being updated correctly</li>
	 * 				<li>false if the coordinate the unit is being added to isn't free</li></li>
	 */
	private boolean addModel(final Coordinate c, final Model model) {
		if (state.getModels().containsKey(c)) {
			return false;
		}
		new Thread() {
			public void run() {
				state.addModel(c, model);
				Field actField = FieldList.getInstance().get(state.getMap().get(c.getX(), c.getY()));
				
				new ViewRangeThread(null, state, c, model.getViewRange() + actField.getViewPlus(), new MyHashMap<Coordinate, Integer>(), true).start();
				if (model instanceof Unit) {
					new MoveRangeThread(null, state, c, model, ((Unit)model).getMovespeed(), new MyHashMap<Coordinate, Integer>(), true).start();
				}
				if (model instanceof AttackingModel) {
					new DirectAttackRangeThread(null, state, c, ((AttackingModel)model).getAttackRange() + actField.getActionPlus(), new MyHashMap<Coordinate, Integer>(), true).start();
					if (model instanceof Unit) {
						new FullAttackRangeThread(null, state, c, ((AttackingModel)model).getAttackRange() + actField.getActionPlus(), new MyHashMap<Coordinate, Integer>(), true).start();
					}
				} 
				if (model instanceof BuildingModel) {
					new BuildRangeThread(null, state, c, ((BuildingModel)model).getBuildRange() + actField.getActionPlus(), new MyHashMap<Coordinate, Integer>(), true).start();
				}
				if (model.getPlayer().equals(state.getCurrentPlayer())) {
					state.addPlayerModel(model);
					state.addPlayerModelPosition(c);
				}
				if (model.getPlayer().getTeam() == state.getCurrentPlayer().getTeam()) {
					state.addAllyModelPosition(c);
				}
				Coordinate[] sight = state.getSight();
				for (int i = 0; i < sight.length; i++) {
					if (sight[i].equals(c)) {
						state.addModelPositionInSight(c);
						break;
					}
				}
				state.setSight(getSight());
//				new FowToPolygonThread(null, state, null, null).start();
			}
		}.start();
		return true;
	}
	
	/**
	 * Removes a model and all it's references from the game.
	 * @param c Coordinate the Model being removed is standing on.
	 */
	public void removeModel(final Coordinate c) {
		GameLogger.logger.info("removeModel " + c);
		new Thread() {
			public void run() {
				Model model = state.getModel(c);
				state.removeModel(c);
				state.removeViewrange(model);
				if (model instanceof Unit) {
					state.removeMoverange(model);
				}
				if (model instanceof AttackingModel) {
					state.removeFullAttackrange(model);
					state.removeDirectAttackrange(model);
				} 
				if (model instanceof BuildingModel) {
					state.removeBuildrange(model);
				}
				if (model.getPlayer().equals(state.getCurrentPlayer())) {
					state.removePlayerModel(model);
					state.removePlayerModelPosition(c);
				}
				if (model.getPlayer().getTeam() == state.getCurrentPlayer().getTeam()) {
					state.removeAllyModelPosition(c);
				}
				Coordinate[] sight = state.getSight();
				for (int i = 0; i < sight.length; i++) {
					if (sight[i].equals(c)) {
						state.removeModelPositionInSight(c);
						break;
					}
				}
				state.setSight(getSight());
//				new FowToPolygonThread(null, state, null, null).start();
				modelRemoved(c, model);
			}
		}.start();
	}
	
	/**
	 * Calculates the direction a model is looking after having moved
	 * @param from Coordinate the Model is coming form
	 * @param to Coordinate the Model is moving to
	 * @return the new direction the model has to be facing (0=NORTH, 1=EAST, 2=SOUTH, 3=WEST)
	 */
	public int getDirectionOf(Coordinate from, Coordinate to) {
		if (from == null || to == null) {
			return -1;
		}
		int dx = to.getX() - from.getX();
		int dy = to.getY() - from.getY();
		if (Math.abs(dx) >= Math.abs(dy)) {
			if (dx > 0) {
				return 1;
			} else {
				return 3;
			}
		} else {
			if (dy > 0) {
				return 2;
			} else {
				return 0;
			}
		}
	}
	
	/**
	 * Removes all duplicates of an ArrayList of Coordinates, which can be used to clean a list after ranges (e.g. sight) have been calculated.<br/>
	 * This algorithm sorts the list first. After that it goes through the list once, removing an entry if it equalsthe entry before.
	 * @param list List with duplicates to be removed
	 */
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
	
	/**
	 * This method is called when a player has won. It notifies the view and closes the game.
	 */
	@Override
	public void hasWon(Player p) {
		System.out.println("Player won: " + p);
		//TODO add interface to VIEW
		//TODO Finish game
	}
	
	/**
	 * This method is called whenever a player has lost according to the WinCondition. It notifies the view and removes that player from the game.
	 */
	@Override
	public void hasLost(Player p) {
		System.out.println("Player lost: " + p);
		removePlayer(p);
		if (p.equals(state.getCurrentPlayer()))
			endTurn();
		//TODO add interface to VIEW
	}
	
	/**
	 * This method removes a player from the game removing each of it's models.
	 * @param player
	 */
	private void removePlayer(Player player) {
		MyHashMap<Coordinate, Model> models = state.getModels();
		Object[] keys = models.keySet().toArray();
		state.removePlayer(player);
		for (Object o : keys) {
			if (models.get(o).getPlayer().equals(player)) {
				removeModel((Coordinate)o);
			}
		}
	}
}
