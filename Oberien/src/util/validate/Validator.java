package util.validate;

public class Validator {
	public static boolean validateUsername(String username) {
		return !(username.contains("[")
				|| username.contains("]")
				|| username.contains("{")
				|| username.contains("}")
				|| username.contains("<")
				|| username.contains(">"));
	}

	public static boolean validateMail(String mail) {
		return mail.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
	}

	public static boolean validateActivationToken(String activationToken) {
		char[] chars = activationToken.toCharArray();
		for (char c : chars) {
			if (c < 33 || c > 126) {
				return false;
			}
		}
		return true;
	}
}
