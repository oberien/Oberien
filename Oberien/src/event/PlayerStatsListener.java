/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package event;

/**
 *
 * @author Bobthepeanut
 */
public interface PlayerStatsListener {
	public void moneyChanged(int metal);
	public void energyChanged(int energy);
	public void populationChanged(int population);
}
