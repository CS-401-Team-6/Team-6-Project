import java.io.Serializable;

public class Message implements Serializable{
	//Messages will be defined as actions to be sent between client and server
	//Depending on the type of message, a different action will be processed through MessageProcessor

    protected String type;
    protected String status;
    protected String text;
    protected Game game;
   
    public Message() {
        this.type = "Undefined";
        this.status = "Undefined";
        this.text = "Undefined";
        this.game = null;
    }
   
    public Message(String type, String status, String text, Game game) {
    	this.type = type;
    	this.status = status;
    	this.text = text;
    	this.game = game;
    }
   
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public Game getGame() {
		return game;
	}
	
	public void setGame (Game game) {
		this.game = game;
	}
    
}
