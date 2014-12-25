//package view.gui.controllers.multiplayer;
//
//import de.lessvoid.nifty.Nifty;
//import de.lessvoid.nifty.NiftyEventSubscriber;
//import de.lessvoid.nifty.controls.Chat;
//import de.lessvoid.nifty.controls.ChatTextSendEvent;
//import de.lessvoid.nifty.elements.render.TextRenderer;
//import de.lessvoid.nifty.screen.Screen;
//import de.lessvoid.nifty.screen.ScreenController;
//import event.NiftyMenuUpdateListener;
//import event.multiplayer.*;
//import event.multiplayer.chat.*;
//import event.multiplayer.chat.ChatEventType;
//import logger.ErrorLogger;
//import multiplayer.client.Client;
//import view.gamesstates.NiftyMenu;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.logging.Level;
//
//public class ChatScreenController implements ScreenController, NiftyMenuUpdateListener, ChatEventListener {
//	private Nifty nifty;
//	private Screen screen;
//	private Chat chat;
//	private ArrayList<ChatEvent> chatEventBuffer = new ArrayList<>();
//	private ArrayList<UserEvent> userEventBuffer = new ArrayList<>();
//	private String user = "asdf";
//
//	@Override
//	public void bind(Nifty nifty, Screen screen) {
//		this.nifty = nifty;
//		this.screen = screen;
//		chat = screen.findNiftyControl("chat", Chat.class);
//	}
//
//	@Override
//	public void onStartScreen() {
//		Client.addChatEventListener(this);
//		Client.addUserEventListener(this);
//		NiftyMenu.addNiftyMenuUpdateListener(this);
//		chat.update();
//	}
//
//	@Override
//	public void onEndScreen() {
//		Client.removeChatEventListener(this);
//		Client.removeUserEventListener(this);
//		NiftyMenu.removeNiftyMenuUpdateListener(this);
//	}
//
//	@Override
//	public void update() {
//		for (ChatEvent e : chatEventBuffer) {
//			switch (e.getType()) {
//				case BroadcastMessageReceived:
//				case PrivateMessageReceived:
//					chat.receivedChatLine(e.getUsername() + ": " + e.getMessage(), null);
//					break;
//			}
//		}
//		chatEventBuffer.clear();
//		for (UserEvent e : userEventBuffer) {
//			switch (e.getType()) {
//				case Kick:
//					break;
//				case UserAdded:
//					chat.addPlayer(e.getData(), null);
//					break;
//				case UserRemoved:
//					chat.removePlayer(e.getData());
//					break;
//			}
//		}
//		userEventBuffer.clear();
//	}
//
//	@NiftyEventSubscriber(id="chat")
//	public void messageSent(String id, ChatTextSendEvent e) {
//		try {
//			System.out.println(e.getText());
//			String help = Client.send(e.getText());
//			if (help != null) {
//				chatEventBuffer.add(new ChatEvent(ChatEventType.PrivateMessageReceived, "Help", help));
//			}
//		} catch (IOException e1) {
//			ErrorLogger.logger.log(Level.SEVERE, e1.getMessage(), e1);
//			screen.findElementById("errorMessage").getRenderer(TextRenderer.class).setText("Connection to Server failed: " + e1.getMessage());
//		}
//	}
//
//	@Override
//	public void broadcastMessageReceived(ChatEvent e) {
//		chatEventBuffer.add(e);
//	}
//
//	@Override
//	public void privateMessageReceived(ChatEvent e) {
//		chatEventBuffer.add(e);
//	}
//
//	@Override
//	public void kicked(UserEvent e) {
//		userEventBuffer.add(e);
//	}
//
//	@Override
//	public void userAdded(UserEvent e) {
//		userEventBuffer.add(e);
//	}
//
//	@Override
//	public void userRemoved(UserEvent e) {
//		userEventBuffer.add(e);
//	}
//
//	public void back() {
//		Client.logout();
//		nifty.gotoScreen("start");
//	}
//
//	@Override
//	public void broadcastMessageReceived(BroadcastMessageReceivedEvent e) {
//
//	}
//
//	@Override
//	public void privateMessageReceived(PrivateMessageReceivedEvent e) {
//
//	}
//
//	@Override
//	public void userKicked(UserKickedEvent e) {
//
//	}
//
//	@Override
//	public void userAdded(UserAddedEvent e) {
//
//	}
//
//	@Override
//	public void userRemoved(UserRemovedEvent e) {
//
//	}
//}
