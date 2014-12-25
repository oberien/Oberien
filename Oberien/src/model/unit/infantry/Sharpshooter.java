package model.unit.infantry;

import model.Layer;
import model.Type;
import model.player.Player;

public class Sharpshooter extends Infantry {
	//TODO: img
	public Sharpshooter(Player player) {
		super("Sharpshooter", Type.Infantry, 5, player, 
				100, 35, 1, 
				50, 1, 2, 5,
				1, true, false, false, false, false, Layer.Ground,
				100, 100, 5, Type.Infantry);
		
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
				+ "<i>Sharpshooters have a high damage and a ultra-long range, but move quite slow.</i></body></html>";
	}

}
