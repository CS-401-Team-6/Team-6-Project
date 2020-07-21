import java.util.ArrayList;

public class Game 
{
    private Deck deck;
    private Dealer dealer;
    private ArrayList<User> players = new ArrayList<User>();
    
    public Game()
    {
    	deck = new Deck();
    	dealer = new Dealer();
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
    
    public User activePlayer() {
    	for (int i = 0; i < players.size(); i++) {
    		if (players.get(i).getStatus().equals(STATUS.ACTIVE))
    			return players.get(i);
    	}
    }
}
