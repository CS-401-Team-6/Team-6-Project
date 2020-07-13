package project;
public class Card 
{
	private VALUE value;
	private SUIT suit;
	private String picture;
	private STATUS status;
	
	enum STATUS
	{
		UP, DOWN;
	}
	
    enum VALUE
    {
    	TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, J, Q, K, A;
    }
    
    //hearts, diamonds, clubs, spades
    enum SUIT
    {
    	H, D, C, S;
    }
    
    public Card(SUIT s, VALUE v, String pic)
    {
        this.setSuit(s);
        this.setValue(v);
        this.setPic(pic);
    }
    
    public int valueToInt()
    {
    	int a = 0;
    	
    	switch(this.getValue())
    	{
    	    case TWO: a = 2; break;
    	    case THREE: a = 3; break;
    	    case FOUR: a = 4; break;
    	    case FIVE: a = 5; break;
    	    case SIX: a = 6; break;
    	    case SEVEN: a = 7; break;
    	    case EIGHT: a = 8; break;
    	    case NINE: a = 9; break;
    	    case TEN: a = 10; break;
    	    case J: a = 10; break;
    	    case Q: a = 10; break;
    	    case K: a = 10; break;
    	    case A: a = 1; break;
    	}
    	return a;
    }
    
    public String valueToString()
    {
    	String a = "";
    	
    	switch(this.getValue())
    	{
    	    case TWO: a = "2"; break;
    	    case THREE: a = "3"; break;
    	    case FOUR: a = "4"; break;
    	    case FIVE: a = "5"; break;
    	    case SIX: a = "6"; break;
    	    case SEVEN: a = "7"; break;
    	    case EIGHT: a = "8"; break;
    	    case NINE: a = "9"; break;
    	    case TEN: a = "10"; break;
    	    case J: a = "J"; break;
    	    case Q: a = "Q"; break;
    	    case K: a = "K"; break;
    	    case A: a = "A"; break;
    	}
    	return a;
    }
    
    public STATUS getStatus()
    {
    	return this.status;
    }
    
    public String getPic()
    {
    	return this.picture;
    }
    
    public VALUE getValue()
    {
    	return this.value;
    }
    
    public SUIT getSuit()
    {
    	return this.suit;
    }
    
    public void flipCard()
    {
    	if(this.status == STATUS.DOWN)
    	{
    		this.status = STATUS.UP;
    	}
    }
    
    public void setStatus(STATUS s)
    {
    	this.status = s;
    }
    
    public void setPic(String pic)
    {
    	this.picture = pic;
    }
    
    public void setValue(VALUE value)
    {
    	this.value = value;
    }
    
    public void setSuit(SUIT suit)
    {
    	this.suit = suit;
    }
}
