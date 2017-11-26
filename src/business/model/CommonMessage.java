package business.model;

public class CommonMessage extends Message
{
	private static final long serialVersionUID = -3594224477671398331L;

	public CommonMessage(String userName, String text) {
		super(userName, text);
	}

	@Override
	public String getColoredText() {
		return String.format("%s: %s", super.getUserName(), super.getText());
	}
}
