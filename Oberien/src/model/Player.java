package model;

import java.io.Serializable;

import org.newdawn.slick.Color;

public class Player implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	private String name;
	private Color color;
	private int team;
	private int money;
	private int energy;
	private int population;
	private int storage;
	private int populationStorage;
	
	public Player(String name, Color color, int team) {
		super();
		this.name = name;
		this.color = color;
		this.team = team;
		this.money = 100;
		this.energy = 100;
		this.population = 10;
		this.storage = 100;
	}

	public String getName() {
		return name;
	}

	public Color getColor() {
		return color;
	}
	
	public int getTeam() {
		return team;
	}

	public int getMoney() {
		return money;
	}

	public int getEnergy() {
		return energy;
	}

	public int getPopulation() {
		return population;
	}

	public void useMoney(int money) {
		this.money -= money;
	}

	public void useEnergy(int energy) {
		this.energy -= energy;
	}

	public void usePopulation(int population) {
		this.population -= population;
	}
	
	public void addMoney(int money) {
		this.money += money;
	}

	public void addEnergy(int energy) {
		this.energy += energy;
	}

	public void addPopulation(int population) {
		this.population += population;
	}

	public void setStorage(int storage) {
		this.storage = storage;
		if (money > storage) {
			money = storage;
		}
		if (energy > storage) {
			energy = storage;
		}
	}
	
	public void setPopulationStorage(int populationStorage) {
		this.populationStorage = populationStorage;
		if (population > populationStorage) {
			population = populationStorage;
		}
	}
	
	public int getStorage() {
		return storage;
	}
	
	public int getPopulationStorage() {
		return populationStorage;
	}
	@Override
	public String toString() {
		return "Player [name=" + name + ", color=" + color + ", team=" + team
				+ ", money=" + money + ", energy=" + energy + ", population="
				+ population + "]";
	}
	
	public boolean equals(Object o) {
		return name.equals(((Player)o).getName());
	}
}
