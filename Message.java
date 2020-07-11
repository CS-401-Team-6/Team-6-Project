import java.io.Serializable;

public class Message implements Serializable{
	//Messages will be defined as actions to be sent between client and server
	//Depending on the type of message, a different action will be processed through MessageProcessor
	
	//Need to possibly get these objects either from passing it in through the message directly
	//or do it through the server and grab it through a server-side class, then pass it in through messageProcessor instead.
	//protected User user; //Need to get player hand
	//protected Deck deck; //Need to get the deck
	//protected Game game; //Need to get the game if wanted to go with the path of ONLY passing in a Game object
	
    protected String type;
    protected String status;
    protected String text;
    
    public Message(){
        this.type = "Undefined";
        this.status = "Undefined";
        this.text = "Undefined";
    }
    
    public Message(String type, String status, String text){
    	this.type = type;
    	this.status = status;
    	this.text = text;
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
    
}