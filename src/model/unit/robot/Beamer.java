package model.unit.robot;

import model.Layer;
import model.Type;
import model.player.Player;

public class Beamer extends Robot {
	//TODO: img
	public Beamer(Player player) {
		super("Beamer", Type.Robot, 34, player, 
				100, 100, 0, 
				300, 10, 10, 2,
				1, true, false, false, false, false, Layer.Ground,
				50, 98, 2, Type.Tank);
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
				+ "<i>Kill tanks with laz0rs.</i></body></html>";
	}

}
