package model.unit.infantry;

import model.Layer;
import model.Type;
import model.player.Player;

public class Soldier extends Infantry {

	public Soldier(Player player) {
		super("Soldier", Type.Infantry, 0, player, 
				10, 1, 1, 
				50, 5, 10, 5,
				5, true, false, false, false, false, Layer.Ground,
				90, 100, 1, Type.Infantry);
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
				+ "<i>Infantry is a low cost, but really weak unit.</i></body></html>";
	}

}
