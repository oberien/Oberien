package controller;

import java.util.ArrayList;
import java.util.Collections;

import model.map.Coordinate;
import model.map.FieldList;
import model.map.Map;
import model.map.MapList;
import model.unit.*;
import model.unit.builder.*;
import model.building.*;
import model.building.resourceCollector.ResourceCollector;
import model.building.storage.Storage;
import model.*;

public class Controller {

	public static final int MOVERANGE = 0;
	public static final int VIEWRANGE = 1;
	public static final int FULL_ATTACKRANGE = 2;
	public static final int DIRECT_ATTACKRANGE = 3;
	public static final int BUILDRANGE = 4;

	MyHashMap<Coordinate, Model> models;
	Player[] players;
	int currentPlayer;

	int round;

	Map map;

	public Controller(Player[] players) {
		models = new MyHashMap<Coordinate, Model>();
		this.players = players;
		round = 0;
		map = MapList.getInstance().getCurrentMap();
	}

	public Player getCurrentPlayer() {
		return players[currentPlayer];
	}

	/**
	 * Returns all keys of all allied models as Coordinate-array
	 *
	 * @return Coordinate-array with all positions of models
	 */
	public Coordinate[] getPlayerModelPositions() {
		ArrayList<Coordinate> ret = new ArrayList<Coordinate>();
		Object[] o = models.keySet().toArray();
		for (int i = 0; i < o.length; i++) {
			Coordinate c = (Coordinate) o[i];
			if (getModel(c).getPlayer().equals(getCurrentPlayer())) {
				ret.add(c);
			}
		}

		o = ret.toArray();
		Coordinate[] retur = new Coordinate[o.length];
		for (int i = 0; i < o.length; i++) {
			retur[i] = (Coordinate) o[i];
		}
		return retur;
	}

