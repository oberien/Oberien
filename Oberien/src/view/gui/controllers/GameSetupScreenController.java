package view.gui.controllers;

import java.util.List;

import model.player.PlayerColors;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.builder.EffectBuilder;
import de.lessvoid.nifty.builder.HoverEffectBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.TextBuilder;
import de.lessvoid.nifty.controls.Button;
import de.lessvoid.nifty.controls.Controller;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.controls.textfield.builder.TextFieldBuilder;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.events.NiftyMousePrimaryClickedEvent;
import de.lessvoid.nifty.elements.render.ElementRenderer;
import de.lessvoid.nifty.elements.render.PanelRenderer;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.tools.Color;
import de.lessvoid.xml.xpp3.Attributes;

public class GameSetupScreenController implements ScreenController {
	private Nifty nifty;
	private Screen screen;
	private Element playersPanel;
	
	private int id = 1;
	
	@Override
	public void bind(Nifty nifty, Screen screen) {
		this.nifty = nifty;
		this.screen = screen;
		playersPanel = screen.findElementById("players");
	}

	@Override
	public void onStartScreen() {
	}

	@Override
	public void onEndScreen() {
	}
	
	public void addPlayer() {
		addPlayerComponents("Player:", "player" + id + "TextField", "Player" + id);
	}
	
	public void addAI() {
		addPlayerComponents("AI:", "computer" + id + "TextField", "Computer" + id);
	}
	
	public void addPlayerComponents(final String label, final String textFieldID, final String textFieldText) {
		addPlayerComponents(label, textFieldID, textFieldText, playersPanel.getChildrenCount()-2 + "");
	}
	
	public void addPlayerComponents(final String label, final String textFieldID, final String textFieldText, final String team) {
		final int index = playersPanel.getChildrenCount()-2;
		final String panelName = "player" + id + "Panel";
		new PanelBuilder(panelName){{
			alignLeft();
			valignCenter();
			childLayoutHorizontal();
			width("100%");
			
			panel(new PanelBuilder("player" + id + "LabelPanel") {{
				alignLeft();
				valignCenter();
				childLayoutCenter();
				width("10%");
				text(new TextBuilder("player" + id + "Label"){{
					wrap(false);
					font("res/fonts/32/airstrikecond.fnt");
					color("#fff");
					text(label);
					alignLeft();
					valignCenter();
				}});
			}});
			
			control(new TextFieldBuilder(textFieldID, textFieldText){{
				valignCenter();
				width("55%");
			}});
			panel(new PanelBuilder("color" + id) {{
				backgroundColor("#f00");
				onHoverEffect(new HoverEffectBuilder("border") {{
					effectParameter("color", "#822");
					post(true);
				}});
				interactOnClick("changeColor(" + getId() + ")");
				valignCenter();
				height("23px");
				width("5%");
			}});
			control(new ButtonBuilder("team" + id) {{
				label("Team " + team);
				interactOnClick("changeTeam(" + getId() + ")");
				interactOnClickRepeat("changeTeam(" + getId() + ")");
				valignCenter();
				width("15%");
			}});
			control(new ButtonBuilder("remove" + id) {{
				label("Remove");
				interactOnClick("remove(" + panelName + ")");
				valignCenter();
				width("15%");
			}});
		}}.build(nifty, screen, playersPanel, index);
		
		id++;
	}
	
	public void resetPlayers() {
		int removableChildren = playersPanel.getChildrenCount()-3;
		List<Element> children = playersPanel.getChildren();
		for (int i = 1; i <= removableChildren; i++) {
			children.get(i).markForRemoval();
		}
		
		id = 1;
		addPlayerComponents("Player:", "player0TextField", System.getenv("USERNAME"), "1");
	}
	
	public void changeTeam(String id) {
		Button b = screen.findNiftyControl(id, Button.class);
		int team = Integer.parseInt(b.getText().substring(5));
		if (team == playersPanel.getChildrenCount()-3) {
			team = 1;
		} else {
			team++;
		}
		b.setText("Team " + team);
	}
	
	public void changeColor(String id) {
		Element e = screen.findElementById(id);
		java.awt.Color col = PlayerColors.get(3);
		e.getRenderer(PanelRenderer.class).setBackgroundColor(new Color((float)col.getRed(), (float)col.getGreen(), (float)col.getBlue(), (float)col.getAlpha()));
	}
	
	public void remove(String id) {
		screen.findElementById(id).markForRemoval();
	}
	
	public void mainMenu() {
		nifty.gotoScreen("start");
	}
}
