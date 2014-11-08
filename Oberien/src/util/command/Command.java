package util.command;

public class Command {
	private static final String DIVIDER = new String(new char[]{0});

	private int commandId;
	private CommandType type;
	private String[] args;

	public Command(int commandId, String[] args) {
		this.commandId = commandId;
		this.type = getType(commandId);
		this.args = args;
	}

	public Command(int commandId, String args) {
		this.commandId = commandId;
		this.type = getType(commandId);
		this.args = args.substring(1).split(new String(new char[]{0}));
	}

	public Command(String command) {
		this.commandId = command.charAt(0);
		this.type = getType(commandId);
		this.args = command.substring(1).split(new String(new char[]{0}));
	}

	private CommandType getType(int commandId) {
		switch (commandId) {
			case 0:
				return CommandType.ActionFailed;
			case 1:
				return CommandType.ActionSucceed;
			case 2:
				return CommandType.Register;
			case 3:
				return CommandType.Login;
			case 4:
				return CommandType.ValidateMail;
			case 5:
				return CommandType.UserAdded;
			case 6:
				return CommandType.UserRemoved;

			case 25:
				return CommandType.Kick;
			case 27:
				return CommandType.BroadcastToAll;

			case 50:
				return CommandType.Broadcast;
			case 51:
				return CommandType.PrivateMessage;

			case 65535:
				return CommandType.WrongCommandType;
			default:
				return null;
		}
	}

	public static Command wrongCommandType(String... args) {
		return new Command(65535, args);
	}

	public static Command actionFailed(String... args) {
		return new Command(0, args);
	}

	public static Command actionSucceed(String... args) {
		return new Command(1, args);
	}

	public static Command register(String username, String pwdHash, String mail) {
		return new Command(2, new String[]{username, pwdHash, mail});
	}

	public static Command login(String username, String pwdHash) {
		return new Command(3, new String[]{username, pwdHash});
	}

	public static Command validateMail(String activationToken) {
		return new Command(4, new String[]{activationToken});
	}

	public static Command addUsers(String... usernames) {
		return new Command(5, usernames);
	}

	public static Command removeUsers(String... usernames) {
		return new Command(6, usernames);
	}

	public static Command kick(String reason) {
		return new Command(25, new String[]{reason});
	}

	public static Command broadcastToAll(String username, String message) {
		return new Command(27, new String[]{username, message});
	}

	public static Command broadcastMessage(String username, String message) {
		return new Command(50, new String[]{username, message});
	}

	public static Command privateMessage(String from, String to, String message) {
		return new Command(51, new String[]{from, to, message});
	}

	public int getCommandId() {
		return commandId;
	}

	public String[] getArgs() {
		return args;
	}

	public CommandType getCommandType() {
		return type;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(new String(new char[]{(char)commandId}));
		for (int i = 0; i < args.length; i++) {
			if (i > 0) {
				sb.append(DIVIDER);
			}
			sb.append(args[i]);
		}
		return sb.toString();
	}
}
