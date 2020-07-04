package project;

public class Card 
{
	private VALUE value;
	private SUIT suit;
	private String picture;
	
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
