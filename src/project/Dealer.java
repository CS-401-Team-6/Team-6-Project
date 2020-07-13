package project;
public class Dealer 
{
    private Hand hand;
    
    public Dealer()
    {
    	//theres really nothing to do here
    }
    
    public void hit(Card card)
    {
    	this.hand.hit(card);
    }
    
    public boolean rules()
    {
    	if(this.hand.getScore() <= 16)
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }
    
    public Hand getHand()
    {
        return this.hand;
    }
}
