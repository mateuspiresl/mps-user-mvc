package business.model;

import java.io.Serializable;

public abstract class Message implements Serializable
{
	private static final long serialVersionUID = -5365963605721442551L;
	
	private final String userName;
	private final String text;
	
	public Message(String userName, String text)
	{
		this.userName = userName;
		this.text = text;
	}
	
	public String getUserName() {
		return this.userName;
	}
	
	public String getText() {
		return this.text;
	}
	
	public abstract String getColoredText();
	
	@Override
	public String toString() {
		return this.getColoredText();
	}
	
	@Override
	public boolean equals(Object that)
	{
		if (!(that instanceof Message)) return false;
		
		Message thatMessage = (Message) that;
		return this.userName.equals(thatMessage.userName)
				&& this.text.equals(thatMessage.text);
	}
}
