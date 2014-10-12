package model.unit.spider;

import model.Layer;
import model.Type;
import model.player.Player;

public class Attercop extends Spider {
	//TODO: img
	public Attercop(Player player) {
		super("Attercop", Type.Robot, 129, player, 
				50, 50, 0,
				125, 5, 0, 4,
				8, true, false, false, false, false, Layer.Ground,
				60, 100, 1, Type.LightVehicle);
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
				+ "<i>The spiders' basic damage dealer.</i></body></html>";
	}


}
