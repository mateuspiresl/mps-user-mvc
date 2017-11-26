package business.model;

import java.io.Serializable;

public class User implements Serializable
{
	private static final long serialVersionUID = 7333680617913601432L;
	private static boolean vipGen = false;
	
	private String login, password;
	private final boolean vip;

    public User(String login, String password)
    {
    	this.login = login;
    	this.password = password;
    	
    	// The odd users created are VIP
    	this.vip = vipGen = !vipGen;
    }
	
	public String getLogin() {
        return login;
    }
    
    public String getPassword() {
        return password;
    }

    public boolean isVip() {
    	return this.vip;
    }
    
    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
