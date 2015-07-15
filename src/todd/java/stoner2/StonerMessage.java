package todd.java.stoner2;

public class StonerMessage {

	public StonerMessage(SmokingItem recipient, SmokingItem sender, String message) {
		this.recipient = recipient;
		this.sender = sender;
		this.message = message;
	}

	private final SmokingItem recipient;
	private final SmokingItem sender;
	private final String message;
	
	public SmokingItem getRecipient() {
		return recipient;
	}
	
	public String getMessage() {
		return message;
	}
	
	public SmokingItem getSender() {
		return sender;
	}

}
