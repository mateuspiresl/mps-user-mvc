package business.model;

public class MessageFactory
{
	public static Message create(User user, String text)
	{
		return user.isVip() ? new VipMessage(user.getLogin(), text)
				: new CommonMessage(user.getLogin(), text);
	}
}