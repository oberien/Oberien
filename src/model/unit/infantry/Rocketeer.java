package model.unit.infantry;

import model.Layer;
import model.Type;
import model.player.Player;

public class Rocketeer extends Infantry {

	public Rocketeer(Player player) {
		super("Rocketeer", Type.Infantry, 1, player, 
				15, 2, 1,
				30, 2, 10, 7,
				6, true, false, false, false, false, Layer.Ground,
				75, 85, 2, Type.Tank);
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
				+ "<i>Early long range unit with weak damage.</i></body></html>";
	}

}
