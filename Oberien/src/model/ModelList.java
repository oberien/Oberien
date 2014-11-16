package model;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import model.building.base.Base;
import model.building.producing.MilitaryTrainingArea;
import model.building.producing.RoboHub;
import model.building.producing.TankManufactory;
import model.building.resourceCollector.HamsterWheel;
import model.building.resourceCollector.House;
import model.building.resourceCollector.NuclearReactor;
import model.building.resourceCollector.SolarCell;
import model.building.resourceCollector.Windmill;
import model.building.storage.BigStorage;
import model.building.storage.MediumStorage;
import model.building.storage.SmallStorage;
import model.building.turret.GatlingGun;
import model.building.turret.LaserCannon;
import model.player.Player;
import model.unit.builder.HighRangeTurretBuilder;
import model.unit.builder.ProducingBuilder;
import model.unit.builder.ResourceCollectorBuilder;
import model.unit.builder.StorageBuilder;
import model.unit.builder.TurretBuilder;
import model.unit.infantry.HeavyAssaultWalker;
import model.unit.infantry.Rocketeer;
import model.unit.infantry.Sharpshooter;
import model.unit.infantry.Soldier;
import model.unit.infantry.Spy;
import model.unit.infantry.Warrior;
import model.unit.robot.AnnoyBot;
import model.unit.spider.Strider;
import model.unit.tank.LaserTank;
import model.unit.tank.Leopard5;


public class ModelList {
	private final static ModelList instance = new ModelList();
	private Model[] models;
	
	
	public static ModelList getInstance() {
		return instance;
	}

	private ModelList() {
		models = new Model[29];
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
		models[19] = new MilitaryTrainingArea(null);
		models[20] = new TankManufactory(null);
		models[21] = new RoboHub(null);
		models[22] = new GatlingGun(null);
		models[23] = new LaserCannon(null);
		models[24] = new Base(null);
		models[25] = new Sharpshooter(null);
		models[26] = new Warrior(null);
		models[27] = new AnnoyBot(null);
		models[28] = new Windmill(null);
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
	
	public Model createNewModel(String name, Player player) {
		for (Model m : getAllModels()) {
			if (m.getName().equals(name)) {
				try {
					return m.getClass().getConstructor(player.getClass()).newInstance(player);
				} catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
