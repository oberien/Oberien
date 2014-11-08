package util.command;

public enum CommandType {
	ActionFailed, ActionSucceed,
	Register, Login, ValidateMail,
	UserAdded, UserRemoved,

	Kick, Ban, BroadcastToAll,

	Broadcast, PrivateMessage,

	WrongCommandType
}
