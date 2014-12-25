package model.building.turret;

import model.Layer;
import model.Type;
import model.player.Player;

public class GatlingGun extends Turret {

	public GatlingGun(Player player) {
		super("Gatling Gun", Type.Turret, 704, player, 
				50, 50, 0, 
				100, 5, 7, Layer.Ground,
				100, 90, 3, Type.Infantry);
	}
	
	@Override
	public String getDescription() {
		return "<html><body><h4>" + getName() + "</h4>"
				+ "Type: " + getType() + "<br>"
				+ "Standard layer: " + getDefaultLayer() + "<br>"
				+ "Costs " + getCostMoney() + " money, " + getCostEnergy() + " energy, " + getCostPopulation() + " population<br>"
				+ "HP: " + getMaxLife() + "<br>Defense: " + getDefense() + "<br>Viewrange: " + getViewRange() + "<br>"
				+ "Damage: " + getDamage() + "<br>Strikechance: " + getStrike() + "%<br>Attackrange: " + getAttackRange() + "<br>Strong against: " + getStrongAgainst() + "<br><br>"
				+ "<i>Gatling guns are high-ranged early game turrets with a good amount of damage.</i></body></html>";
	}
}
