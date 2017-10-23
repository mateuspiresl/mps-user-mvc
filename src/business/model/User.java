package business.model;

import java.io.Serializable;

public class User implements Serializable
{
	private static final long serialVersionUID = 7333680617913601432L;
	
	private String login, password;

    public User(String login, String password)
    {
    	this.login = login;
    	this.password = password;
    }
	
	public String getLogin() {
        return login;
    }
    
    public String getPassword() {
        return password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
