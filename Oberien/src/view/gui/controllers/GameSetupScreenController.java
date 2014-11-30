package view.gui.controllers;

import java.util.ArrayList;
import java.util.List;

import logger.ErrorLogger;
import model.map.MapList;
import model.player.Player;
import model.player.PlayerColors;
import view.gamesstates.NiftyMenu;
import controller.Controller;
import controller.wincondition.Conquest;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.EffectBuilder;
import de.lessvoid.nifty.builder.HoverEffectBuilder;
import de.lessvoid.nifty.builder.ImageBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.PopupBuilder;
import de.lessvoid.nifty.builder.TextBuilder;
import de.lessvoid.nifty.controls.Button;
import de.lessvoid.nifty.controls.Scrollbar;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.controls.scrollpanel.builder.ScrollPanelBuilder;
import de.lessvoid.nifty.controls.textfield.builder.TextFieldBuilder;
import de.lessvoid.nifty.effects.Effect;
import de.lessvoid.nifty.effects.EffectEventId;
import de.lessvoid.nifty.effects.impl.Border;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.ImageRenderer;
import de.lessvoid.nifty.elements.render.PanelRenderer;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.render.NiftyImage;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.tools.Color;
import de.lessvoid.xml.xpp3.Attributes;

public class GameSetupScreenController implements ScreenController {
	private Nifty nifty;
	private Screen screen;
	private Element playerScrollPanelPanel;
	private Element popup;
	private Element popupLastMap = null;
	
	private int id = 1;
	
	@Override
	public void bind(Nifty nifty, Screen screen) {
		this.nifty = nifty;
		this.screen = screen;
		playerScrollPanelPanel = screen.findElementById("playerScrollPanelPanel");
		
	}

	@Override
	public void onStartScreen() {
	}

	@Override
	public void onEndScreen() {
	}
	
	public void addPlayer() {
		addPlayerComponents("Player:", "player" + id + "TextField", "Player " + id);
	}
	
	public void addAI() {
		addPlayerComponents("AI:", "player" + id + "TextField", "Computer " + id);
	}
	
	public void addPlayerComponents(final String label, final String textFieldID, final String textFieldText) {
		addPlayerComponents(label, textFieldID, textFieldText, (playerScrollPanelPanel.getChildrenCount()+1) + "");
	}
	
	public void addPlayerComponents(final String label, final String textFieldID, final String textFieldText, final String team) {
		final int index = playerScrollPanelPanel.getChildrenCount();
		final String panelName = "player" + id + "Panel";
		new PanelBuilder(panelName) {{
			alignLeft();
			valignTop();
			childLayoutHorizontal();
			width("100%");
			
			panel(new PanelBuilder("player" + id + "LabelPanel") {{
				alignLeft();
				valignCenter();
				childLayoutCenter();
				width("10%");
				text(new TextBuilder("player" + id + "Label"){{
					wrap(false);
					font("res/fonts/32/raven.fnt");
					color("#ffff");
					text(label);
					alignLeft();
					valignCenter();
				}});
			}});
			
			control(new TextFieldBuilder(textFieldID, textFieldText) {{
				valignCenter();
				width("55%");
			}});
			panel(new PanelBuilder("color" + id) {{
				backgroundColor("#f00f");
				valignCenter();
				width("5%");
				height("36px");
				onActiveEffect(new EffectBuilder("border") {{
					effectParameter("color", "#111f");
					effectParameter("inset", "1px");
					post(true);
				}});
				onActiveEffect(new EffectBuilder("border") {{
					effectParameter("color", "#222f");
					effectParameter("inset", "2px");
					post(true);
				}});
				onActiveEffect(new EffectBuilder("border") {{
					effectParameter("color", "#333f");
					effectParameter("inset", "3px");
					post(true);
				}});
				onActiveEffect(new EffectBuilder("border") {{
					effectParameter("color", "#444f");
					effectParameter("inset", "4px");
					post(true);
				}});
				onActiveEffect(new EffectBuilder("border") {{
					effectParameter("color", "#555f");
					effectParameter("inset", "5px");
					post(true);
				}});
				onHoverEffect(new HoverEffectBuilder("border") {{
					effectParameter("color", "#000f");
					post(true);
				}});
				interactOnClick("changeColor(" + getId() + ")");
			}});
			control(new ButtonBuilder("team" + id) {{
				label("Team " + team);
				interactOnClick("changeTeam(" + getId() + ")");
				interactOnClickRepeat("changeTeam(" + getId() + ")");
				valignCenter();
				width("15%");
			}});
			control(new ButtonBuilder("remove" + id) {{
				name("buttonRedThinBorder");
				label("Remove");
				interactOnClick("remove(" + panelName + ")");
				valignCenter();
				width("15%");
			}});
		}}.build(nifty, screen, playerScrollPanelPanel, index);
		
		id++;
	}
	
