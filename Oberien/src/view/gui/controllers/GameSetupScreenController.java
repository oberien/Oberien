package view.gui.controllers;

import model.player.PlayerColors;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.builder.EffectBuilder;
import de.lessvoid.nifty.builder.HoverEffectBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.TextBuilder;
import de.lessvoid.nifty.controls.Button;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.controls.textfield.builder.TextFieldBuilder;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.events.NiftyMousePrimaryClickedEvent;
import de.lessvoid.nifty.elements.render.ElementRenderer;
import de.lessvoid.nifty.elements.render.PanelRenderer;
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
		final int index = playersPanel.getChildrenCount()-1;
		final String panelName = "player" + id + "Panel";
		new PanelBuilder(panelName){{
			alignLeft();
			valignCenter();
			childLayoutHorizontal();
			width("100%");
			
			text(new TextBuilder("player" + id + "Label"){{
				wrap(false);
				font("res/fonts/32/airstrikecond.fnt");
				text("Player:");
				alignLeft();
				valignCenter();
				width("15%");
			}});
			control(new TextFieldBuilder("player" + id + "TextField", "Player" + id){{
				valignCenter();
				width("60%");
			}});
			panel(new PanelBuilder("color" + id) {{
				backgroundColor("#f00");
				onHoverEffect(new HoverEffectBuilder("border") {{
					effectParameter("color", "#822");
					post(true);
				}});
				interactOnClick("changeColor(" + getId() + ")");
				valignCenter();
				width("5%");
				height("23px");
			}});
			control(new ButtonBuilder("team" + id) {{
				label("Team " + (playersPanel.getChildrenCount()-1));
				interactOnClick("changeTeam(" + getId() + ")");
				interactOnClickRepeat("changeTeam(" + getId() + ")");
				valignCenter();
				width("10%");
			}});
			control(new ButtonBuilder("remove" + id) {{
				label("Remove");
				interactOnClick("remove(" + panelName + ")");
				valignCenter();
				width("10%");
			}});
		}}.build(nifty, screen, playersPanel, index);
		
		id++;
	}
	
	public void addAI() {
		final int index = playersPanel.getChildrenCount()-1;
		final String panelName = "player" + id + "Panel";
		new PanelBuilder(panelName){{
			alignLeft();
			valignCenter();
			childLayoutHorizontal();
			width("100%");
			height("23px");
			
			text(new TextBuilder("player" + id + "Label"){{
				wrap(false);
				font("res/fonts/32/airstrikecond.fnt");
				text("AI:");
				alignLeft();
				valignCenter();
				width("15%");
			}});
			control(new TextFieldBuilder("computer" + id + "TextField", "Computer" + id){{
				valignCenter();
				width("60%");
			}});
			panel(new PanelBuilder("color" + id) {{
				backgroundColor("#f00");
				onHoverEffect(new HoverEffectBuilder("border") {{
					effectParameter("color", "#822");
					post(true);
				}});
				interactOnClick("changeColor(" + getId() + ")");
				valignCenter();
				width("5%");
			}});
			control(new ButtonBuilder("team" + id) {{
				label("Team " + (playersPanel.getChildrenCount()-1));
				interactOnClick("changeTeam(" + getId() + ")");
				interactOnClickRepeat("changeTeam(" + getId() + ")");
				valignCenter();
				width("10%");
			}});
			control(new ButtonBuilder("remove" + id) {{
				label("Remove");
				interactOnClick("remove(" + panelName + ")");
				valignCenter();
				width("10%");
			}});
		}}.build(nifty, screen, playersPanel, index);
		
		id++;
	}
	
	public void changeTeam(String id) {
		Button b = screen.findNiftyControl(id, Button.class);
		int team = Integer.parseInt(b.getText().substring(5));
		if (team == playersPanel.getChildrenCount()-2) {
			team = 1;
		} else {
			team++;
		}
		b.setText("Team " + team);
	}
	
	public void changeColor(String id) {
		Element e = screen.findElementById(id);
		java.awt.Color col = PlayerColors.get(3);
		System.out.println(e.getElementType().getAttributes().getAsColor("backgroundColor"));
		e.getRenderer(PanelRenderer.class).setBackgroundColor(new Color((float)col.getRed(), (float)col.getGreen(), (float)col.getBlue(), (float)col.getAlpha()));
//		e.getElementType().applyAttributes(new Attributes("id=\"color1\"", "backgroundColor=\"" + new Color((float)col.getRed(), (float)col.getGreen(), (float)col.getBlue(), (float)col.getAlpha()).getColorString() + "\"", "width=\"5%\""));
		System.out.println(e.getElementType().getAttributes().getAsColor("backgroundColor"));
	}
	
	public void remove(String id) {
		screen.findElementById(id).markForRemoval();
	}
}
