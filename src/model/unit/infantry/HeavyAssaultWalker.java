package model.unit.infantry;

import model.Layer;
import model.Type;
import model.player.Player;

public class HeavyAssaultWalker extends Infantry {

	public HeavyAssaultWalker(Player player) {
		super("Heavy Assault Walker", Type.Infantry, 4, player, 
				150, 25, 1, 
				500, 5, 10, 3, 
				3, true, false, false, false, false, Layer.Ground,
				70, 100, 1, null);
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
				+ "<i>A good defensive unit.</i></body></html>";
	}

}