	/**
	 * Returns all keys of all allied models as Coordinate-array
	 *
	 * @return Coordinate-array with all positions of models
	 */
	public Coordinate[] getAllyModelPositions() {
		ArrayList<Coordinate> ret = new ArrayList<Coordinate>();
		Object[] o = models.keySet().toArray();
		for (int i = 0; i < o.length; i++) {
			Coordinate c = (Coordinate) o[i];
			if (getModel(c).getPlayer().getTeam() == getCurrentPlayer().getTeam()) {
				ret.add(c);
			}
		}

		o = ret.toArray();
		Coordinate[] retur = new Coordinate[o.length];
		for (int i = 0; i < o.length; i++) {
			retur[i] = (Coordinate) o[i];
		}
		return retur;
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
		Object[] o = ret.toArray();
		Coordinate[] retur = new Coordinate[o.length];
		for (int i = 0; i < o.length; i++) {
			retur[i] = (Coordinate) o[i];
		}
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
		Object[] o = ret.toArray();
		Coordinate[] retur = new Coordinate[o.length];
		for (int i = 0; i < o.length; i++) {
			retur[i] = (Coordinate) o[i];
		}
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

	private Model[] getPlayerModels() {
		Coordinate[] c = getPlayerModelPositions();
		Model[] ret = new Model[c.length];
		for (int i = 0; i < c.length; i++) {
			ret[i] = getModel(c[i]);
		}
		return ret;
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
		Model m = getModel(old);
		if (!(m instanceof Unit)) {
			return -1;
		}
		Unit u = (Unit) m;
		if (u.isActionDone() || u.isMoved()) {
			return -2;
		}
		if (!u.getPlayer().equals(getCurrentPlayer())) {
			return -3;
		}
		if (getModel(neu) != null) {
			return -4;
		}
		Coordinate[] c = getRange(old, MOVERANGE);
		for (int i = 0; i < c.length; i++) {
			if (c[i].equals(neu)) {
				models.remove(old);
				models.put(neu, u);
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
		Model m = getModel(attacker);
		if (!(m instanceof AttackingModel)) {
			return -1;
		}
		AttackingModel am = (AttackingModel) m;
		Unit au = (Unit)m;
		if (m.isActionDone() || m.getTimeToBuild() != 0) {
			return -2;
		}
		boolean attackerMissed = false;
		Coordinate[] c = getRange(attacker, DIRECT_ATTACKRANGE);
		int damage;
		int strikeChance;
		int random;
		boolean survived;
		for (int i = 0; i < c.length; i++) {
			//if enemy in range
			if (c[i].equals(defender)) {
				//get models
				Model def = models.get(defender);
				
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
							Model[] builders = getPlayerModels();
							for (int j = 0; j < builders.length; j++) {
								if (builders[j] instanceof Builder && ((Builder)builders[j]).getCurrentBuilding() == def) {
									((Builder)builders[j]).setCurrentBuilding(null);
								}
							}
						}
						models.remove(defender);
						return 2;
					}
				} else {
					attackerMissed = true;
				}
				c = getRange(defender, DIRECT_ATTACKRANGE);
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
							models.remove(attacker);
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
		getModel(c).setActionDone(true);
	}

	public void moved(Coordinate c) {
		getModel(c).setMoved(true);
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
	public boolean addModel(int x, int y, String name) {
		Model m = ModelList.getInstance().getModel(name, getCurrentPlayer());
		Coordinate c = new Coordinate(x, y, m.getDefaultLayer());
		if (!models.containsKey(c)) {
			m.decreaseTimeToBuild(m.getTimeToBuild());
			models.put(c, m);
			return true;
		}
		return false;
	}

	public void removeModel(Coordinate c) {
		models.remove(c);
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
		Model b = ModelList.getInstance().getModel(name, getCurrentPlayer());
		Model m = getModel(model);
		Coordinate build = new Coordinate(x, y, b.getDefaultLayer());
		if (!models.containsKey(build)) {
			if (m instanceof Builder && ((Builder)m).getBuilds() != b.getType()) {
				return -5;
			}
			Coordinate[] range = getRange(model, BUILDRANGE);
			for (int j = 0; j < range.length; j++) {
				if (range[j].equals(build)) {
					if (getCurrentPlayer().getMoney() < b.getCostMoney()) {
						return -1;
					}
					if (getCurrentPlayer().getEnergy() < b.getCostEnergy()) {
						return -2;
					}
					if (getCurrentPlayer().getPopulation() < b.getCostPopulation()) {
						return -3;
					}
					getCurrentPlayer().useMoney(b.getCostMoney());
					getCurrentPlayer().useEnergy(b.getCostEnergy());
					getCurrentPlayer().usePopulation(b.getCostPopulation());
					models.put(build, b);
					((Builder)m).setCurrentBuilding(b);
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
		Model m = getModel(model);
		Model b = getModel(build);
		if (b.getTimeToBuild() == 0 || !(m instanceof Builder) || ((Builder)m).getBuilds() != b.getType() || m.isActionDone()) {
			return false;
		}
		((Builder)m).setCurrentBuilding(b);
		return true;
	}

	/**
	 * Returns the Viewrange of all allied Models
	 *
	 * @return Coordinate-array of all fields in viewrange
	 */
	public Coordinate[] getSight() {

		if (round == 0) {
			return map.getStartAreaOfTeam(getCurrentPlayer().getTeam());
		}

		ArrayList<Coordinate> c = new ArrayList<Coordinate>();

		Coordinate[] keys = getAllyModelPositions();
		for (int i = 0; i < keys.length; i++) {
			Model m = getModel(keys[i]);
			if (m.getPlayer().getTeam() == getCurrentPlayer().getTeam() && m.getTimeToBuild() == 0) {
				Coordinate[] viewRange = getRange(keys[i], VIEWRANGE);
				for (int j = 0; j < viewRange.length; j++) {
					c.add(viewRange[j]);
				}
			}
		}
		removeDuplicates(c);
		Object[] o = c.toArray();
		Coordinate[] ret = new Coordinate[o.length];
		for (int i = 0; i < o.length; i++) {
			ret[i] = (Coordinate) o[i];
		}
		return ret;
	}

	/**
	 *
	 * @param c Coordinate where the unit is on
	 * @param type Type of range
	 * (StateMap.MOVERANGE/VIEWRANGE/ATTACKRANGE/BUILDRANGE)
	 * @return Coordinate-Array of all fields that the unit can
	 * move/view/attack/build
	 */
	public Coordinate[] getRange(Coordinate c, int type) {
		if (c == null) {
			return null;
		}
		Model model = getModel(c);
		if (model.getTimeToBuild() != 0) {
			return null;
		}
		if (type == MOVERANGE) {
			if (model.isMoved()) {
				return null;
			}
			return getMoveRange(c, model, model.getMovespeed());
		} else if (type == VIEWRANGE) {
			int viewrange = model.getViewrange() + FieldList.getInstance().get(MapList.getInstance().getCurrentMap().get(c.getX(), c.getY())).getViewplus();
			viewrange = viewrange < 1 ? 1 : viewrange;
			return getViewRange(c, viewrange);
		} else if (type == FULL_ATTACKRANGE) {
			if (model.isActionDone() || !(model instanceof AttackingModel)) {
				return null;
			}
			AttackingModel am = (AttackingModel)model;
			int attackrange = am.getAttackrange();
			if (attackrange == 0) {
				return null;
			}
			if (attackrange > 1) {
				attackrange += FieldList.getInstance().get(map.get(c.getX(), c.getY())).getAttackplus();
			}
			if (attackrange < 1) {
				attackrange = 1;
			}
			ArrayList<Coordinate> ret = new ArrayList<Coordinate>();
			Coordinate[] mr = getRange(c, MOVERANGE);

			for (Coordinate m : mr) {
				ret.add(m);
				Coordinate[] ar = getAttackRange(m, attackrange - getDifference(c, m));
				for (Coordinate a : ar) {
					ret.add(a);
				}
			}
			removeDuplicates(ret);
			Coordinate[] retur = new Coordinate[ret.size()];
			retur = ret.toArray(retur);
			return retur;
		}
		if (type == DIRECT_ATTACKRANGE) {
			if (model.isActionDone() || !(model instanceof AttackingModel)) {
				return null;
			}
			AttackingModel am = (AttackingModel)model;
			int attackrange = am.getAttackrange();
			if (attackrange == 0) {
				return null;
			}
			if (attackrange > 1) {
				attackrange += FieldList.getInstance().get(map.get(c.getX(), c.getY())).getAttackplus();
			}
			if (attackrange < 1) {
				attackrange = 1;
			}
			return getAttackRange(c, attackrange);
		} else {
			if (model.isActionDone() || !(model instanceof Builder)) {
				return null;
			}
			Builder b = (Builder)model;
			return getBuildRange(c, b.getBuildRange());
		}
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
				byte b = map.get(act.getX(), act.getY());
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
		Object[] o = ret.toArray();
		Coordinate[] retur = new Coordinate[o.length];
		for (int i = 0; i < o.length; i++) {
			retur[i] = (Coordinate) o[i];
		}
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
				byte b = map.get(act.getX(), act.getY());
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
				byte b = map.get(act.getX(), act.getY());
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
		Object[] o = ret.toArray();
		Coordinate[] retur = new Coordinate[o.length];
		for (int i = 0; i < o.length; i++) {
			retur[i] = (Coordinate) o[i];
		}
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
				byte b = map.get(act.getX(), act.getY());
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
		Coordinate[] sight = getSight();
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
		Object[] o = ret.toArray();
		Coordinate[] retur = new Coordinate[o.length];
		for (int i = 0; i < o.length; i++) {
			retur[i] = (Coordinate) o[i];
		}
		return retur;
	}

	public int getRound() {
		return round;
	}

	public void endTurn() {
		Model[] models = getPlayerModels();
		for (Model m : models) {
			m.setActionDone(false);
			if (m instanceof Unit) {
				Unit u = (Unit) m;
				u.setMoved(false);
			}
			if (m.getTimeToBuild() == 0) {
				if (m instanceof Builder) {
					Builder b = (Builder) m;
					if (b.getCurrentBuilding() != null) {
						b.getCurrentBuilding().decreaseTimeToBuild(b.getBuildSpeed());
						if (b.getCurrentBuilding().getTimeToBuild() == 0) {
							b.setCurrentBuilding(null);
						} else {
							b.setActionDone(true);
						}
					}
				}
			}
		}
		if (currentPlayer == players.length - 1) {
			round++;
			currentPlayer = 0;
		} else {
			currentPlayer++;
		}
		if (round != 0) {
			models = getPlayerModels();
			int storage = 0;
			int populationStorage = 0;
			for (Model m : models) {
				if (m.getTimeToBuild() == 0) {
					if (m instanceof ResourceCollector) {
						ResourceCollector rc = (ResourceCollector) m;
						getCurrentPlayer().addMoney(rc.getProducingMoney());
						getCurrentPlayer().addEnergy(rc.getProducingEnergy());
						getCurrentPlayer().addPopulation(rc.getProducingPopulation());
					}
					if (m instanceof Storage) {
						Storage s = (Storage) m;
						storage += s.getStoragePlus();
						populationStorage += s.getPopulationStoragePlus();
					}
				}
			}
			getCurrentPlayer().setStorage(storage);
			getCurrentPlayer().setPopulationStorage(populationStorage);
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

	private int getDifference(Coordinate c1, Coordinate c2) {
		int dx = c1.getX() - c2.getX();
		int dy = c1.getY() - c2.getY();
		dx = Math.abs(dx);
		dy = Math.abs(dy);
		return dx + dy;
	}

	public void printModels() {
		System.out.println(models);
	}
}