	public void resetPlayers() {
		new Thread() {
			public void run() {
				int removableChildren = playerScrollPanelPanel.getChildrenCount();
				List<Element> children = playerScrollPanelPanel.getChildren();
				for (int i = 0; i < removableChildren; i++) {
					children.get(i).markForRemoval();
				}
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {ErrorLogger.logger.severe(e.getMessage());}
				
				id = 0;
				addPlayerComponents("Player:", "player0TextField", System.getenv("USERNAME"), "1");
			}
		}.start();
	}
	
	public void changeTeam(String id) {
		//TODO: only accept teams that are on the map
		Button b = screen.findNiftyControl(id, Button.class);
		int team = Integer.parseInt(b.getText().substring(5));
		if (team == playerScrollPanelPanel.getChildrenCount()) {
			team = 1;
		} else {
			team++;
		}
		b.setText("Team " + team);
	}
	
	public void changeColor(String id) {
		Element e = screen.findElementById(id);
		PanelRenderer pr = e.getRenderer(PanelRenderer.class);
		Color colorNifty = pr.getBackgroundColor();
		java.awt.Color colorAwt = new java.awt.Color(colorNifty.getRed(), colorNifty.getGreen(), colorNifty.getBlue(), colorNifty.getAlpha());
		colorAwt = PlayerColors.getNext(colorAwt);
		float[] f = colorAwt.getRGBComponents(null);
		colorNifty = new Color(f[0], f[1], f[2], f[3]);
		pr.setBackgroundColor(colorNifty);
	}
	
	public void remove(String id) {
		screen.findElementById(id).markForRemoval();
		Element e = screen.findElementById("playerScrollPanelPanel");
		e.setHeight(e.getHeight()-42);
		Scrollbar b = screen.findNiftyControl("playerScrollPanel#nifty-internal-vertical-scrollbar", Scrollbar.class);
		b.setWorldMax(e.getHeight());
	}
	
	public void chooseMap() {
		String currentMap = screen.findElementById("mapNameLabel").getRenderer(TextRenderer.class).getOriginalText();
		popup = nifty.createPopup("popupMapChooser");
		
		Element element = popup.findElementById("insertMapChooser");
		element.addChild(
			new PanelBuilder("popupMapChooser") {{
			childLayoutCenter();
			
			panel(new PanelBuilder() {{
			childLayoutHorizontal();
			backgroundColor("#0000");
			valignTop();
			height("100%");
			
				panel(new PanelBuilder("mapPanel") {{
					alignLeft();
					valignTop();
					childLayoutVertical();
					width("65%");
					height("100%");
					padding("10px");
					control(new ScrollPanelBuilder("mapNames") {{
						alignLeft();
						valignTop();
						padding("5px");
						parameter("horizontal", "false");
						backgroundColor("#fff9");
						
						panel(new PanelBuilder("mapNamesScrollLabelPanel") {{
							alignLeft();
							valignTop();
							childLayoutVertical();
							padding("5px");
							width("100%");
													
							
							final String[] mapNames = MapList.getInstance().getMapNames();
							for (String s : mapNames) {
								final String currentMap = s;
								panel(new PanelBuilder("mapNames" + currentMap + "LabelPanel") {{
									alignLeft();
									valignTop();
									childLayoutCenter();
									interactOnClick("selectMap(" + getId() + ", " + currentMap + ")");
									width("100%");
									height("32px");
									
									text(new TextBuilder("mapNames" + currentMap + "Label") {{
										wrap(false);
										font("res/fonts/32/raven.fnt");
										color("#f00f");
										backgroundColor("#0000");
										text(currentMap);
										alignLeft();
										valignCenter();
									}});
								}});
							}
						}});
					}});
				}});

				panel(new PanelBuilder("mapPreview") {{
					childLayoutVertical();
					width("35%");
					height("70%");
					
					panel(new PanelBuilder("popupMapNameLabelPanel"){{
						childLayoutCenter();
						width("100%");
						text(new TextBuilder("popupMapNameLabel"){{
							wrap(true);
							font("res/fonts/32/raven.fnt");
							color("#ffff");
							text("Tera Rising");
						}});
					}});
					panel(new PanelBuilder("popupMapImagePanel"){{
						childLayoutCenter();
						width("100%");
						image(new ImageBuilder("popupMapImage"){{
							width("90%");
							height("90%");
							filename("res/imgs/maps/Tera Rising.png");
//							alignCenter();
//							valignCenter();
						}});
					}});
					
				}});
				
			}});
			
		}}.build(nifty, nifty.getCurrentScreen(), element));
		
		nifty.showPopup(screen, popup.getId(), null);
		selectMap("mapNames" + currentMap + "LabelPanel", currentMap);
	}
	
	public void closePopup() {
		nifty.closePopup(popup.getId());
	}
	
