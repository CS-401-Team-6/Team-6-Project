public class Dealer 
{
    private Hand hand;
    
    public Dealer()
    {
    	this.hand = new Hand();
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
