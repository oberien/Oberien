package model.unit.infantry;

import model.Layer;
import model.Type;
import model.player.Player;

public class Flamethrower extends Infantry {
	//TODO: img
	public Flamethrower(Player player) {
		super("Flamethrower", Type.Infantry, 3, player, 
				50, 50, 1, 
				50, 5, 5, 2, 
				2, true, false, false, false, false, Layer.Ground, 
				25, 90, 2, Type.Infantry);
	}
	
	@Override
	public String getDescription() {
		return "<html><body><h4>" + getName() + "</h4>"
				+ "Type: " + getType() + "<br>"
				+ "Costs " + getCostMoney() + " money, " + getCostEnergy() + " energy, " + getCostPopulation() + " population<br>"
				+ "HP: " + getMaxLife() + "<br>Defense: " + getDefense() + "<br>Viewrange: " + getViewRange() + "<br>"
				+ "Movespeed: " + getMovespeed() + "<br>Can move on (Ground/Water/Air/Underground/Underwater): " + isMoveGround() + "/" + isMoveWater() + "/"
				+ isMoveAir() + "/" + isMoveUnderground() + "/" + isMoveUnderwater() + "<br>Standard layer: " + getDefaultLayer() + "<br>"
				+ "Damage: " + getDamage() + "<br>Strikechance: " + getStrike() + "%<br>Attackrange: " + getAttackRange() + "<br>Strong against: " + getStrongAgainst() + "<br><br>"
				+ "<i>Balanced unit with medium defensive stats and medium offensive stats.</i></body></html>";
	}
}