	public void selectMap(String id, String mapName) {
		Element e = popup.findElementById(id);
		if (popupLastMap != null) {
			popupLastMap.getRenderer(PanelRenderer.class).setBackgroundColor(new Color("#ffff"));
		}
		popupLastMap = e;
		e.getRenderer(PanelRenderer.class).setBackgroundColor(new Color("#bbbf"));
		popup.findElementById("popupMapNameLabel").getRenderer(TextRenderer.class).setText(mapName);
		popup.findElementById("mapNamesScrollLabelPanel").layoutElements();
		setScaledPopupImage("popupMapImage", "res/imgs/maps/" + mapName + ".png");
	}
	
	public void useMap() {
		String mapName = popup.findElementById("popupMapNameLabel").getRenderer(TextRenderer.class).getOriginalText();
		screen.findElementById("mapNameLabel").getRenderer(TextRenderer.class).setText(mapName);
		setScaledImage("mapImage", "res/imgs/maps/" + mapName + ".png");
		closePopup();
	}
	
	public void startGame() {
		//TODO: without a player one cannot start
		String mapName = screen.findElementById("mapNameLabel").getRenderer(TextRenderer.class).getOriginalText();
		List<Element> children = playerScrollPanelPanel.getChildren();
		ArrayList<Player> players = new ArrayList<Player>();
		for (int i = 0; i < children.size(); i++) {
			Element child = children.get(i);
			String name = child.findNiftyControl("player" + i + "TextField", TextField.class).getDisplayedText();
			if (child.findElementById("player" + i + "Label").getRenderer(TextRenderer.class).getOriginalText().equals("AI:")) {
				System.err.println("AI not implemented yet. " + name + " excluded from Players.");
				continue;
			}
			Color colorNifty = screen.findElementById("color" + i).getRenderer(PanelRenderer.class).getBackgroundColor();
			org.newdawn.slick.Color colorSlick = new org.newdawn.slick.Color(colorNifty.getRed(), colorNifty.getGreen(), colorNifty.getBlue(), colorNifty.getAlpha());
			int team = Integer.parseInt(screen.findNiftyControl("team" + i, Button.class).getText().substring(5))-1;
			players.add(new Player(name, colorSlick, team));
		}

		Player[] player = new Player[players.size()];
		player = players.toArray(player);
		NiftyMenu.startGame(new Controller(MapList.getInstance().getMap(mapName), player, "Conquest"));
	}
	
	public void mainMenu() {
		nifty.gotoScreen("start");
	}
	
	private void setScaledImage(String id, String filePath) {
		NiftyImage ni = nifty.createImage(filePath, false);
		int imgWidth = ni.getWidth();
		int imgHeight = ni.getHeight();
		int elWidth = screen.findElementById(id).getWidth();
		int elHight = screen.findElementById(id).getHeight();
		int elSize;
		if (elWidth > elHight) {
			elSize = elWidth;
		} else {
			elSize = elHight;
		}
		if (imgWidth == imgHeight) {
			screen.findElementById(id).setWidth(elSize);
			screen.findElementById(id).setHeight(elSize);
		} else if (imgWidth > imgHeight) {
			screen.findElementById(id).setWidth(elSize);
			double scale = imgHeight/(double)imgWidth;
			screen.findElementById(id).setHeight((int)(elSize*scale));
		}else if (imgWidth < imgHeight) {
			double scale = imgWidth/(double)imgHeight;
			screen.findElementById(id).setWidth((int)(elSize*scale));
			screen.findElementById(id).setHeight(elSize);
		}

		screen.findElementById(id).getRenderer(ImageRenderer.class).setImage(ni);
	}
	
	private void setScaledPopupImage(String id, String filePath) {
		NiftyImage ni = nifty.createImage(filePath, false);
		int imgWidth = ni.getWidth();
		int imgHeight = ni.getHeight();
		int elWidth = popup.findElementById(id).getWidth();
		int elHight = popup.findElementById(id).getHeight();
		int elSize;
		if (elWidth > elHight) {
			elSize = elWidth;
		} else {
			elSize = elHight;
		}
		if (imgWidth == imgHeight) {
			popup.findElementById(id).setWidth(elSize);
			popup.findElementById(id).setHeight(elSize);
		} else if (imgWidth > imgHeight) {
			popup.findElementById(id).setWidth(elSize);
			double scale = imgHeight/(double)imgWidth;
			popup.findElementById(id).setHeight((int)(elSize*scale));
		}else if (imgWidth < imgHeight) {
			double scale = imgWidth/(double)imgHeight;
			popup.findElementById(id).setWidth((int)(elSize*scale));
			popup.findElementById(id).setHeight(elSize);
		}

		popup.findElementById(id).getRenderer(ImageRenderer.class).setImage(ni);
	}
}
