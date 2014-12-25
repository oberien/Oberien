package model.unit.infantry;

import model.Layer;
import model.Type;
import model.player.Player;

public class Warrior extends Infantry {
	//TODO: img
	public Warrior(Player player) {
		super("Warrior", Type.Infantry, 2, player, 
				250, 20, 1,
				250, 8, 5, 3, 
				2, true, false, false, false, false, Layer.Ground, 
				80, 98, 2, Type.Robot);
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
				+ "<i>The upgraded version of soldiers, costs more and has increased stats.</i></body></html>";
	}
}
