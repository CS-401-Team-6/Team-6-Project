import java.util.ArrayList;

import project.Card;

public class Hand 
{
	private ArrayList<Card> cards = new ArrayList<Card>();
	private int score;
	private int bet;
	
	//fist card will be facedown, second is faceup
	public Hand()
	{
		//make score 0
		this.resetScore();
	}
	
	public void initialDeal(Card first, Card second)
	{
		//make score 0
		this.resetScore();
		//add cards
		first.setStatus(Card.STATUS.DOWN);
		second.setStatus(Card.STATUS.UP);
		this.hit(first);
		this.hit(second);
		this.calculateScore();
	}
	
	public void calculateScore()
	{
		//zero out the score, calculate from scratch
		this.resetScore();
	    //number of aces
		int ace = 0; 
		//working variables
		int i, v;
		Card c;
		for(i = 0; i < this.cards.size(); i++)
		{
			c = this.cards.get(i);
			v = c.valueToInt();
			if(v == 1)
			{
				ace++;
				continue;
			}
			this.score += v;
		}
		
		if(ace > 0)
		{
            int target = 21 - this.score;
            //calculating one of the aces as 11
            int current = 11 + (ace - 1);
            //if too big, then we will consider all aces 1
            if(current > target)
            {
            	current = ace;
            }
            
            this.score += current;
		}
	}
	
	//add score as you add cards to hand
	public void hit(Card card)
	{
		card.setStatus(Card.STATUS.UP);
		this.cards.add(card);
	}
	
	//in case we only want to remove one card
	public Card removeCard()
	{
    	Card card = this.cards.get(0);
    	this.cards.remove(card);
    	
    	return card;
	}
	
	//remove every card, return as an arraylist
	public ArrayList<Card> emptyHand()
	{
		ArrayList<Card> temp = new ArrayList<Card>();
		while(cards.size() > 0)
		{
			temp.add(this.removeCard());
		}
		
		this.resetScore();
		return temp;
	}
	
	public void resetScore()
	{
		this.score = 0;
	}
	
	public void setBet(int bet)
	{
		this.bet = bet;
	}
	
	public void resetBet()
	{
		this.bet = 0;
	}

	public int getBet()
	{
		return this.bet;
	}
	
	
	public int getScore()
	{
		return this.score;
	}
	
	public int getNumber()
	{
		return this.cards.size();
	}
	
	public ArrayList<Card> getCards()
	{
		return this.cards;
	}
}
