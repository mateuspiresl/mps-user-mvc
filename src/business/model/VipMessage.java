package business.model;

public class VipMessage extends Message
{
	private static final long serialVersionUID = 3941561136678440145L;

	public VipMessage(String userName, String text) {
		super(userName, text);
	}

	@Override
	public String getColoredText() {
		return String.format("*-.-*-.-*- %s -*-.-*-.-*: %s", super.getUserName(), super.getText());
	}
}
