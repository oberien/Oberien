package model;

import java.util.ArrayList;
import java.util.Arrays;


import model.map.FieldList;

public class Model {
	
	private String name;
	private Type type;
	private int id;
	private Player player;
	
	private int costMoney;
	private int costEnergy;
	private int costPopulation;
	
	private int life;
	private int maxLife;
	private int defense;
	private int viewrange;
	private int timeToBuild;
	
	private boolean moveGround;
	private boolean moveWater;
	private boolean moveAir;
	private boolean moveUnderground;
	private boolean moveUnderwater;
	
	private int movespeed;
	private int direction;
	private Layer defaultLayer;
	
	private boolean actionDone;
	
	private int level;
	
	public Model(String name, Type type, int id, Player player,
			int costMoney, int costEnergy, int costPopulation, 
			int maxLife, int defense, int viewrange,
			int movespeed, boolean moveGround, boolean moveWater, boolean moveAir, boolean moveUnderground, boolean moveUnderwater, Layer defaultLayer) {
		this.name = name;
		this.type = type;
		this.id = id;
		this.player = player;
		this.costMoney = costMoney;
		this.costEnergy = costEnergy;
		this.costPopulation = costPopulation;
		this.life = 0;
		this.maxLife = maxLife;
		this.defense = defense;
		this.viewrange = viewrange;
		this.timeToBuild = costMoney;
		this.movespeed = movespeed;
		this.moveGround = moveGround;
		this.moveWater = moveWater;
		this.moveAir = moveAir;
		this.moveUnderground = moveUnderground;
		this.moveUnderwater = moveUnderwater;
		this.defaultLayer = defaultLayer;
		this.direction = 1;
		this.actionDone = false;
	}
	
	public String getName() {
		return name;
	}

	public Type getType() {
		return type;
	}

	public int getId() {
		return id;
	}

	public Player getPlayer() {
		return player;
	}

	public int getCostMoney() {
		return costMoney;
	}

	public int getCostEnergy() {
		return costEnergy;
	}

	public int getCostPopulation() {
		return costPopulation;
	}

	public int getLife() {
		return life;
	}

	public int getMaxLife() {
		return maxLife;
	}

	public int getDefense() {
		return defense;
	}

	public int getViewrange() {
		return viewrange;
	}

	public int getTimeToBuild() {
		return timeToBuild;
	}

	public boolean isMoveGround() {
		return moveGround;
	}

	public boolean isMoveWater() {
		return moveWater;
	}

	public boolean isMoveAir() {
		return moveAir;
	}

	public boolean isMoveUnderground() {
		return moveUnderground;
	}

	public boolean isMoveUnderwater() {
		return moveUnderwater;
	}

	public int getMovespeed() {
		return movespeed;
	}

	public int getDirection() {
		return direction;
	}

	public Layer getDefaultLayer() {
		return defaultLayer;
	}

	public boolean isActionDone() {
		return actionDone;
	}
	
	public int getLevel() {
		return level;
	}
	
	public void decreaseTimeToBuild(int i) {
		if (timeToBuild-i < 0) {
			i = timeToBuild;
		}
		timeToBuild -= i;
		life += (float)maxLife/(float)costMoney*i;
	}
	
	public void levelUp() {
		level++;
	}
	
	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	public void setActionDone(boolean b) {
		actionDone = b;
	}
	
	public boolean damage(int damage) {
		life -= damage - defense - ((double)defense/5*level);
		if (life > 0) {
			return true;
		}
		return false;
	}
}

