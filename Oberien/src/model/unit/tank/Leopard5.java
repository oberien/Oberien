package model.unit.tank;

import model.Layer;
import model.Type;
import model.player.Player;

public class Leopard5 extends Tank {

	public Leopard5(Player player) {
		super("Leopard 5", Type.Tank, 64, player, 
				50, 20, 5,
				250, 5, 50, 7,
				7, true, false, false, false, false, Layer.Ground,
				85, 90, 3, Type.Tank);
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
				+ "<i>Leopard 5 is a heavy tank with strong damage.</i></body></html>";
	}


}
