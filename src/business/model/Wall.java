package business.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Wall implements Serializable
{
	private static final long serialVersionUID = -8132608403753910011L;
	
	private String name, description;
	private List<Message> messages = new ArrayList<Message>();
	
	public Wall(String name)
	{
		this.name = name;
		this.description = "";
		this.messages = new ArrayList<Message>();
	}
	
	public void add(Message message) {
		this.messages.add(message);
	}
	
	public List<Message> list() {
		return Collections.unmodifiableList(this.messages);
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public void setName(String name) {
		this.name= name;
	}
	
	public void setDescription(String text) {
		this.description = text;
	}
}
