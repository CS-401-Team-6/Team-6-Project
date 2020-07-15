package server;
import java.util.ArrayList;

public class Game 
{
    private Deck deck;
    private Dealer dealer;
    private ArrayList<User> players = new ArrayList<User>();
    private int numPlayers;
    
    public Game()
    {
    	deck = new Deck();
    	dealer = new Dealer();   
    	numPlayers = 0;
    }
    
    public Deck getDeck()
    {
    	return this.deck;
    }
    
    public Dealer getDealer()
    {
    	return this.dealer;
    }
    
    public ArrayList<User> getPlayers()
    {
    	return this.players;
    }
    
    //ADDED: MM
    //IS FULL
    public boolean isFull()
    {
    	return (numPlayers >= 4);
    }
    
    
}
