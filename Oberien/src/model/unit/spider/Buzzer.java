package model.unit.spider;

import model.Layer;
import model.Type;
import model.player.Player;

public class Buzzer extends Spider {
	//TODO: img
	public Buzzer(Player player) {
		super("Buzzer", Type.Robot, 130, player, 
				60, 40, 0,
				100, 3, 5, 5,
				3, true, false, false, false, false, Layer.Ground,
				80, 90, 2, null);
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
				+ "<i>Buzzers are balanced spiders for scouting.</i></body></html>";
	}


}
