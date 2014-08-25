package tests;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.SlickCallable;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.NiftyInputConsumer;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.controls.textfield.builder.TextFieldBuilder;
import de.lessvoid.nifty.elements.events.NiftyMousePrimaryClickedEvent;
import de.lessvoid.nifty.input.keyboard.KeyboardInputEvent;
import de.lessvoid.nifty.nulldevice.NullSoundDevice;
import de.lessvoid.nifty.renderer.lwjgl.input.LwjglKeyboardInputEventCreator;
import de.lessvoid.nifty.renderer.lwjgl.render.LwjglRenderDevice;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.spi.input.InputSystem;
import de.lessvoid.nifty.tools.TimeProvider;
import de.lessvoid.nifty.tools.resourceloader.NiftyResourceLoader;

public class MyNiftySlickGame extends StateBasedGame {

	class MouseEvent {
		private int mouseX;
		private int mouseY;
		private int mouseWheel;
		private int button;
		private boolean buttonDown;

		public MouseEvent(final int mouseX, final int mouseY,
				final boolean mouseDown, final int mouseButton) {
			this.mouseX = mouseX;
			this.mouseY = mouseY;
			this.buttonDown = mouseDown;
			this.button = mouseButton;
			this.mouseWheel = 0;
		}

		public void processMouseEvents(
				final NiftyInputConsumer inputEventConsumer) {
			inputEventConsumer.processMouseEvent(mouseX, mouseY, mouseWheel,
					button, buttonDown);
		}
	}

	class ScreenControllerExample implements ScreenController {
		public void bind(Nifty arg0, Screen arg1) {
		}

		public void onEndScreen() {
		}

		public void onStartScreen() {
		}
		
		@NiftyEventSubscriber(id="textField")
		public void onClick(String id, NiftyMousePrimaryClickedEvent event) {
		System.out.println("element with id [" + id + "] clicked at [" + event.getMouseX() +
		", " + event.getMouseY() + "]");
		}
	}

	private GameContainer container;
	private List<MouseEvent> mouseEvents = new ArrayList<MouseEvent>();
	private List<KeyboardInputEvent> keyEvents = new ArrayList<KeyboardInputEvent>();
	private LwjglKeyboardInputEventCreator inputEventCreator = new LwjglKeyboardInputEventCreator();

	class MainMenuGameState extends BasicGameState {

		private Nifty nifty;

		public void init(GameContainer container, StateBasedGame game)
				throws SlickException {
			nifty = new Nifty(new LwjglRenderDevice(), new NullSoundDevice(),
					new InputSystem() {
						public void forwardEvents(
								final NiftyInputConsumer inputEventConsumer) {
							for (MouseEvent event : mouseEvents) {
								event.processMouseEvents(inputEventConsumer);
							}
							mouseEvents.clear();

							for (KeyboardInputEvent event : keyEvents) {
								inputEventConsumer.processKeyboardEvent(event);
							}
							keyEvents.clear();
						}

						public void setMousePosition(int x, int y) {

						}

						@Override
						public void setResourceLoader(
								NiftyResourceLoader niftyResourceLoader) {

						}

					}, new TimeProvider());

			nifty.loadStyleFile("nifty-default-styles.xml");
			nifty.loadControlFile("nifty-default-controls.xml");

			Screen mainScreen = new ScreenBuilder("main") {{
				controller(new ScreenControllerExample());
				layer(new LayerBuilder("layer") {{
					childLayoutCenter();
					panel(new PanelBuilder("dialog-parent") {{
							height("200px");
							width("200px");
							style("nifty-panel");
							align(Align.Center);
							valign(VAlign.Center);
							childLayoutVertical();
							control(new ButtonBuilder("SelGrpBt") {{
								label("Select Group");
								width("120px");
								height("40px");
								align(Align.Center);
							}});
							control(new ButtonBuilder("SelDngBt") {{
								label("Select Dungeon");
								width("120px");
								height("40px");
								align(Align.Center);
							}});
							control(new ButtonBuilder("Options") {{
									label("Options");
									width("120px");
									height("40px");
									align(Align.Center);
								}
							});
							control(new ButtonBuilder("ExitGame") {{
								label("Exit Game");
								width("120px");
								height("40px");
								align(Align.Center);
							}});
							control(new TextFieldBuilder("textField", "Textfield") {{
								width("120px");
								align(Align.Center);
								visibleToMouse(true);
							}});
						}});
					}});
				}
			}.build(nifty);

			nifty.addScreen("mainScreen", mainScreen);
			nifty.gotoScreen("mainScreen");
		}

		public void update(GameContainer container, StateBasedGame game,
				int delta) throws SlickException {
			nifty.update();
		}

		public void render(GameContainer container, StateBasedGame game,
				Graphics g) throws SlickException {
			SlickCallable.enterSafeBlock();
			nifty.render(false);
			SlickCallable.leaveSafeBlock();
		}

		@Override
		public int getID() {
			return 0;
		}
	}

	public MyNiftySlickGame(String name) {
		super(name);
		addState(new MainMenuGameState());
	}

	public static void main(String[] args) throws SlickException {
		MyNiftySlickGame game = new MyNiftySlickGame("Nifty Slick Example");

		AppGameContainer app = new AppGameContainer(game);
		app.setDisplayMode(800, 600, false);
		app.setAlwaysRender(true);
		app.setFullscreen(false);
		app.setForceExit(true);
		app.start();
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		this.container = container;
	}

	/**
	 * mouse x.
	 */
	protected int mouseX;

	/**
	 * mouse y.
	 */
	protected int mouseY;

	/**
	 * mouse down.
	 */
	protected boolean mouseDown;

	/**
	 * 
	 * @param mouseX
	 * @param mouseY
	 * @param mouseDown
	 */
	private void forwardMouseEventToNifty(final int mouseX, final int mouseY,
			final boolean mouseDown) {
		mouseEvents.add(new MouseEvent(mouseX, mouseY, mouseDown, 0));
	}

	/**
	 * @see org.newdawn.slick.InputListener#mouseMoved(int, int, int, int)
	 */
	public void mouseMoved(final int oldx, final int oldy, final int newx,
			final int newy) {
		mouseX = newx;
		mouseY = newy;
		forwardMouseEventToNifty(mouseX, mouseY, mouseDown);
	}

	/**
	 * @see org.newdawn.slick.InputListener#mousePressed(int, int, int)
	 */
	public void mousePressed(final int button, final int x, final int y) {
		mouseX = x;
		mouseY = y;
		mouseDown = true;
		forwardMouseEventToNifty(mouseX, mouseY, mouseDown);
	}

	/**
	 * @see org.newdawn.slick.InputListener#mouseReleased(int, int, int)
	 */
	public void mouseReleased(final int button, final int x, final int y) {
		mouseX = x;
		mouseY = y;
		mouseDown = false;
		forwardMouseEventToNifty(mouseX, mouseY, mouseDown);
	}

	/**
	 * @see org.newdawn.slick.InputListener#keyPressed(int, char)
	 */
	public void keyPressed(final int key, final char c) {
		keyEvents.add(inputEventCreator.createEvent(key, c, true));
	}

	/**
	 * @see org.newdawn.slick.InputListener#keyReleased(int, char)
	 */
	public void keyReleased(final int key, final char c) {
		keyEvents.add(inputEventCreator.createEvent(key, c, false));
	}

}
