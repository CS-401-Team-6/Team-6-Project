package client;
import java.io.Serializable;

public class ConnectionMessage implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String type;
    protected byte[] status;
    protected String text;

    public ConnectionMessage(){
        this.type = "Undefined";
 
        this.text = "Undefined";
    }

    public ConnectionMessage(String type, byte[] status, String text){
       setType(type);
       setStatus(status);
       setText(text);
    }

    private void setType(String type){
    	this.type = type;
    }

    public void setStatus(byte[] status){
    	this.status = status;
    }

    public void setText(String text){
    	this.text = text;
    }

    public String getType(){
    	return type;
    }

    public byte[] getStatus(){
    	return status;
    }

    public String getText(){
    	return text;
    }

}