//
//public class Model {
//	private String name;
//	private Type type;
//	private int id;
//	private Player player;
//	
//	private int costMoney;
//	private int costEnergy;
//	private int costPopulation;
//
//	private int level;
//	private int life;
//	private int maxLife;
//	private int defense;
//	private int viewrange;
//	private int timeToBuild;
//	
//	private int strike;
//	private int evade;
//	private int damage;
//	private int attackrange;
//	private Type strongAgainst;
//	
//	
//	private int movespeed;
//	private byte[] canPass;
//	private boolean moveGround;
//	private boolean moveWater;
//	private boolean moveAir;
//	private boolean moveUnderground;
//	private boolean moveUnderwater;
//	private Layer defaultLayer;
//	
//	private int producingMoney;
//	private int producingEnergy;
//	private int producingPopulation;
//	
//	private int storagePlus;
//	private int populationStoragePlus;
//	
//	private Type builds;
//	private int buildSpeed;
//	private int buildRange;
//	private Model currentBuilding;
//	
//	private int canStore;
//	private ArrayList<Model> stored;
//	
//	private int direction;
//	private boolean moved;
//
//	private boolean actionDone;
//
//	public Model(String name, Type type, int id, Player player,
//			int costMoney, int costEnergy, int costPopulation, 
//			int life, int defense, int viewrange,
//			int strike, int evade, int damage, int attackrange, Type strongAgainst,
//			int movespeed, boolean moveGround, boolean moveWater, boolean moveAir, boolean moveUnderground, boolean moveUnderwater, Layer defaultLayer,
//			int producingMoney, int producingEnergy, int producingPopulation,
//			int storagePlus, int populationStoragePlus,
//			Type builds, int buildSpeed, int buildRange,
//			int canStore) {
//		this.name = name;
//		this.type = type;
//		this.id = id;
//		this.player = player;
//		this.costMoney = costMoney;
//		this.costEnergy = costEnergy;
//		this.costPopulation = costPopulation;
//		this.life = 0;
//		this.maxLife = life;
//		this.defense = defense;
//		this.viewrange = viewrange;
//		this.timeToBuild = costMoney;
//		this.strike = strike;
//		this.evade = evade;
//		this.damage = damage;
//		this.attackrange = attackrange;
//		this.strongAgainst = strongAgainst;
//		this.movespeed = movespeed;
//		this.canPass = FieldList.getInstance().getCanPass(type);
//		this.moveGround = moveGround;
//		this.moveWater = moveWater;
//		this.moveAir = moveAir;
//		this.moveUnderground = moveUnderground;
//		this.moveUnderwater = moveUnderwater;
//		this.defaultLayer = defaultLayer;
//		this.producingMoney = producingMoney;
//		this.producingEnergy = producingEnergy;
//		this.producingPopulation = producingPopulation;
//		this.storagePlus = storagePlus;
//		this.populationStoragePlus = populationStoragePlus;
//		this.builds = builds;
//		this.buildSpeed = buildSpeed;
//		this.buildRange = buildRange;
//		this.currentBuilding = null;
//		this.canStore = canStore;
//		this.stored = new ArrayList<Model>();
//		this.direction = 1;
//		this.actionDone = false;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public Type getType() {
//		return type;
//	}
//
//	public int getId() {
//		return id;
//	}
//	
//	public Player getPlayer() {
//		return player;
//	}
//
//	public int getCostMoney() {
//		return costMoney;
//	}
//
//	public int getCostEnergy() {
//		return costEnergy;
//	}
//
//	public int getCostPopulation() {
//		return costPopulation;
//	}
//
//	public int getLevel() {
//		return level;
//	}
//
//	public int getLife() {
//		return life;
//	}
//	
//	public int getMaxLife() {
//		return maxLife;
//	}
//
//	public int getDefense() {
//		return defense;
//	}
//
//	public int getViewrange() {
//		return viewrange;
//	}
//
//	public int getTimeToBuild() {
//		return timeToBuild;
//	}
//
//	public int getStrike() {
//		return strike;
//	}
//
//	public int getEvade() {
//		return evade;
//	}
//
//	public int getDamage() {
//		return damage + (int)((double)damage/10*level);
//	}
//
//	public int getAttackrange() {
//		return attackrange;
//	}
//	
//	public Type getStrongAgainst() {
//		return strongAgainst;
//	}
//
//	public int getMovespeed() {
//		return movespeed;
//	}
//
//	public boolean canPass(byte b) {
//		for (int i = 0; i < canPass.length; i++) {
//			if (canPass[i] == b) {
//				return true;
//			}
//		}
//		return false;
//	}
//
//	public boolean isMoveGround() {
//		return moveGround;
//	}
//
//	public boolean isMoveWater() {
//		return moveWater;
//	}
//
//	public boolean isMoveAir() {
//		return moveAir;
//	}
//
//	public boolean isMoveUnderground() {
//		return moveUnderground;
//	}
//
//	public boolean isMoveUnderwater() {
//		return moveUnderwater;
//	}
//
//	public Layer getDefaultLayer() {
//		return defaultLayer;
//	}
//
//	public int getProducingMoney() {
//		return producingMoney;
//	}
//
//	public int getProducingEnergy() {
//		return producingEnergy;
//	}
//
//	public int getProducingPopulation() {
//		return producingPopulation;
//	}
//
//	public int getStoragePlus() {
//		return storagePlus;
//	}
//	
//	public int getPopulationStoragePlus() {
//		return populationStoragePlus;
//	}
//
//	public Type getBuilds() {
//		return builds;
//	}
//
//	public int getBuildSpeed() {
//		return buildSpeed;
//	}
//
//	public int getBuildRange() {
//		return buildRange;
//	}
//	
//	public Model getCurrentBuilding() {
//		return currentBuilding;
//	}
//
//	public int getCanStore() {
//		return canStore;
//	}
//
//	public Model[] getStored() {
//		Model[] ret = new Model[stored.size()];
//		ret = stored.toArray(ret);
//		return ret;
//	}
//	
//	public int getDirection() {
//		return direction;
//	}
//	
//	public boolean isActionDone() {
//		return actionDone;
//	}
//	
//	public void levelUp() {
//		level++;
//	}
//	
//	public void decreaseTimeToBuild(int i) {
//		if (timeToBuild-i < 0) {
//			i = timeToBuild;
//		}
//		timeToBuild -= i;
//		life += (float)maxLife/(float)costMoney*i;
//	}
//	
//	public void setDirection(int direction) {
//		this.direction = direction;
//	}
//	
//	public void setActionDone(boolean b) {
//		actionDone = b;
//	}
//	public boolean isMoved() {
//		return moved;
//	}
//
//	public void setMoved(boolean moved) {
//		this.moved = moved;
//	}
//	
//	public void setCurrentBuilding(Model m) {
//		this.currentBuilding = m;
//	}
//
//	public boolean damage(int damage) {
//		life -= damage - defense - ((double)defense/5*level);
//		if (life > 0) {
//			return true;
//		}
//		return false;
//	}
//
//	@Override
//	public String toString() {
//		return "Model [name=" + name + ", type=" + type + ", id=" + id
//				+ ", player=" + player + ", costMoney=" + costMoney
//				+ ", costEnergy=" + costEnergy + ", costPopulation="
//				+ costPopulation + ", level=" + level + ", life=" + life
//				+ ", maxLife=" + maxLife + ", defense=" + defense
//				+ ", viewrange=" + viewrange + ", timeToBuild=" + timeToBuild
//				+ ", strike=" + strike + ", evade=" + evade + ", damage="
//				+ damage + ", attackrange=" + attackrange + ", strongAgainst="
//				+ strongAgainst + ", movespeed=" + movespeed + ", canPass="
//				+ Arrays.toString(canPass) + ", moveGround=" + moveGround
//				+ ", moveWater=" + moveWater + ", moveAir=" + moveAir
//				+ ", moveUnderground=" + moveUnderground + ", moveUnderwater="
//				+ moveUnderwater + ", defaultLayer=" + defaultLayer
//				+ ", producingMoney=" + producingMoney + ", producingEnergy="
//				+ producingEnergy + ", producingPopulation="
//				+ producingPopulation + ", storagePlus=" + storagePlus
//				+ ", builds=" + builds + ", buildSpeed=" + buildSpeed
//				+ ", buildRange=" + buildRange + ", currentBuilding="
//				+ currentBuilding + ", canStore=" + canStore + ", stored="
//				+ stored + ", direction=" + direction + ", moved=" + moved
//				+ ", actionDone=" + actionDone + "]";
//	}
//}
