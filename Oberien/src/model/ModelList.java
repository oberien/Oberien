package model;

import java.util.ArrayList;

import model.building.base.Base;
import model.building.producing.Barracks;
import model.building.producing.Factory;
import model.building.producing.SpyCenter;
import model.building.resourceCollector.HamsterWheel;
import model.building.resourceCollector.House;
import model.building.resourceCollector.NuclearReactor;
import model.building.resourceCollector.SolarCell;
import model.building.resourceCollector.Watergenerator;
import model.building.resourceCollector.Windmill;
import model.building.storage.BigStorage;
import model.building.storage.MediumStorage;
import model.building.storage.SmallStorage;
import model.building.turret.GatlingGun;
import model.building.turret.LaserCannon;
import model.unit.builder.HighRangeTurretBuilder;
import model.unit.builder.ProducingBuilder;
import model.unit.builder.ResourceCollectorBuilder;
import model.unit.builder.StorageBuilder;
import model.unit.builder.TurretBuilder;
import model.unit.infantry.HeavyAssaultWalker;
import model.unit.infantry.Rocketeer;
import model.unit.infantry.Sharpshooter;
import model.unit.infantry.Soldier;
import model.unit.infantry.Warrior;
import model.unit.recon.Spy;
import model.unit.robot.AnnoyBot;
import model.unit.robot.Strider;
import model.unit.tank.LaserTank;
import model.unit.tank.Leopard5;


public class ModelList {
	private final static ModelList instance = new ModelList();
	private Model[] models;
	
	
	public static ModelList getInstance() {
		return instance;
	}

	private ModelList() {
		models = new Model[30];
		models[0] = new Soldier(null);
		models[1] = new Rocketeer(null);
		models[2] = new HeavyAssaultWalker(null);
		models[3] = new Strider(null);
		models[4] = new Leopard5(null);
		models[5] = new LaserTank(null);
		models[6] = new Spy(null);
		models[7] = new ResourceCollectorBuilder(null);
		models[8] = new StorageBuilder(null);
		models[9] = new ProducingBuilder(null);
		models[10] = new TurretBuilder(null);
		models[11] = new HighRangeTurretBuilder(null);
		models[12] = new SolarCell(null);
		models[13] = new HamsterWheel(null);
		models[14] = new House(null);
		models[15] = new NuclearReactor(null);
		models[16] = new SmallStorage(null);
		models[17] = new MediumStorage(null);
		models[18] = new BigStorage(null);
		models[19] = new Barracks(null);
		models[20] = new Factory(null);
		models[21] = new SpyCenter(null);
		models[22] = new GatlingGun(null);
		models[23] = new LaserCannon(null);
		models[24] = new Base(null);
		models[25] = new Sharpshooter(null);
		models[26] = new Warrior(null);
		models[27] = new AnnoyBot(null);
		models[28] = new Windmill(null);
		models[29] = new Watergenerator(null);
	}
	
	public Model[] getAllModels() {
		return models;
	}
	
	public Model[] getModelsOfType(Type type) {
		ArrayList<Model> ret = new ArrayList<Model>();
		for (int i = 0; i < models.length; i++) {
			if (models[i].getType() == type) {
				ret.add(models[i]);
			}
		}
		Object[] o = ret.toArray();
		Model[] retur = new Model[o.length];
		for (int i = 0; i < o.length; i++) {
			retur[i] = (Model) o[i];
		}
		return retur;
	}
	
	public Model getModel(String name, Player player) {
		if(name.equals("Soldier")) {
			return new Soldier(player);
		} else if(name.equals("Rocketeer")) {
			return new Rocketeer(player);
		} else if(name.equals("Heavy Assault Walker")) {
			return new HeavyAssaultWalker(player);
		} else if(name.equals("Sharpshooter")) {
			return new Sharpshooter(player);
		} else if(name.equals("Warrior")) {
			return new Warrior(player);
		} else if(name.equals("Strider")) {
			return new Strider(player);
		} else if(name.equals("Annoy Bot")) {
			return new AnnoyBot(player);
		} else if(name.equals("Leopard 5")) {
			return new Leopard5(player);
		} else if(name.equals("Laser Tank")) {
			return new LaserTank(player);
		} else if(name.equals("Spy")) {
			return new Spy(player);
		} else if(name.equals("Resource Collector Builder")) {
			return new ResourceCollectorBuilder(player);
		} else if(name.equals("Storage Builder")) {
			return new StorageBuilder(player);
		} else if(name.equals("Producing Builder")) {
			return new ProducingBuilder(player);
		} else if(name.equals("Turret Builder")) {
			return new TurretBuilder(player);
		} else if(name.equals("High Range Turret Builder")) {
			return new HighRangeTurretBuilder(player);
		} else if(name.equals("Solar Cell")) {
			return new SolarCell(player);
		} else if(name.equals("Hamster Wheel")) {
			return new HamsterWheel(player);
		} else if(name.equals("House")) {
			return new House(player);
		} else if(name.equals("Nuclear Reactor")) {
			return new NuclearReactor(player);
		} else if(name.equals("Windmill")) {
			return new Windmill(player);
		} else if(name.equals("Watergenerator")) {
			return new Watergenerator(player);
		} else if(name.equals("Small Storage")) {
			return new SmallStorage(player);
		} else if(name.equals("Medium Storage")) {
			return new MediumStorage(player);
		} else if(name.equals("Big Storage")) {
			return new BigStorage(player);
		} else if(name.equals("Barracks")) {
			return new Barracks(player);
		} else if(name.equals("Factory")) {
			return new Factory(player);
		} else if(name.equals("Spy Center")) {
			return new SpyCenter(player);
		} else if(name.equals("Gatling Gun")) {
			return new GatlingGun(player);
		} else if(name.equals("Laser Cannon")) {
			return new LaserCannon(player);
		} else if(name.equals("Base")) {
			return new Base(player);
		} else {
			return null;
		}
	}
}
