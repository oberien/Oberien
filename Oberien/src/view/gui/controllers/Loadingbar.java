/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view.gui.controllers;

import de.lessvoid.nifty.controls.NiftyControl;

public interface Loadingbar extends NiftyControl {
	public float getProgress();
	public void setProgress(float i);
}
