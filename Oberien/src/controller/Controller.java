package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import controller.ranges.BuildRangeThread;
import controller.ranges.DirectAttackRangeThread;
import controller.ranges.FullAttackRangeThread;
import controller.ranges.MoveRangeThread;
import controller.ranges.ViewRangeThread;

import model.map.Coordinate;
import model.map.FieldList;
import model.unit.*;
import model.building.base.Base;
import model.building.resourceCollector.ResourceCollector;
import model.building.storage.Storage;
import model.*;

public class Controller {
	public static final int VIEWRANGE = 0;
	public static final int MOVERANGE = 1;
	public static final int FULL_ATTACKRANGE = 2;
	public static final int DIRECT_ATTACKRANGE = 3;
	public static final int BUILDRANGE = 4;
	
	private State state;

	public Controller(State state) {
		this.state = state;
		state.setSight(getSight());
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
		Model m = state.getModel(attacker);
		if (!(m instanceof AttackingModel)) {
			return -1;
		}
		AttackingModel am = (AttackingModel) m;
		Unit au = (Unit)m;
		if (m.isActionDone() || m.getTimeToBuild() != 0) {
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
					m.setDirection(getDirectionOf(attacker, defender));
					m.setActionDone(true);
					survived = def.damage(damage);
					if (!survived) {
						m.levelUp();
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
						if (dm.getStrongAgainst() == m.getType()) {
							damage *= 2;
						}
						def.setDirection(getDirectionOf(defender, attacker));
						survived = m.damage(damage);
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

	public void actionDone(Coordinate c) {
		state.getModel(c).setActionDone(true);
	}

	public void moved(Coordinate c) {
		Model m = state.getModel(c);
		if (m instanceof Unit) {
			((Unit)m).setMoved(true);
		}
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
		Model m = ModelList.getInstance().getModel(name, state.getCurrentPlayer());
		Coordinate c = new Coordinate(x, y, m.getDefaultLayer());
		System.out.println(c);
		m.decreaseTimeToBuild(m.getTimeToBuild());
		System.out.println(addModel(c, m));
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
	 * @param model Model willing to build
	 * @param build Model that will be built
	 * @return whether model is added to build or not (when model can't build
	 * Type of build / build is already finished / action is done)
	 */
	public boolean addModelToBuild(Coordinate model, Coordinate build) {
		Model m = state.getModel(model);
		Model b = state.getModel(build);
		if (b.getTimeToBuild() == 0 || !(m instanceof BuildingModel) || ((BuildingModel)m).getBuilds() != b.getType() || m.isActionDone()) {
			return false;
		}
		((BuildingModel)m).setCurrentBuilding(b);
		return true;
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
		HashMap<Model, Coordinate[]> viewrange = state.getViewranges();
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
	 *
	 * @param c Coordinate where the unit is on
	 * @param model Model wanting to move
	 * @param maxRange the maximum range to move, usually unit.getMovespeed()
	 * @return Coordinate-Array of all fields that the unit can move or <b>null</b> if the model can't move.
	 */
	private Coordinate[] getMoveRange(Coordinate c, Model model, int maxRange) {
		if (!(model instanceof Unit)) {
			return null;
		}
		Unit u = (Unit)model;
		
		ArrayList<Coordinate> ret = new ArrayList<Coordinate>();
		ret.add(c);
		//0=North; 1=East; 2=South; 3=West
		for (int i = 0; i < 4; i++) {
			int move = maxRange;
			Coordinate act;
			for (int j = 1; move > 0; j++) {
				if (i == 0) {
					act = new Coordinate(c.getX(), c.getY() - j, Layer.Ground);
				} else if (i == 1) {
					act = new Coordinate(c.getX() + j, c.getY(), Layer.Ground);
				} else if (i == 2) {
					act = new Coordinate(c.getX(), c.getY() + j, Layer.Ground);
				} else {
					act = new Coordinate(c.getX() - j, c.getY(), Layer.Ground);
				}
				byte b = state.getMap().get(act.getX(), act.getY());
				if (b < 0) {
					break;
				}
				if (u.canPass(b)) {
					move -= FieldList.getInstance().get(b).getMovespeed();
					if (move >= 0) {
						ret.add(act);
						if (move != 0) {
							Coordinate[] sub = getMoveRange(act, u, move);
							for (Coordinate coord : sub) {
								ret.add(coord);
							}
						}
					}
				} else {
					break;
				}
			}
		}
		removeDuplicates(ret);
		Coordinate[] retur = new Coordinate[ret.size()];
		retur = ret.toArray(retur);
		return retur;
	}

	/**
	 * @param c Coordinate where the unit is on
	 * @param maxRange the maximum range to see, usually unit.getViewrange()
	 * @return Coordinate-Array of all fields that the unit can view
	 */
	private Coordinate[] getViewRange(Coordinate c, int viewRange) {
		ArrayList<Coordinate> ret = new ArrayList<Coordinate>();
		ret.add(c);
		//0=North; 1=East; 2=South; 3=West
		for (int i = 0; i < 4; i++) {
			int view = viewRange;
			Coordinate act;
			for (int j = 1; view > 0; j++) {
				if (i == 0) {
					act = new Coordinate(c.getX(), c.getY() - j, Layer.Ground);
				} else if (i == 1) {
					act = new Coordinate(c.getX() + j, c.getY(), Layer.Ground);
				} else if (i == 2) {
					act = new Coordinate(c.getX(), c.getY() + j, Layer.Ground);
				} else {
					act = new Coordinate(c.getX() - j, c.getY(), Layer.Ground);
				}
				byte b = state.getMap().get(act.getX(), act.getY());
				if (b < 0) {
					break;
				}
				view -= FieldList.getInstance().get(b).getViewrange();
				if (view >= 0) {
					ret.add(act);
					if (view != 0) {
						Coordinate[] sub = getViewRange(act, view);
						for (Coordinate coord : sub) {
							ret.add(coord);
						}
					}
				} else {
					break;
				}
			}
		}

		removeDuplicates(ret);
		Coordinate[] coords = new Coordinate[ret.size()];
		coords = ret.toArray(coords);
		return coords;
	}

	/**
	 * @param c Coordinate where the unit is on
	 * @param maxRange the maximum range of attack, usually
	 * unit.getAttackrange()
	 * @return Coordinate-Array of all fields that the unit can attack
	 */
	private Coordinate[] getAttackRange(Coordinate c, int maxRange) {
		ArrayList<Coordinate> ret = new ArrayList<Coordinate>();
		//0=North; 1=East; 2=South; 3=West
		for (int i = 0; i < 4; i++) {
			int attack = maxRange;
			Coordinate act;
			for (int j = 1; attack > 0; j++) {
				if (i == 0) {
					act = new Coordinate(c.getX(), c.getY() - j, Layer.Ground);
				} else if (i == 1) {
					act = new Coordinate(c.getX() + j, c.getY(), Layer.Ground);
				} else if (i == 2) {
					act = new Coordinate(c.getX(), c.getY() + j, Layer.Ground);
				} else {
					act = new Coordinate(c.getX() - j, c.getY(), Layer.Ground);
				}
				byte b = state.getMap().get(act.getX(), act.getY());
				if (b < 0) {
					break;
				}
				attack -= FieldList.getInstance().get(b).getAttackrange();
				ret.add(act);
				if (attack > 0) {
					Coordinate[] sub = getAttackRange(act, attack);
					for (Coordinate coord : sub) {
						ret.add(coord);
					}
				}
			}
		}
		removeDuplicates(ret);
		Coordinate[] retur = new Coordinate[ret.size()];
		retur = ret.toArray(retur);
		return retur;
	}

	/**
	 * @param c Coordinate where the unit is on
	 * @param maxRange the maximum range of build, usually unit.getBuildRange()
	 * @return Coordinate-Array of all fields that the unit can build on or <b>null</b> if it can't build
	 */
	private Coordinate[] getBuildRange(Coordinate c, int maxRange) {
		ArrayList<Coordinate> ret = new ArrayList<Coordinate>();
		ret.add(c);
		//0=North; 1=East; 2=South; 3=West
		for (int i = 0; i < 4; i++) {
			int build = maxRange;
			Coordinate act;
			for (int j = 1; build > 0; j++) {
				if (i == 0) {
					act = new Coordinate(c.getX(), c.getY() - j, Layer.Ground);
				} else if (i == 1) {
					act = new Coordinate(c.getX() + j, c.getY(), Layer.Ground);
				} else if (i == 2) {
					act = new Coordinate(c.getX(), c.getY() + j, Layer.Ground);
				} else {
					act = new Coordinate(c.getX() - j, c.getY(), Layer.Ground);
				}
				byte b = state.getMap().get(act.getX(), act.getY());
				if (b < 0) {
					break;
				}
				build -= FieldList.getInstance().get(b).getAttackrange();
				if (build >= 0) {
					ret.add(act);
					if (build != 0) {
						Coordinate[] sub = getBuildRange(act, build);
						for (Coordinate coord : sub) {
							ret.add(coord);
						}
					}
				}
			}
		}
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

		removeDuplicates(ret);
		Coordinate[] retur = new Coordinate[ret.size()];
		retur = ret.toArray(retur);
		return retur;
	}

	public int getRound() {
		return state.getRound();
	}

	public void endTurn() {
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
		ArrayList<Model> playerModelList = new ArrayList<Model>();
		ArrayList<Coordinate> playerModelPositionList = new ArrayList<Coordinate>();
		ArrayList<Coordinate> allyModelPositionList = new ArrayList<Coordinate>();
		MyHashMap<Coordinate, Model> modelMap = state.getModels();
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
		ArrayList<Coordinate> modelPositionsInSightList = new ArrayList<Coordinate>(Arrays.asList(state.getModelPositionsInArea(state.getSight())));
		state.reinitiate(playerModelList, playerModelPositionList, allyModelPositionList, modelPositionsInSightList);
		state.setSight(getSight());
		//END
		
		//START calculate new player's stats
		if (state.getRound() != 0) {
			models = state.getPlayerModels();
			int storage = 0;
			int populationStorage = 0;
			for (Model m : models) {
				if (m.getTimeToBuild() == 0) {
					//normal buildings
					if (m instanceof ResourceCollector) {
						ResourceCollector rc = (ResourceCollector) m;
						state.getCurrentPlayer().addMoney(rc.getProducingMoney());
						state.getCurrentPlayer().addEnergy(rc.getProducingEnergy());
						state.getCurrentPlayer().addPopulation(rc.getProducingPopulation());
					}
					if (m instanceof Storage) {
						Storage s = (Storage) m;
						storage += s.getStoragePlus();
						populationStorage += s.getPopulationStoragePlus();
					}
					//BASE
					if (m instanceof Base) {
						Base b = (Base) m;
						state.getCurrentPlayer().addMoney(b.getProducingMoney());
						state.getCurrentPlayer().addEnergy(b.getProducingEnergy());
						state.getCurrentPlayer().addPopulation(b.getProducingPopulation());
						storage += b.getStoragePlus();
						populationStorage += b.getPopulationStoragePlus();
					}
				}
			}
			state.getCurrentPlayer().setStorage(storage);
			state.getCurrentPlayer().setPopulationStorage(populationStorage);
		}
		//END
	}
	
	private boolean updateModel(final Coordinate from, final Coordinate to) {
		if (state.getModels().containsKey(to)) {
			return false;
		}
		new Thread() {
			public void run() {
				Model model = state.getModel(from);
				state.updateModel(from, to);
				new ViewRangeThread(null, state, to, model.getViewrange(), new MyHashMap<Coordinate, Integer>(), false).start();
				if (model instanceof Unit) {
					new MoveRangeThread(null, state, to, model, ((Unit)model).getMovespeed(), new MyHashMap<Coordinate, Integer>(), false).start();
				}
				if (model instanceof AttackingModel) {
					new DirectAttackRangeThread(null, state, to, ((AttackingModel)model).getAttackrange(), new MyHashMap<Coordinate, Integer>(), false).start();
					if (model instanceof Unit) {
						new FullAttackRangeThread(null, state, to, ((AttackingModel)model).getAttackrange(), new MyHashMap<Coordinate, Integer>(), false).start();
					}
				} 
				if (model instanceof BuildingModel) {
					new BuildRangeThread(null, state, to, ((BuildingModel)model).getBuildrange(), new MyHashMap<Coordinate, Integer>(), false).start();
				}
				if (model.getPlayer().equals(state.getCurrentPlayer())) {
					state.updatePlayerModelPosition(from, to);
				}
				if (model.getPlayer().getTeam() == state.getCurrentPlayer().getTeam()) {
					state.updateAllyModelPosition(from, to);
				}
				state.setSight(getSight());
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
	
	private boolean addModel(final Coordinate c, final Model model) {
		if (state.getModels().containsKey(c)) {
			return false;
		}
		new Thread() {
			public void run() {
				System.out.println(c);
				state.addModel(c, model);
				new ViewRangeThread(null, state, c, model.getViewrange(), new MyHashMap<Coordinate, Integer>(), true).start();
				if (model instanceof Unit) {
					new MoveRangeThread(null, state, c, model, ((Unit)model).getMovespeed(), new MyHashMap<Coordinate, Integer>(), true).start();
				}
				if (model instanceof AttackingModel) {
					new DirectAttackRangeThread(null, state, c, ((AttackingModel)model).getAttackrange(), new MyHashMap<Coordinate, Integer>(), true).start();
					if (model instanceof Unit) {
						new FullAttackRangeThread(null, state, c, ((AttackingModel)model).getAttackrange(), new MyHashMap<Coordinate, Integer>(), true).start();
					}
				} 
				if (model instanceof BuildingModel) {
					new BuildRangeThread(null, state, c, ((BuildingModel)model).getBuildrange(), new MyHashMap<Coordinate, Integer>(), true).start();
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
			}
		}.start();
		return true;
	}
	
	public void removeModel(final Coordinate c) {
		new Thread() {
			public void run() {
				Model model = state.getModel(c);
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
				state.removeModel(c);
				state.setSight(getSight());
			}
		}.start();
	}
	
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
	
	private int getDifference(Coordinate c1, Coordinate c2) {
		int dx = c1.getX() - c2.getX();
		int dy = c1.getY() - c2.getY();
		dx = Math.abs(dx);
		dy = Math.abs(dy);
		return dx + dy;
	}
}
