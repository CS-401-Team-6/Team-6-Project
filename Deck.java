package project;

import java.util.*;

public class Deck 
{
    private ArrayList<Card> deck = new ArrayList<Card>();
    
    //makes a deck of 52 cards, 4 suits and 13 values
    public Deck()
    {
    	for(Card.SUIT suit : Card.SUIT.values())
    	{
    		for(Card.VALUE value : Card.VALUE.values())
    		{
    			String pic = suit.toString() + value.toString();
    			Card card = new Card(suit, value, pic);
    			this.addCard(card);
    		}
    	}
    }
    
    //draws a card randomly from any part of the deck
    public Card randomDrawCard()
    {
    	Random r = new Random();
    	int num = r.nextInt(this.deck.size());
    	Card card = this.deck.get(num);
    	this.deck.remove(card);
    	
    	return card;
    }
    
    //draws the card at the top of the deck
    public Card topDrawCard()
    {
    	Card card = this.deck.get(0);
    	this.deck.remove(card);
    	
    	return card;
    }
    
    //adds a card back to the deck
    public void addCard(Card card)
    {
    	this.deck.add(card);
    }
    
    //shuffles the deck
    public void shuffle()
    {
    	Card card;
    	ArrayList<Card> temp = new ArrayList<Card>();
    	//continuously randomly draw cards and put them in a temp deck
    	//the temp deck becomes our shuffled deck
    	while(this.deck.size() > 0)
    	{
        	card = randomDrawCard();
        	temp.add(card);
    	}
    	
    	this.deck = temp;
    }
}
