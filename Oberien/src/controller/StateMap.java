package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import model.map.Coordinate;
import model.map.FieldList;
import model.map.Map;
import model.map.MapList;
import model.*;

public class StateMap {
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
	
	public StateMap(Player[] players) {
		models = new MyHashMap<Coordinate, Model>();
		this.players = players;
		round = 1;
		map = MapList.getInstance().getCurrentMap();
	}
	
	public Player getCurrentPlayer() {
		return players[currentPlayer];
	}
	
	/**
	 * Returns all keys of all allied models as Coordinate-array
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
	 * @param old Coordinate of Model
	 * @param neu Coordinate where the Model should move
	 * @return <ul>
	 * 			<li>-4 when there is already a model on that field</li>
	 * 			<li>-3 when the Model doesn't belong to the current palyer</li>
	 * 			<li>-2 when the Model's action is done</li>
	 * 			<li>-1 when the Model can't move</li>
	 * 			<li>0 when the Field where to move isn't in Moverange</li>
	 * 			<li>1 when Model moved successfully</li>
	 * 		  </ul>
	 */
	public int move(Coordinate old, Coordinate neu) {
		if (getModel(old).getMovespeed() == 0) {
			return -1;
		}
		if (getModel(old).isActionDone() || getModel(old).isMoved()) {
			return -2;
		}
		if (!getModel(old).getPlayer().equals(getCurrentPlayer())) {
			return -3;
		}
		if (getModel(neu) != null) {
			return -4;
		}
		Coordinate[] c = getRange(old, MOVERANGE);
		for (int i = 0; i < c.length; i++) {
			if (c[i].equals(neu)) {
				Model model = models.get(old);
				models.remove(old);
				models.put(neu, model);
				model.setDirection(getDirectionOf(old, neu));
				model.setMoved(true);
				return 1;
			}
		}
		return 0;
	}
	
