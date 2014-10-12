package model.building.turret;

import model.Layer;
import model.Type;
import model.player.Player;

public class LaserCannon extends Turret {

	public LaserCannon(Player player) {
		super("Laser Cannon", Type.Turret, 705, player, 
				75, 75, 0,
				75, 5, 7, Layer.Ground,
				95, 99, 3, Type.Robot);
	}
	
	@Override
	public String getDescription() {
		return "<html><body><h4>" + getName() + "</h4>"
				+ "Type: " + getType() + "<br>"
				+ "Standard layer: " + getDefaultLayer() + "<br>"
				+ "Costs " + getCostMoney() + " money, " + getCostEnergy() + " energy, " + getCostPopulation() + " population<br>"
				+ "HP: " + getMaxLife() + "<br>Defense: " + getDefense() + "<br>Viewrange: " + getViewRange() + "<br>"
				+ "Damage: " + getDamage() + "<br>Strikechance: " + getStrike() + "%<br>Attackrange: " + getAttackRange() + "<br>Strong against: " + getStrongAgainst() + "<br><br>"
				+ "<i>Defend your base with laz0rs.</i></body></html>";
	}
}
