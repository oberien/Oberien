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
public interface TurnChangedListener {
	public void roundChanged(int turn);
	public void playernameChanged(String playername);
}