	/**
	 * Attacker damages Defender
	 * @param attacker Coordinate of attacker
	 * @param defender Coordinate of Defender
	 * @return <ul>
	 * 			<li>-2 when the Model's action is done</li>
	 * 			<li>-1 when the Model can't attack (no damage/not finished to build)</li>
	 * 			<li>0 when the Field where to attack isn't in Attackrange</li>
	 * 			<li>1 when both Models attacked</li>
	 * 			<li>2 when only the attacker attacked</li>
	 * 			<li>3 when the attacker attacked and the defender missed</li>
	 * 			<li>4 when the attacker missed but the defender attacked</li>
	 *			<li>5 when both the attacker and the defender missed</li>
	 * 		  </ul>
	 */
	public int attack(Coordinate attacker, Coordinate defender) {
		if (getModel(attacker).getDamage() == 0) {
			return -1;
		}
		if (getModel(attacker).isActionDone() || getModel(attacker).getTimeToBuild() != 0) {
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
				Model atk = models.get(attacker);
				Model def = models.get(defender);
				//calc and evaluate strikeChance
				strikeChance = atk.getStrike() - def.getEvade();
				random = (int)(Math.random()*100);
				if (random <= strikeChance) {
					//ATTACKER
					//calculating damage
					damage = atk.getDamage();
					if (atk.getStrongAgainst() == def.getType()) {
						damage *= 2;
					}
					atk.setDirection(getDirectionOf(attacker, defender));
					atk.setActionDone(true);
					survived = def.damage(damage);
					if (!survived) {
						atk.levelUp();
						//if died unit isn't built yet all builders are removed from it
						if (def.getTimeToBuild() != 0) {
							Model[] m = getPlayerModels();
							for (int j = 0; j < m.length; j++) {
								if (m[j].getCurrentBuilding() == def) {
									m[j].setCurrentBuilding(null);
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
				for (int j = 0; j < c.length; j++) {
					//if attacker in range
					if (c[j].equals(attacker)) {
						//DEFENDER
						if (def.getTimeToBuild() != 0 || def.getDamage() == 0) {
							return 2;
						}
						//strikeChance
						strikeChance = def.getStrike() - atk.getEvade();
						random = (int)(Math.random()*100);
						if (random > strikeChance) {
							if (attackerMissed) {
								return 5;
							}
							return 3;
						}
						//damage
						damage = def.getDamage();
						if (def.getStrongAgainst() == atk.getType()) {
							damage *= 2;
						}
						System.out.println(damage);
						def.setDirection(getDirectionOf(defender, attacker));
						survived = atk.damage(damage);
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
	 * Adds an Model to the map - Only use for testing/debugging reasons
	 * To build an Model use buildModel()
	 * @param x x-Coordinate where to add
	 * @param y y-Coordinate where to add
	 * @param i ID of Model to add
	 * @return whether the Model is added or not (e.g. when on that spot there already is an Model)
	 */
	public boolean addModel(int x, int y, int i) {
		Model m = ModelList.getInstance().get(i, getCurrentPlayer());
		Coordinate c = new Coordinate(x, y, m.getDefaultLayer());
		if (!models.containsKey(c)) {
			m.decreaseTimeToBuild(m.getTimeToBuild());
			models.put(c, m);
			return true;
		}
		return false;
	}
	
	/**
	 * Adds an Model to the map - Only use for testing/debugging reasons
	 * To build an Model use buildModel()
	 * @param model Model which is building
	 * @param x x-Coordinate where to build
	 * @param y y-Coordinate where to build
	 * @param i ID of Model to build
	 * @return <ul>
	 * 			<li>0 when there already is an Model on that spot</li>
	 * 			<li>1 when the Model is built</li>
	 * 			<li>-1 when there is not enough Money to build the Model</li>
	 * 			<li>-2 when there is not enough Energy to build the Model</li>
	 * 			<li>-3 when there is not enough Population to build the Model</li>
	 * 			<li>-4 when the unit to build is out of range</li>
	 * 			<li>-5 when the Buildingtype of model and the Type of build doesn't fit</li>
	 * 		   </ul>
	 */
	public int buildModel(Coordinate model, int x, int y, int i) {
		Model b = ModelList.getInstance().get(i, getCurrentPlayer());
		Model m = getModel(model);
		Coordinate build = new Coordinate(x, y, b.getDefaultLayer());
		if (!models.containsKey(build)) {
			if (m.getBuilds() != b.getType()) {
				return -5;
			}
			Coordinate[] range = getRange(model, BUILDRANGE);
			for (int j = 0; j < range.length; j++) {
				if (range[j].equals(build)) {
					if (getCurrentPlayer().getMoney() < m.getCostMoney()) {
						return -1;
					}
					if (getCurrentPlayer().getEnergy() < m.getCostEnergy()) {
						return -2;
					}
					if (getCurrentPlayer().getPopulation() < m.getCostPopulation()) {
						return -3;
					}
					getCurrentPlayer().useMoney(m.getCostMoney());
					getCurrentPlayer().useEnergy(m.getCostEnergy());
					getCurrentPlayer().usePopulation(m.getCostPopulation());
					models.put(build, b);
					m.setCurrentBuilding(b);
					return 1;
				}
			}
			return -4;
		}
		return 0;
	}
	
	/**
	 * Adds an Model to build another model
	 * @param model Model willing to build
	 * @param build Model that will be built
	 * @return whether model is added to build or not (when model can't build Type of build / build is already finished / action is done)
	 */
	public boolean addModelToBuild(Coordinate model, Coordinate build) {
		Model m = getModel(model);
		Model b = getModel(build);
		if (b.getTimeToBuild() == 0 || m.getBuilds() != b.getType() || m.isActionDone()) {
			return false;
		}
		m.setCurrentBuilding(b);
		return true;
	}
	
	/**
	 * Returns the Viewrange of all allied Models
	 * @return Coordinate-array of all fields in viewrange
	 */
	public Coordinate[] getSight() {
		ArrayList<Coordinate> c = new ArrayList<Coordinate>();
		
		Coordinate[] keys = getAllyModelPositions();
		for (int i = 0; i < keys.length; i++) {
			if (getModel(keys[i]).getPlayer().getTeam() == getCurrentPlayer().getTeam()) {
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
	 * @param type Type of range (StateMap.MOVERANGE/VIEWRANGE/ATTACKRANGE/BUILDRANGE)
	 * @return Coordinate-Array of all fields that the unit can move/view/attack/build
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
			viewrange = viewrange<1 ? 1 : viewrange;
			return getViewRange(c, viewrange);
		} else if (type == FULL_ATTACKRANGE) {
			if (model.isActionDone()) {
				return null;
			}
			int attackrange = model.getAttackrange();
                        int tempatkr = attackrange;
                        attackrange += FieldList.getInstance().get(map.get(c.getX(), c.getY())).getAttackplus();
                        if (attackrange < 1) {
                            attackrange = 1;
                        }
			if (tempatkr == 0) {
				return null;
			}
			ArrayList<Coordinate> ret = new ArrayList<Coordinate>();
			Coordinate[] mr = getRange(c, MOVERANGE);
			
			for (Coordinate m : mr) {
				ret.add(m);
				Coordinate[] ar = getAttackRange(m, attackrange-getDifference(c, m));
				for (Coordinate a : ar) {
					ret.add(a);
				}
			}
			removeDuplicates(ret);
			Coordinate[] retur = new Coordinate[ret.size()];
			retur = ret.toArray(retur);
			return retur;
		}if (type == DIRECT_ATTACKRANGE) {
			if (model.isActionDone()) {
				return null;
			}
			int attackrange = model.getAttackrange();
                        int tempatkr = attackrange;
                        attackrange += FieldList.getInstance().get(map.get(c.getX(), c.getY())).getAttackplus();
                        if (attackrange < 1) {
                            attackrange = 1;
                        }
			if (tempatkr == 0) {
				return null;
			}
			return getAttackRange(c, attackrange);
		} else {
			if (model.isActionDone()) {
				return null;
			}
			return getBuildRange(c, model.getBuildRange());
		}
	}
	
	/**
	 * 
	 * @param c Coordinate where the unit is on
	 * @param model Model wanting to move
	 * @param maxRange the maximum range to move, usually unit.getMovespeed()
	 * @return Coordinate-Array of all fields that the unit can move
	 */
	private Coordinate[] getMoveRange(Coordinate c, Model model, int maxRange) {
		ArrayList<Coordinate> ret = new ArrayList<Coordinate>();
		ret.add(c);
		//0=North; 1=East; 2=South; 3=West
		for (int i = 0; i < 4; i++) {
			int move = maxRange;
			Coordinate act;
			for (int j = 1; move > 0; j++) {
				if (i == 0) {
					act = new Coordinate(c.getX(), c.getY()-j, Layer.Ground);
				} else if (i == 1) {
					act = new Coordinate(c.getX()+j, c.getY(), Layer.Ground);
				} else if (i == 2) {
					act = new Coordinate(c.getX(), c.getY()+j, Layer.Ground);
				} else {
					act = new Coordinate(c.getX()-j, c.getY(), Layer.Ground);
				}
				byte b = map.get(act.getX(), act.getY());
				if (b < 0) {
					break;
				}
				if (model.canPass(b)) {
					move -= FieldList.getInstance().get(b).getMovespeed();
					if (move >= 0) {
						ret.add(act);
						if (move != 0) {
							Coordinate[] sub = getMoveRange(act, model, move);
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
		
		getViewRangeOfDirectionVertical(c, ret, 0, viewRange);
		getViewRangeOfDirectionVertical(c, ret, 2, viewRange);
		
		removeDuplicates(ret);
		Coordinate[] coords = new Coordinate[ret.size()];
		coords = ret.toArray(coords);
		return coords;
	}
	
	private void getViewRangeOfDirection(Coordinate c, ArrayList<Coordinate> ret, int dir, int viewRange) {
		int view = viewRange;
		Coordinate act;
		for (int j = 1; view > 0; j++) {
			if (dir == 0) {
				act = new Coordinate(c.getX(), c.getY()-j, Layer.Ground);
			} else if (dir == 1) {
				act = new Coordinate(c.getX()+j, c.getY(), Layer.Ground);
			} else if (dir == 2) {
				act = new Coordinate(c.getX(), c.getY()+j, Layer.Ground);
			} else {
				act = new Coordinate(c.getX()-j, c.getY(), Layer.Ground);
			}
			byte b = map.get(act.getX(), act.getY());
			if (b < 0) {
				break;
			}
			view -= FieldList.getInstance().get(b).getViewrange();
			if (view >= 0) {
				ret.add(act);
			}
		}
	}
	private void getViewRangeOfDirectionVertical(Coordinate c, ArrayList<Coordinate> ret, int dir, int viewRange) {
		int view = viewRange;
		ret.add(c);
		getViewRangeOfDirection(c, ret, 1, view);
		getViewRangeOfDirection(c, ret, 3, view);
		
		Coordinate act;
		for (int j = 1; view > 0; j++) {
			if (dir == 0) {
				act = new Coordinate(c.getX(), c.getY()-j, Layer.Ground);
			} else if (dir == 1) {
				act = new Coordinate(c.getX()+j, c.getY(), Layer.Ground);
			} else if (dir == 2) {
				act = new Coordinate(c.getX(), c.getY()+j, Layer.Ground);
			} else {
				act = new Coordinate(c.getX()-j, c.getY(), Layer.Ground);
			}
			byte b = map.get(act.getX(), act.getY());
			if (b < 0) {
				break;
			}
			view -= FieldList.getInstance().get(b).getViewrange();
			if (view >= 0) {
				ret.add(act);
				if (view > 0) {
					getViewRangeOfDirection(act, ret, 1, view);
					getViewRangeOfDirection(act, ret, 3, view);
				}
			}
		}
	}
	
	/**
	 * @param c Coordinate where the unit is on
	 * @param maxRange the maximum range of attack, usually unit.getAttackrange()
	 * @return Coordinate-Array of all fields that the unit can move
	 */
	private Coordinate[] getAttackRange(Coordinate c, int maxRange) {
		ArrayList<Coordinate> ret = new ArrayList<Coordinate>();
		//0=North; 1=East; 2=South; 3=West
		for (int i = 0; i < 4; i++) {
			int attack = maxRange;
			Coordinate act;
			for (int j = 1; attack > 0; j++) {
				if (i == 0) {
					act = new Coordinate(c.getX(), c.getY()-j, Layer.Ground);
				} else if (i == 1) {
					act = new Coordinate(c.getX()+j, c.getY(), Layer.Ground);
				} else if (i == 2) {
					act = new Coordinate(c.getX(), c.getY()+j, Layer.Ground);
				} else {
					act = new Coordinate(c.getX()-j, c.getY(), Layer.Ground);
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
	 * @return Coordinate-Array of all fields that the unit can move
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
					act = new Coordinate(c.getX(), c.getY()-j, Layer.Ground);
				} else if (i == 1) {
					act = new Coordinate(c.getX()+j, c.getY(), Layer.Ground);
				} else if (i == 2) {
					act = new Coordinate(c.getX(), c.getY()+j, Layer.Ground);
				} else {
					act = new Coordinate(c.getX()-j, c.getY(), Layer.Ground);
				}
				byte b = map.get(act.getX(), act.getY());
				if (b < 0) {
					break;
				}
				build -= FieldList.getInstance().get(b).getAttackrange();
				if (build >= 0) {
					ret.add(act);
					if (build != 0) {
						Coordinate[] sub = getAttackRange(act, build);
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
	
	public void endTurn() {		
		Model[] models = getPlayerModels();
		for (Model m : models) {
			m.setActionDone(false);
			m.setMoved(false);
			if (m.getTimeToBuild() == 0) {
				if (m.getCurrentBuilding() != null) {
					m.getCurrentBuilding().decreaseTimeToBuild(m.getBuildSpeed());
					if (m.getCurrentBuilding().getTimeToBuild() == 0) {
						m.setCurrentBuilding(null);
					} else {
						m.setActionDone(true);
					}
				}
			}
		}
		if (currentPlayer == players.length-1) {
			round++;
			currentPlayer = 0;
		} else {
			currentPlayer++;
		}
		if (round != 1) {
			models = getPlayerModels();
			int storage = 0;
			for (Model m : models) {
				if (m.getTimeToBuild() == 0) {
					getCurrentPlayer().addMoney(m.getProducingMoney());
					getCurrentPlayer().addEnergy(m.getProducingEnergy());
					getCurrentPlayer().addPopulation(m.getProducingPopulation());
					storage += m.getStoragePlus();
				}
			}
			getCurrentPlayer().setStorage(storage);
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
		return dx+dy;
	}
}
